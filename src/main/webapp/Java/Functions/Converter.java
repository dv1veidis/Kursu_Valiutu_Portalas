package main.webapp.Java.Functions;

import java.sql.*;
import java.util.Vector;

public class Converter {
    public static Double conversionFunction(Double amount, String currency1, String currency2) throws SQLException { // supposedly a function which lacks functionality but in theory should work
        if(currency1.equals(currency2)){
           return amount;
        }
        else {
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement stmt;
            try {
                String query = "select * from TBL_VALUES where currency = ? OR currency = ?";
                Connection con = null;
                con = DriverManager.
                        getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "123");
                stmt = con.prepareStatement(query);
                stmt.setString(1, currency1);
                stmt.setString(2, currency2);
                ResultSet rs = stmt.executeQuery();
                Vector<String> currencySQLV = new Vector<>();
                Vector<Double> rateSQLV = new Vector<>();
                while (rs.next()) {
                    String currencySQL = rs.getString("currency");
                    currencySQLV.add(currencySQL);
                    double rateSQL = rs.getDouble("rate");
                    rateSQLV.add(rateSQL);
                }
                Double conversion;
                String currency1SQL = currencySQLV.get(0);
                if (currency1.equals(currency1SQL)) {
                    conversion = amount / rateSQLV.get(0) * rateSQLV.get(1);
                } else {
                    conversion = amount / rateSQLV.get(1) * rateSQLV.get(0);
                }


                con.close();
                return conversion;

            } catch (SQLException e) {
                e.printStackTrace();
                return -1.0;
            }
        }
    }
}
