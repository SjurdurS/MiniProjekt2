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
public class Sink {

    public static void main(String[] args) throws UnknownHostException, IOException {

        InetAddress localhost = InetAddress.getLocalHost();
        int PORT = 9;

        Socket receiverSocket = new Socket(localhost, PORT);

        try (
                InputStream is = receiverSocket.getInputStream();
                OutputStream os = System.out;) {

            byte[] Buf = new byte[1024];

            int eof;
            do {
                eof = is.read(Buf);
                if (eof > 0) {
                    os.write(Buf, 0, eof);
                }
            } while (eof >= 0);

        } catch (IOException ex) {
            System.out.println("Connection died:" + ex.getMessage());

        }
    }
}
