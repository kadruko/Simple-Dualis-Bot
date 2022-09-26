package app;

import java.io.IOException;
import java.sql.Timestamp;

public class Log {

    private final File file;

    public Log(){

        file = new File("DualisLog.txt");

    }

    public void write(String text) throws IOException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        file.write(timestamp.toString() + " --- " + text);

    }

}
