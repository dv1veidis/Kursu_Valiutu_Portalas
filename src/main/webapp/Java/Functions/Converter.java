package main.webapp.Java.Functions;

import java.sql.*;
import java.util.Vector;

public class Converter {
    public static Double conversionFunction(Double amount, String currency1, String currency2) throws SQLException { //Valiutų konvertavimo funkcija
        if(currency1.equals(currency2)){ // Smulkus bug avoidance kurio esme yra jog jei paduodamos 2 vertes kurios yra tos pacios duomenu baze grazins tik viena todel 
           return amount; //sios funkcijos esme yra grazinti iskarto kiek norime versti ir jog neismestu erroro veliau
        }
        else {
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            PreparedStatement stmt;
            try {
                String query = "select * from TBL_VALUES where currency = ? OR currency = ?"; //query sukuriamas
                Connection con = null;
                con = DriverManager.
                        getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "123");
                stmt = con.prepareStatement(query); // Sukuriame prepare statementa ir irasome vertes kurias padaveme jam puslapyje
                stmt.setString(1, currency1);
                stmt.setString(2, currency2);
                ResultSet rs = stmt.executeQuery();
                Vector<String> currencySQLV = new Vector<>();
                Vector<Double> rateSQLV = new Vector<>();
                while (rs.next()) { //Valiutas istraukiame is duomenu bazes tik tas kurios atitinka mu paduoda query
                    String currencySQL = rs.getString("currency");
                    currencySQLV.add(currencySQL);
                    double rateSQL = rs.getDouble("rate");
                    rateSQLV.add(rateSQL);
                }
                Double conversion;
                String currency1SQL = currencySQLV.get(0);
                if (currency1.equals(currency1SQL)) { // Kadangi duomenu bazeje esanti pirmesne verte nevisada bus mus norima verte si funkcija patikrins ar pradine verte bus lygi grazintai vertei
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
