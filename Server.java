import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;


public class Server {
	int Online=0;
	ArrayList<Socket> sockets = new ArrayList<>();
	ArrayList<Scanner> inputs= new ArrayList<Scanner>();
	ArrayList<Formatter> outputs= new ArrayList<Formatter>();
	ArrayList<Client> clients = new ArrayList<>();
	ArrayList<Thread> threads = new ArrayList<>();
	ServerSocket serverSocket;
	public class AcceptNewClient implements Runnable{
		//private ServerSocket serverSocket;
		public AcceptNewClient() {}
		public void run() {
			try {
				serverSocket = new ServerSocket(9003);
				String name = "";
				while  (true) {
					sockets.add(serverSocket.accept());
					Online++;
					System.out.println("Accepted "+Online);
					Scanner in = new Scanner(sockets.get(sockets.size()-1).getInputStream()) ;
					inputs.add(in);
					Formatter out = new Formatter(sockets.get(sockets.size()-1).getOutputStream());
					outputs.add(out);
					outputs.get(outputs.size()-1).format("Enter your name\n");
					outputs.get(outputs.size()-1).flush();
					name=inputs.get(inputs.size()-1).nextLine();
					System.out.println(name+" Joined.");
					clients.add(new Client(name, clients.size()));
					threads.add(new Thread(clients.get(clients.size()-1)));
					threads.get(threads.size()-1).start();
				}
			} catch (IOException e) {
				System.out.println("Exception");
			}
		}
	}
	public class Client implements Runnable {
		private String name = "";
		private int index = 0;
		public Client (String name, int index) {
			this.name=name;
			this.index=index;
		}
		@Override
		public void run() {
			String next;
			do {
				next = inputs.get(index).nextLine();
				if (next.equals("exit")) Online--;
				next = name+": " + next;
				System.out.println(next);
				System.out.println("Online: "+Online+"\n\n");
				for (Formatter ou:outputs) {
					ou.format(next+"\n");
					ou.flush();
				}
			}while (!next.endsWith("exit"));
		}
		
	}
	public static void main(String[] args) {
		try {
			Server srvr = new Server ();
			AcceptNewClient r= srvr.new AcceptNewClient();
			Thread t = new Thread (r);
			t.start();
			Scanner scan = new Scanner(System.in);
			String next;
			do {
				next = scan.nextLine();
			} while (!next.equals("exit"));
			srvr.serverSocket.close();
		}
		catch (Exception e) {
			System.out.println("Exception Happend");
		}
		finally {
			System.out.println("Finally Happend");
			
		}
	}
}
