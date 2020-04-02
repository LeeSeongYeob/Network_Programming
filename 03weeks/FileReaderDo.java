import java.io.*;

public class FileReaderDo {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("..\\data\\software_read.txt");
            BufferedReader br = new BufferedReader(fr);

            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            fr.close();
        } catch (IOException ie) {
            // TODO: handle exception
            System.out.println("Exception: " + ie);
        }
    }

}