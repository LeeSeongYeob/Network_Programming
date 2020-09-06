import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * CalculatorImpl
 */
public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

    private static final long serialVersionUID = 1L;

    public CalculatorImpl() throws RemoteException {
        super();
    }

    public long add(long a, long b) throws RemoteException {
        return a + b;
    }

    public long sub(long a, long b) throws RemoteException {
        return a - b;
    }

    public long mul(long a, long b) throws RemoteException {
        return a * b;
    }

    public long div(long a, long b) throws RemoteException {
        return a / b;
    }

    public double var(int[] a) throws RemoteException {
        double mean = Arrays.stream(a).sum() / a.length;
        double sum = 0;
        for (int element : a) {
            sum += Math.pow((element - mean), 2);
        }
        return sum / a.length;
    }

    public double[] qua(int a, int b, int c) throws RemoteException {
        double dis, ans1, ans2;
        dis = Math.pow(b, 2) - (4 * a * c);
        ans1 = ((-1 * b) + Math.sqrt(dis)) / (2 * a);
        ans2 = ((-1 * b) - Math.sqrt(dis)) / (2 * a);
        return new double[] { ans1, ans2 };
    }
}