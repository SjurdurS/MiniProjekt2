/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Sjurdur
 */
public class Source {

    public static void main(String[] args) throws UnknownHostException, IOException {

        InetAddress localhost = InetAddress.getLocalHost();
        int PORT = 8;

        Socket senderSocket = new Socket(localhost, PORT);

        try (InputStream is = System.in;
                OutputStream ss = senderSocket.getOutputStream();) {
            byte[] buf = new byte[1024];

            int eof;
            do {
                eof = is.read(buf);
                if (eof > 0) {
                    ss.write(buf, 0, eof);
                }
            } while (eof >= 0);

        } catch (IOException ex) {
            System.out.println("Connection died:" + ex.getMessage());
        }
    }

}
