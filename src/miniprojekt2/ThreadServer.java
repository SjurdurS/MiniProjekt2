package miniprojekt2;

import java.net.ServerSocket;
import java.net.Socket;

public class ThreadServer {

    static class ServerThread implements Runnable {

        Socket client = null;

        public ServerThread(Socket c) {
            this.client = c;
        }

        public void run() {
            try {
                System.out.println("Connected to client : " + client.getInetAddress().getHostName());
                client.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(7000);
            while (true) {
                Socket p = server.accept();
                new Thread(new ServerThread(p)).start();
            }
        } catch (Exception ex) {
            System.err.println("Error : " + ex.getMessage());
        }
    }
}