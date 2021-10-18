# Kursu_Valiutu_Portalas
Kursu valiutiu portalas naudojant H2, JAVA, MAVEN, QUARTZ ir lb.lt puslapio API
Projektas po paleidimo randamas šiame url http://localhost:8080/Valiutu_kursu_portalas

# Informacija apie projekta

## Projektas padarytas naudojant H2 duomenu baze todėl reikia ją susiinstaliuoti
### Nuoroda į H2 puslapį https://www.h2database.com/html/main.html
### Žingsniai, jog pasileisti duomenų baze, kad projektas veiktų

  1. Susirandame start menų arba kur esame įraše H2 duomenų bazę ir paleidžiame H2 console
  2. Pasirenkame vartotojo sąsajoje server Generic H2 sever tam, jog duomenų bazė veiktų net kai mes esame prisijunge
  3. JDBC url = jdbc:h2:tcp://localhost/~/test
  4. user = sa
  5. password = 123
  6. Šie duomenys yra kodo pagrindas, todėl įvedame kaip čia parašyta arba Converter.java, getCurrency.java ir setCurrency.java turėsime pakeisti getConnection funkcijos įvestį
  7. Naudodamiesi http://www.h2database.com/html/quickstart.html šiuo URL susikuriame ir paleidžiame duomenų bazę
  8. Turime sukurti lentelę TBL_VALUES, kuri turės 2 stulpelius iš kurių pirmas yra rate o antras currency. Tai galima padaryt SQL statement lauke įrašius create TBL_VALUES (currency varchar(3) primary key, rates double)
  9. Tada, kad ja galėtume naudotis mes turime ją užpildyti (pildymas galimas ir rankiniu būdu naudojant SQL arba galima naudojantis setCurrency funkcijos pavyzdziu pakeisti updateString į INSERT INTO TEST VALUES(? , ?) ir for loop mes tada galime parašyti
 ``` powershell
for (int i = 0 ; i<ratesVector.size(); i++) {
updateRates.setString(1, currencyVector.get(i));
updateRates.setDouble(2, ratesVector.get(i)); 
updateRates.executeUpdate();
conn.commit();
 ```
  10. Turime tūrėti šias valiutas, bet jų santykis su euru gali keistis
  ``` powershell
  EUR 1.0
AUD 1.564
BGN 1.9558
BRL 6.3635
CAD 1.4342
CHF 1.0729
CNY 7.4663
CZK 25.403
DKK 7.4402
GBP 0.84368
HKD 9.0258
HRK 7.5105
HUF 359.71
IDR 16292.6
ILS 3.7409
INR 86.967
ISK 149.6
JPY 132.65
KRW 1371.21
MYR 4.8241
MXN 23.8542
NOK 9.7625
NZD 1.6445
PHP 58.903
PLN 4.5644
RON 4.9465
RUB 82.6325
SEK 10.0288
SGD 1.5637
THB 38.669
TRY 10.7132
USD 1.1602
ZAR 17.0504
AED 4.2783
AFN 102.34658
ALL 121.5999
AMD 572.41639
ARS 114.81465
AZN 1.97891
BAM 1.96045
BDT 99.7026
BHD 0.43909
BYN 2.92119
BOB 8.03678
CLP 933.37241
COP 4465.6515
DJF 207.35462
DZD 159.19803
EGP 18.28658
ETB 53.97189
GEL 3.6247
GNF 11371.45425
YER 292.74827
IQD 1699.58573
IRR 48919.5
JOD 0.82581
KES 128.5884
KGS 98.80242
KWD 0.35116
KZT 495.65936
LBP 1761.102
LYD 5.28744
LKR 232.95
MAD 10.51513
MDL 20.5229
MGA 4629.88125
MKD 61.54539
MNT 3331.185
MZN 74.66048
PAB 1.16475
PEN 4.81176
PKR 199.02666
QAR 4.27457
RSD 117.41845
SAR 4.36909
SYP 2926.43438
TJS 13.20244
TMT 4.0708
TND 3.28203
TWD 32.38354
TZS 2690.5725
UAH 30.9696
UYU 49.74065
UZS 12423.28174
VES 4814429.23445
VND 26504.46863
XAF 663.94827
XOF 658.63701
```

### Kadangi tai Maven based projektas mums nereikia nieko įsikelti, bet jei kurios dalies trūktų visi dependencies yra pom.xml faile


### Ką reikėtų turėti omenyje apie šį projektą

  1. Nors šis projektas ir turi QUARTZ Scheduleri jis dėl neaiškių priežasčių neveikia
  2. Šiam projektui trūksta lentelių su valiutos pokyčių istorija
  3. Visos Java funkcijos veikia iš Java.Functions package
  4. Šis projektas veikia su apache tomcat 9.0.54 todėl jį reikia leisti pro šį serverį
