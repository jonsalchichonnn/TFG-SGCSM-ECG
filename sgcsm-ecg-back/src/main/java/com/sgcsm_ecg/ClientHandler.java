package com.sgcsm_ecg;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ClientHandler implements Runnable {

    private SSLSocket clientSocket;

    public ClientHandler(SSLSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
////            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
////            String line = null;
////            while ((line = in.readLine()) != null) {
////                // Process the input from the client
////            }
//            in.close();
            SSLSession session = clientSocket.getSession();

            // Supported cipher
            String cipher = session.getCipherSuite();

            System.err.println("Peer: " + clientSocket.getRemoteSocketAddress());
            System.err.println("Host: " + session.getPeerHost());
            System.err.println("CIPHER SUITES AGREMENTED: " + cipher);

            OutputStream sslOs = clientSocket.getOutputStream();
            InputStream sslIs = clientSocket.getInputStream();
            DataOutputStream sslOutput = new DataOutputStream(sslOs);
            DataInputStream sslInput = new DataInputStream(sslIs);

            // Read input msg
            String sslRecvMsg = sslInput.readUTF();
            System.err.println("MESSAGE RECEIVED >>>>:   " + sslRecvMsg);

//            String fechaactual = new Date().toString();
//				String serverEchoResponse = "     >>>ACK(" + fechaactual + ")";

            // Thread.currentThread().sleep(10000);
            // 10 segundos

            sslOutput.writeUTF("<<<<<<<<<<<<<<<<<<<<<<<<<RESPONSE MESSAGE FROM SERVER");

//            System.err.println(sslRecvMsg);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}