package com.sgcsm_ecg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgcsm_ecg.entity.Device;
import com.sgcsm_ecg.entity.DeviceDTO;
import com.sgcsm_ecg.service.DeviceService;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ClientHandler implements Runnable {

    private SSLSocket clientSocket;
    private DeviceService deviceService;

    public ClientHandler(SSLSocket clientSocket) {
        this.clientSocket = clientSocket;
    }

    private DeviceService getDeviceService() {
        return SpringContext.getBean(DeviceService.class);
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

            // Convert JSON to Object
            ObjectMapper objectMapper = new ObjectMapper();
            DeviceDTO deviceDTO = objectMapper.readValue(sslRecvMsg, DeviceDTO.class);

            Device device = new Device(deviceDTO.getId(), sslRecvMsg);
//            deviceService = getDeviceService();
//            deviceService.saveOrUpdate(device);
            getDeviceService().saveOrUpdate(device);


//            String fechaactual = new Date().toString();
//				String serverEchoResponse = "     >>>ACK(" + fechaactual + ")";

            // Thread.currentThread().sleep(10000);
            // 10 segundos
            String deviceJSON = objectMapper.writeValueAsString(deviceDTO);
            System.err.println(deviceJSON);
            sslOutput.writeUTF(deviceJSON);
//            sslOutput.writeUTF("<<<<<<<<<<<<<<<<<<<<<<<<<RESPONSE MESSAGE FROM SERVER");
//            System.err.println(sslRecvMsg);
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}