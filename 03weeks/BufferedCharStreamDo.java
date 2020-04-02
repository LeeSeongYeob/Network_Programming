import java.io.*;

/**
 * BufferedCharStreamDo
 */
public class BufferedCharStreamDo {

    public static void main(String[] args) {
        int i, len = 0;
        String crSet = "_ansi";

        String sourFile = "..\\data\\base" + crSet + ".txt";
        String destFile = "..\\data\\output_char" + crSet + ".txt";

        System.out.println("Source Name :" + sourFile);
        System.out.println("Target Name :" + destFile);

        try {
            BufferedReader br = null;
            BufferedWriter bw = null;

            br = new BufferedReader(new FileReader(sourFile));
            bw = new BufferedWriter(new FileWriter(destFile));

            while ((i = br.read()) != -1) {
                bw.write(i);

                len += 1;
                System.out.println("Read data[" + i + "," + len + "]");
            }
            br.close();
            bw.close();
            System.out.println("finished");
        } catch (IOException e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }
}