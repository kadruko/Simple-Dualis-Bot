package app;

import org.jsoup.Jsoup;

import java.io.IOException;

public class TelegramBot {

    public static String BASE_URL = "https://api.telegram.org/";
    public static String API_URL;

    public TelegramBot(){

        API_URL = BASE_URL + "bot" + Settings.API_TOKEN;

    }

    public void sendMessage(String msg) throws IOException {

        Jsoup.connect(API_URL + "/sendMessage?chat_id=" + Settings.CHATROOM + "&text=" + msg).ignoreContentType(true).execute();

    }


}
