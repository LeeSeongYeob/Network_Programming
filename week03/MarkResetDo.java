import java.io.*;

/**
 * MarkResetDo???
 */
public class MarkResetDo {

    public static void main(String[] args) throws Exception {

        // Inputstream 과 buffer 의 차이점 ?

        InputStream is = null;
        BufferedInputStream bis = null;

        try {
            File file = new File("..\\data\\number.txt");
            is = new FileInputStream(file);
            bis = new BufferedInputStream(is);

            // buffer 위치 mark
            if (bis.markSupported()) {
                int b, count = 0;
                int half = (int) file.length() / 2; // half 절반 위치 mark
                System.out.println(file.length() + "" + half);
                System.out.println("read complete file:");
                System.out.println("--------------------------");
                while ((b = bis.read()) > 0) {
                    System.out.println((char) b);
                    if (++count == half)
                        bis.mark(half);
                }
                System.out.println("\n-----------------------");
                System.out.println("\nmarked: " + (half) + "\n");

                bis.reset();

                System.out.println("read back from marked point");
                System.out.println("------------------------------");
                while ((b = bis.read()) > 0) {
                    System.out.println((char) b);
                }
                System.out.println("\n---------------------------------");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (is != null)
                is.close();
            if (bis != null)
                bis.close();
        }
    }
}