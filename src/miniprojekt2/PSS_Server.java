package miniprojekt2;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * *
 * Publish-Subscribe System Server
 *
 * @author Sjurdur
 */
public class PSS_Server {

    public static void main(String[] args) throws UnknownHostException, IOException {
        InetAddress hostname = InetAddress.getLocalHost();
        int sourcePort = 8;
        int sinkPort = 9;

        new SourceServer(hostname, sourcePort);
        new SinkServer(hostname, sinkPort);
    }

    static class SourceServer implements Runnable {
        ServerSocket ss;
        Socket client = null;

        public SourceServer(InetAddress hostname, int sourcePort) throws IOException {
            this.ss = new ServerSocket(sourcePort);
        }

        public void run() {
            while (true) {
                try {
                    client = ss.accept();
                    System.out.println("Connected to client : " + client.getInetAddress().getHostName());
                    new SourceThread(client);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    static class SinkServer implements Runnable {
        ArrayList<Thread> sinks = new ArrayList<>();
        ServerSocket ss;
        Socket client = null;

        public SinkServer(InetAddress hostname, int sinkPort) throws IOException {
            this.ss = new ServerSocket(sinkPort);
            
        }

        public void run() {
            while (true) {
                try {
                    client = ss.accept();
                    System.out.println("Connected to client : " + client.getInetAddress().getHostName());
                    sinks.add(new SinkThread(client));
                    
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }


    static class SourceThread extends Thread {

        SourceThread(Socket sourceSocket) {

        }

    }

    static class SinkThread extends Thread {

        SinkThread(Socket sinkSocket) {

        }
        
        public void sendMessage(OutputStream out){
            
        }

    }
}
/**
    public static void main(String args[]) {
        try {
            ServerSocket sourceserver
                    = new ServerSocket(8);
            while (true) {
                Socket p = server.accept();
                new Thread(new ServerThread(p)).start();
            }
        } catch (Exception ex) {
            System.err.println("Error : " + ex.getMessage());
        }

        try {
            ServerSocket sinkserver = new ServerSocket(9);
            while (true) {
                Socket p
                        = server.accept();
                arraylist.add(new Thread(new ServerThread(p)).start());
            }
        } catch (Exception ex) {
            System.err.println("Error : " + ex.getMessage());
        }
    }

}**/
