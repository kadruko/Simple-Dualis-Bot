package app;

import java.io.IOException;

public class Main {

    // DEPRECATED for Servers
    // ONLY use on PCs

    private static float credits;
    private static DualisClient client;
    private static TelegramBot bot;

    public static void main(String[] args) {

        bot = new TelegramBot();

        client = new DualisClient();
        try {
            if(client.login()){
                credits = client.getTotalCredits();
                if(credits > -1){
                    //bot.sendMessage("Der Dualis Bot wurde erfolgreich verbunden.");
                    loop();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void loop(){
        try {
            if(client.login()) {
                float actualCredits = client.getTotalCredits();
                System.out.println("CREDITS: " + actualCredits);
                if (actualCredits > credits) {
                    System.out.println("STATUS: Eine neue Note wurde eingetragen.");
                    //bot.sendMessage(Settings.TELEGRAM_MESSAGE);
                    credits = actualCredits;
                } else {
                    System.out.println("STATUS: Keine Veränderung");
                }
                try {
                    Thread.sleep(Settings.LOOP_INTERVAL * 1000 * 60);
                    loop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
