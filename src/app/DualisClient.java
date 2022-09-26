package app;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DualisClient {

    public static String BASE_URL = "https://dualis.dhbw.de";
    private String cookieLogin;
    private Document docMainPage;

    public DualisClient(){

        cookieLogin = null;
        docMainPage = null;

    }

    public boolean login() throws IOException{

        Connection.Response res = Jsoup.connect(BASE_URL + "/scripts/mgrqispi.dll?usrname=" + Settings.CREDENTIALS_USERNAME + "&pass=" + Settings.CREDENTIALS_PASSWORD + "&APPNAME=CampusNet&PRGNAME=LOGINCHECK&ARGUMENTS=clino,usrname,pass,menuno,menu_type,browser,platform&clino=000000000000001&menuno=000324&menu_type=classic&browser=&platform").method(Connection.Method.GET).execute();
        Document docLogin = res.parse();
        Element eLoginForm = docLogin.selectFirst("#cn_loginForm");
        if(eLoginForm == null){
            cookieLogin = res.cookie("cnsc");
            String redirectURL = res.header("REFRESH").split(";")[1].replace(" URL=", "");
            Document docRedirection = Jsoup.connect(BASE_URL + redirectURL).cookie("cnsc", cookieLogin).get();
            Element metaLink = docRedirection.selectFirst("meta[http-equiv='refresh']");
            String mainPageUrl = metaLink.attr("content").split(";")[1].replace("URL=", "");
            docMainPage = Jsoup.connect(BASE_URL + mainPageUrl).cookie("cnsc", cookieLogin).get();
        }
        return eLoginForm == null;

    }

    public void getExamResults() throws IOException {

        if(cookieLogin != null && docMainPage != null) {

            Element linkPerformance = docMainPage.selectFirst("a.navLink:contains(Prüfungsergebnisse)");
            Document doc = Jsoup.connect(BASE_URL + linkPerformance.attr("href")).cookie("cnsc", cookieLogin).get();
            Element semSelect = doc.getElementById("semester");
            Elements semOptions = semSelect.children();
            for(Element semOption : semOptions){
                String semValue = semOption.attr("value");
                String semURL = "/scripts/mgrqispi.dll?APPNAME=CampusNet&PRGNAME=COURSERESULTS&ARGUMENTS=-N933954874623159,-N000307,-N" + semValue;
                Document semDoc = Jsoup.connect(BASE_URL + semURL).cookie("cnsc", cookieLogin).get();
                Element semTable = semDoc.selectFirst("table.nb.list");
                Element semTableBody = semTable.selectFirst("tbody");
                Elements semRows = semTableBody.select("tr");
                semRows.remove(semRows.last());
                for(Element semRow : semRows){

                }
                System.out.println(semDoc);
                break;
            }
            System.out.println(doc);

        }

    }

    /*public void getPerformance() throws IOException {
        if(cookieLogin != null && docMainPage != null) {
            Element linkPerformance = docMainPage.selectFirst("a.navLink:contains(Leistungsübersicht)");
            Document doc = Jsoup.connect(BASE_URL + linkPerformance.attr("href")).cookie("cnsc", cookieLogin).get();
            Element table = doc.selectFirst("table.students_results");
            Element tbody = table.selectFirst("tbody");
            Elements rows = tbody.select("tr");
            for (Element row : rows){
                if(!row.hasClass("subhead")){

                }
            }
        }
    }*/

    public float getTotalCredits() throws IOException {
        if(cookieLogin != null && docMainPage != null) {
            Element linkPerformance = docMainPage.selectFirst("a.navLink:contains(Leistungsübersicht)");
            Document doc = Jsoup.connect(BASE_URL + linkPerformance.attr("href")).cookie("cnsc", cookieLogin).get();
            Element table = doc.selectFirst("table.students_results");
            Element tbody = table.selectFirst("tbody");
            Elements rows = tbody.select("tr");
            Element creditRow = rows.get(rows.size() - 2);
            Element creditCell = creditRow.select("td").get(2);
            String creditString = creditCell.text().replace(",", ".");
            float credits = Float.parseFloat(creditString);
            return credits;
        }
        return -1;
    }

}
