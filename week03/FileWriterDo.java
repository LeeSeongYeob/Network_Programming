import java.io.*;

/**
 * FileWriterDo
 */
public class FileWriterDo {

    public static void main(String[] args) {
        String what1 = new String("안녕하신가요? -.-\r\n");
        String what2 = new String("자바 네트워크 프로그래밍을 듣고 있습니다.");
        System.out.println("안녕");
        try {
            FileWriter fw = new FileWriter(".\\data\\software_write.txt", true);
            fw.write(what1);
            fw.write(what2);
            fw.close();
            ;
            System.out.println("Done: check software.txt");

        } catch (IOException ie) {
            // TODO: handle exception
            System.out.println("Exeption: " + ie);
        }
    }
}