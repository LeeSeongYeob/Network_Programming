package weeks;

class ImplementsRunnable implements Runnable {

	private int counter = 0;

	public void run() {
		counter++;
		System.out.println("ImplementsRunnable : Counter : " + counter);
	}
}

class ExtendsThread extends Thread {

	private int counter = 0;

	public void run() {
		counter++;
		System.out.println("ExtendsThread : Counter : " + counter);
	}
}

public class ThreadVsRunnable {

	public static void main(String args[]) throws Exception {
		// Threads share the same object
		ImplementsRunnable rc = new ImplementsRunnable();
		// Thread 를 직접 사용하여 선언 시, Runnable 객체를 매개 변수로 넘겨 주어야 함.
		// Runnable 의 인스턴스 사용.
		Thread t1 = new Thread(rc);
		t1.start();
		Thread.sleep(1000); // waiting for 1 sec before starting next thread

		Thread t2 = new Thread(rc);
		t2.start();
		Thread.sleep(1000);

		Thread t3 = new Thread(rc);
		t3.start();

		ExtendsThread tc1 = new ExtendsThread();
		tc1.start();
		Thread.sleep(1000);

		ExtendsThread tc2 = new ExtendsThread();
		tc2.start();
		Thread.sleep(1000);

		ExtendsThread tc3 = new ExtendsThread();
		tc3.start();
	}
}
// Conclusion
// When you extends Thread class, each of your thread creates unique object and
// associate with it. where as
// When you implement Runnable, it shares the same object to multiple threads.

// The other way to create a thread is to declare a class that implements the
// Runnable interface. That class then implements the run method. An instance of
// the class can then be allocated, passed as an argument when creating Thread,
// and started.