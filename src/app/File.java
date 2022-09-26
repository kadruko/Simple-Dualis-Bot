package app;

import java.io.*;

public class File {

    private java.io.File file;

    public File(String fileName){

        file = new java.io.File(fileName);

    }

    public void write(String text) {

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(text);
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public String read() {

        String result = "";
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
            return result;
        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
