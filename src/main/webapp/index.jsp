<%@page import="main.webapp.Java.Functions.setCurrency, main.webapp.Java.Functions.getCurrency, main.webapp.Java.Functions.conversion, main.webapp.Java.Functions.mainScheduler"%>
<html>
<body>
<%
   getCurrency myClass1 = new getCurrency();
   mainScheduler myClass2 = new mainScheduler();%>

<div class="selectionConversion">
    <form action="http://localhost:8080/Valiutu_kursu_portalas/conversion" method="get">
        <label for="amount"> Amount </label>
        <input type = "number" name ="conversionAmount" placeholder = "enter the amount" onkeyup="if(this.value.length > 0) document.getElementById('Convert').disabled = false; else document.getElementById('Convert').disabled = true;"/>
        <label for="currentCurrency"> Converting from</label>
        <select id="currency" name="currency1">
            <%= myClass1.getCurrencies("Options") %>
             </select>
             <label for="secondaryCurrency"> to</label>
             <select id="currency2" name="currency2">
             <%= myClass1.getCurrencies("Options") %>
             <input type="submit" value="Convert" id="Convert" disabled/>
    </form>
    <% String currencyAmount = request.getParameter("conversionAmount"); %>
    <h1>Dabartiniai santykiai (i EUR)</h1>
    <div><%= myClass1.getCurrencies("Rates")%> </div>
</body>
</html>
