import java.io.*;

/**
 * OutputStreamDo
 */
public class OutputStreamDo {

    public static void main(String[] args) {
        OutputStream out = (System.out);

        char out1 = 'A';
        char out2 = '가';
        try {
            out.write(out1);
            out.write(out2);

            out.flush();
            out.close();
        } catch (IOException ie) {
            // TODO: handle exception
            System.out.println("예외냐? 이것때문:" + ie);
        }
    }
}