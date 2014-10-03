import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * *
 * Publish-Subscribe System Server
 *
 * @author Sjurdur
 */
public class PSS_Server {

    private static ArrayList<Socket> sinks = new ArrayList<Socket>();

    public PSS_Server() throws UnknownHostException, IOException {
        int sourcePort = 8;
        int sinkPort = 9;

        new SourceServer(sourcePort);
        new SinkServer(sinkPort);
    }

    static class SourceServer extends Thread {

        ServerSocket ss;
        Socket client = null;

        public SourceServer(int sourcePort) throws IOException {
            this.ss = new ServerSocket(sourcePort);
            this.start();
        }

        public void run() {
            while (true) {
                try {
                    client = ss.accept();
                    System.out.println("Connected to Source : " + client.getInetAddress().getHostName());
                    new SourceThread(client);

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    static class SinkServer extends Thread {

        ServerSocket ss;
        Socket client = null;

        public SinkServer(int sinkPort) throws IOException {
            this.ss = new ServerSocket(sinkPort);
            this.start();
        }

        public void run() {
            while (true) {
                try {
                    client = ss.accept();
                    System.out.println("Connected to Sink : " + client.getInetAddress().getHostName());
                    sinks.add(client);
                    System.out.println(sinks.size());

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    static class SourceThread extends Thread {

        Socket sourceSocket = null;

        SourceThread(Socket sourceSocket) {
            this.sourceSocket = sourceSocket;
            this.start();
        }

        public void run() {
            try {
                System.out.println("Connected to client : " + sourceSocket.getInetAddress().getHostName());

                try (InputStream is = sourceSocket.getInputStream();) {
                    byte[] buf = new byte[1024];
                    int eof;
                    do {
                        eof = is.read(buf);
                        if (eof > 0) {
                            Iterator<Socket> sinksIter = sinks.iterator();
                            while (sinksIter.hasNext()) {
                                Socket sink = sinksIter.next();
                                try {
                                    OutputStream out = sink.getOutputStream();
                                    out.write(buf, 0, eof);
                                } catch (IOException e) {
                                    System.err.println(e.getMessage());                                    
                                    try {
                                        System.out.println("Removing socket from list");
                                        sinksIter.remove();
                                    } catch (Exception ex1) {
                                        System.err.println("Remove error: " + ex1);
                                    }
                                    try {
                                        System.out.println("Closing socked");
                                        sink.close();
                                        
                                    } catch (IOException ex) {
                                        System.err.println("Closing error: " + e);
                                    }
                                }

                            }
                        }
                    } while (eof >= 0);

                } catch (IOException ex) {
                    System.err.println("Connection died:" + ex.getMessage());
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            new PSS_Server();

        } catch (IOException ex) {
            Logger.getLogger(PSS_Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
