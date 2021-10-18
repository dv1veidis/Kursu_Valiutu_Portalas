package main.webapp.Java.Functions;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class setCurrency implements Job{
    public void execute(JobExecutionContext context) throws JobExecutionException{

         //updates the local table through a server connection, lacking a timer that updates it but fuctionally works perfectly
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse("http://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=EU"); //parses lb.lt fx rate XML that we get from the HTTP call
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NodeList currencyName = document.getElementsByTagName("Ccy");
        NodeList rates = document.getElementsByTagName("Amt");
        Vector<String> currencyVector = new Vector<>();
        Vector<Double> ratesVector = new Vector<>();
        Element currency = (Element)currencyName.item(1);
        Element rate = (Element)rates.item(1);
        String currencyString = currency.getTextContent();
        String ratesString = rate.getTextContent();
        double ratesStringD = Double.parseDouble(ratesString);
        currencyVector.add(currencyString);
        ratesVector.add(ratesStringD);
        //out.println("<div>"+currencyString+ " "+ ratesString+"</div>");
        for(int i = 2; i<174; i++){
            i++;
            currency = (Element)currencyName.item(i);
            rate = (Element)rates.item(i);
            currencyString = currency.getTextContent();
            ratesString = rate.getTextContent();
            ratesStringD = Double.parseDouble(ratesString);
            currencyVector.add(currencyString);
            ratesVector.add(ratesStringD);
            System.out.println(ratesStringD);// when updating prints out the new values
            //out.println("<div>"+currencyString+ " "+ ratesString+"</div>");
        }
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String updateString =
                "update TBL_VALUES set rate = ? where currency = ?"; //updating the already existing table values
        Connection conn = null;
        try {
            conn = DriverManager.
                    getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "123"); // connection settings

            try (PreparedStatement updateRates = conn.prepareStatement(updateString))
            {

                conn.setAutoCommit(false);
                for (int i = 0 ; i<ratesVector.size(); i++) {
                    updateRates.setDouble(1, ratesVector.get(i)); //updating rates
                    updateRates.setString(2, currencyVector.get(i)); // choosing which currency rates get updated
                    updateRates.executeUpdate();

                    conn.commit(); // commit to the database
                }
            } catch (SQLException e) {
                System.out.println(e);
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException excep) {
                        System.out.println(excep);
                    }
                }

                String query = "select currency, rate from TBL_VALUES"; //selecting the new currency rates from the table
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    String currencySQL = rs.getString("currency");
                    double rateSQL = rs.getDouble("rate");
                    System.out.println(rateSQL + " " + currencySQL); //printing them out into the console window
                }
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

