import java.io.IOException;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;

public class Client {
	
	public class Reciever implements Runnable{
		Scanner socketIn;
		public Reciever(Scanner socketIn) {
			this.socketIn =socketIn;
		}
		@Override
		public void run() {
			try {
				String next;
				do {
					next = socketIn.nextLine();
					System.out.println(next);
					System.out.flush();
				} while (true);
			}
			catch (Exception e) {System.out.println("Exception");}
		}
		
	}
	public class Sender implements Runnable{
		Formatter socketOut;
		Socket socket;
		public Sender (Formatter socketOut,Socket socket){
			this.socketOut = socketOut;
			this.socket=socket;
		}
		@Override
		public void run() {
			try (Scanner systemIn = new Scanner(System.in)) {
				String next;
				do {
					next = systemIn.nextLine();
					socketOut.format(next+"\n");
					socketOut.flush();
				}while (!next.equals("exit"));
			}
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("Exception");
			}
			System.exit(1);
		}
		
	}
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost",9003);
				Scanner socketIn = new Scanner(socket.getInputStream());
				Formatter socketOut = new Formatter(socket.getOutputStream());
				Scanner systemIn = new Scanner(System.in);){
					System.out.println(socketIn.nextLine());
					socketOut.format(systemIn.nextLine()+"\n");
					socketOut.flush();
					System.out.println("Connected\t\t\t for end Type \"exit\"");
					Client clnt = new Client();
					Reciever rec = clnt.new Reciever(socketIn);
					Sender sen = clnt.new Sender(socketOut,socket);
					Thread t1 = new Thread(rec);
					Thread t2 = new Thread(sen);
					t1.start();
					t2.start();
					while (true) {}
				}
		catch (Exception e) {
			System.out.println("Exception");
		}
		finally {
			System.out.println("Finally");
		}
	}
}
