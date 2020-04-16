
// encoding EUC-KR
import java.net.*;
import java.io.*;

public class ChatServer implements Runnable {

	// 최대 3명까지 chat
	private ChatServerRunnable clients[] = new ChatServerRunnable[3];
	public int clientCount = 0;

	private int ePort = -1;

	public ChatServer(int port) {
		this.ePort = port;
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(ePort);
			System.out.println("Server started: socket created on " + ePort);

			while (true) {
				addClient(serverSocket);
			}
		} catch (BindException b) {
			System.out.println("Can't bind on: " + ePort);
		} catch (IOException i) {
			System.out.println(i);
		} finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}

	public int whoClient(int clientID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getClientID() == clientID)
				return i;
		return -1;
	}

	public void putClient(int clientID, String inputLine) {
		for (int i = 0; i < clientCount; i++)
			// chat 작성한 사람, server console에 출력
			if (clients[i].getClientID() == clientID) {
				System.out.println("writer: " + clientID);
				// clients[i].out.println("your sending msg : " + inputLine);
			} else {
				// 상대방에게 누가 chat을 보냈는지 ClientID 출력해줌
				System.out.println("write: " + clients[i].getClientID());
				clients[i].out.println(inputLine);
			}
	}

	public void addClient(ServerSocket serverSocket) {
		Socket clientSocket = null;
		// 최대 3개의 갯수 만큼 만들 수 있음. 40초간 무응답시, 종료
		if (clientCount < clients.length) {
			try {
				clientSocket = serverSocket.accept();
				clientSocket.setSoTimeout(40000); // 1000/sec
			} catch (IOException i) {
				System.out.println("Accept() fail: " + i);
			}
			clients[clientCount] = new ChatServerRunnable(this, clientSocket);
			new Thread(clients[clientCount]).start();
			clientCount++;
			System.out.println("Client connected: " + clientSocket.getPort() + ", CurrentClient: " + clientCount);
		} else {
			// 3개 이상의 client 요청시, dummysocket 생성 후, 거절 메세지 출력
			try {
				Socket dummySocket = serverSocket.accept();
				ChatServerRunnable dummyRunnable = new ChatServerRunnable(this, dummySocket);
				new Thread(dummyRunnable);
				dummyRunnable.out.println(dummySocket.getPort() + " < Sorry maximum user connected now");
				System.out.println("Client refused: maximum connection " + clients.length + " reached.");
				dummyRunnable.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}

	public synchronized void delClient(int clientID) {
		int pos = whoClient(clientID);
		ChatServerRunnable endClient = null;
		// Client 삭제 시, client 위치 하나 씩 줄여줌.
		if (pos >= 0) {
			endClient = clients[pos];
			if (pos < clientCount - 1)
				for (int i = pos + 1; i < clientCount; i++)
					clients[i - 1] = clients[i];
			clientCount--;
			System.out
					.println("Client removed: " + clientID + " at clients[" + pos + "], CurrentClient: " + clientCount);
			endClient.close();
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Usage: Classname ServerPort");
			System.exit(1);
		}
		int ePort = Integer.parseInt(args[0]);

		new Thread(new ChatServer(ePort)).start();
	}
}

class ChatServerRunnable implements Runnable {
	protected ChatServer chatServer = null;
	protected Socket clientSocket = null;
	protected PrintWriter out = null;
	protected BufferedReader in = null;
	public int clientID = -1;

	public ChatServerRunnable(ChatServer server, Socket socket) {
		this.chatServer = server;
		this.clientSocket = socket;
		clientID = clientSocket.getPort();
		try {
			// 전송하는 chat 내용
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			// 수신하는 char 내용 String 타입으로 받음
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		} catch (IOException i) {
		}
	}

	public int getClientID() {
		return clientID;
	}

	public void close() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (clientSocket != null)
				clientSocket.close();
		} catch (IOException i) {
		}
	}

	public void run() {
		try {
			String inputLine;
			// chatclient 에서 보낸 정보 ,in으로 받아 읽음
			while ((inputLine = in.readLine()) != null) {
				chatServer.putClient(getClientID(), getClientID() + ":" + inputLine);
				if (inputLine.equalsIgnoreCase("Bye."))
					break;
			}
			chatServer.delClient(getClientID());
		} catch (SocketTimeoutException ste) {
			System.out.println("Socket timeout Occurred, force close() : " + getClientID());
			chatServer.delClient(getClientID());
		} catch (IOException e) {
			chatServer.delClient(getClientID());
		}
	}
}
