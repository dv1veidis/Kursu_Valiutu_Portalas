package main.webapp.Java.Functions;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/conversion")
public class conversion extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getParameter("conversionAmount");
        double conversionAmount = Double.parseDouble(request.getParameter("conversionAmount"));
        String currency1 = request.getParameter("currency1");
        String currency2 = request.getParameter("currency2");
        double conversion = 0;
        try {
            conversion = Converter.conversionFunction(conversionAmount, currency1, currency2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String url = "http://localhost:8080/Valiutu_kursu_portalas/";
        response.setContentType("text/html");
        PrintWriter out= response.getWriter();
        out.println(conversionAmount);
        out.println(currency1+" converted to ");
        out.println(currency2+" is");
        out.println(conversion);
        out.println("<a href="+url+">RETURN</a>");
    }
}
