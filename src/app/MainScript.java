package app;

import java.io.IOException;

public class MainScript {

    public static void main(String[] args) {

        TelegramBot bot = new TelegramBot();
        DualisClient client = new DualisClient();
        Log log = new Log();

        try {
            if(client.login()){

                client.getExamResults();

                /*
                float credits = client.getTotalCredits();
                if(credits > -1){

                    File creditFile = new File("credits.txt");
                    float actualCredits = Float.parseFloat(creditFile.read());
                    if(credits > actualCredits){
                        bot.sendMessage(Settings.TELEGRAM_MESSAGE);
                        log.write("STATUS:" + Settings.TELEGRAM_MESSAGE);
                        creditFile.write(String.valueOf(credits));
                    }else{
                        log.write("STATUS: Keine Veränderung");
                    }

                }else{
                    log.write("Die Credits konnten in Dualis nicht ausgelesen werden.");
                }*/


            }else{
                log.write("Beim Login in Dualis ist ein Fehler aufgetreten.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
