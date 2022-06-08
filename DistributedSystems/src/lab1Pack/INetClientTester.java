package lab1Pack;

import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class INetClientTester extends Thread {

private Socket client;
static String host = "192.168.1.254";
static int port = 7;

public INetClientTester(Socket client) {
this.client = client;
}

public static void main(String[] args) throws IOException{
//this would keep a continuous connection to the server on this port.
//while(true) {
Socket socket = new Socket(host, port);
try {
sendInfo(host, port, socket);
getInfo(host, port, socket);
} catch (IOException e) {
e.printStackTrace();
}
//}
}

public static void sendInfo(String host, int port, Socket socket) throws IOException{

try {
InetAddress test = InetAddress.getByName(host);
//InetAddress iNet = InetAddress.getByName("localhost");
String ip = test.getHostAddress();

/*
* Scanner console = new Scanner(System.in);
* System.out.println("Enter what you want to send to server: ");
* String test = console.nextLine();
*/

PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
pw.println(ip.toString());

} catch (Exception ex) {
ex.printStackTrace();
}
}

public static void getInfo(String host, int port, Socket socket) throws IOException{

InputStreamReader in = new InputStreamReader(socket.getInputStream());
BufferedReader bf = new BufferedReader(in);
String str = bf.readLine();
System.out.println("server sent back: " + str);
bf.close();
}

}
