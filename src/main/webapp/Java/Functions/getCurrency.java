package main.webapp.Java.Functions;

import java.sql.*;
import java.util.Vector;

import static java.lang.System.out;

public class getCurrency {
    public static String getCurrencies(String choice) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver"); // using the database in order to bring back all the possible currencies
        String temporary="";
        Connection con = null;
        con = DriverManager.
                getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "123");
        String query = "select currency, rate from TBL_VALUES";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Vector<String> currencySQLV = new Vector<>();
        Vector<Double> rateSQLV = new Vector<>();
        if(choice=="Options"){//a html based solution for selecting currency and using a form to get the info
        try {
            while (rs.next()) {
                String currencySQL = rs.getString("currency");
                currencySQLV.add(currencySQL);
                temporary+="<option value= " + currencySQL + "> " + currencySQL + "</option>";
            }
            con.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
            return "error 2";
        }
        return temporary;
        }
        else{
            try {
                while (rs.next()) {
                    String currencySQL = rs.getString("currency");
                    currencySQLV.add(currencySQL);
                    double rateSQL = rs.getDouble("rate");
                    rateSQLV.add(rateSQL);
                    temporary+="<div>" + currencySQL+" "+rateSQL + "</div>";
                }
                con.close();
            } catch (
                    SQLException e) {
                e.printStackTrace();
                return "error 2";
            }
            return temporary;
        }
    }
}
