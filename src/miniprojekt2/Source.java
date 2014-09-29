/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojekt2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Sjurdur
 */
public class Source {
    
    public static void Main(String[] args) throws UnknownHostException {
        
        if (args.length != 1) {
         System.err.println("Usage: java Source <message>");
         System.exit(1);
         }

        String message = args[0];
        InetAddress localhost = InetAddress.getLocalHost();
        int port = 8;    
    }
    
}
