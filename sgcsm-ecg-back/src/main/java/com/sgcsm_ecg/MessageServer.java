package com.sgcsm_ecg;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class MessageServer implements Runnable {

    private SSLServerSocket serverSocket;

    public MessageServer(int port, String keystoreName, String keystorePassword, String truststoreName, String truststorePassword) throws Exception {
        // build keyStore
        Resource res = new ClassPathResource(keystoreName);
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(new FileInputStream(res.getFile()), keystorePassword.toCharArray());

        X509Certificate certificate = null;
        Enumeration<String> enumeration = keystore.aliases();
        String alias = null;
        int n = 1;
        while (enumeration.hasMoreElements()) {
            System.out.println("\n\n");
            alias = (String) enumeration.nextElement();
            System.out.println("alias name " + n + ":  " + alias);

            certificate = (X509Certificate) keystore.getCertificate(alias);
            System.out.println(certificate.toString());
            System.out.println("\n\n");
            n++;

        }
        System.out.println("salgo de leer el keystore");


        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keystore, keystorePassword.toCharArray());

        // build trustStore
        KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
        truststore.load(null , truststorePassword.toCharArray()); //new FileInputStream(truststorePath)

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        // Chain = array de certificados que en el valor 0 tiene nuestro certificado
        java.security.cert.Certificate[] chain = {};
        // reads cert containing in the given inputStream
        res = new ClassPathResource(truststoreName);
        FileInputStream certificateStream = new FileInputStream(res.getFile());
        chain = certificateFactory.generateCertificates(certificateStream).toArray(chain);
        certificateStream.close();

        truststore.setEntry(truststorePassword, new KeyStore.TrustedCertificateEntry(chain[0]), null);

        System.out.println(chain[0].toString());
        System.out.println("\n\n");
        System.out.println("LEIDO CERTIFICADO:");
        System.out.println("\tPROPIETARIO: " + ((X509Certificate) chain[0]).getSubjectDN());
        System.out.println("\tEMISOR: " + ((X509Certificate) chain[0]).getIssuerDN());
        System.out.println("\tVALIDDEZ: " + ((X509Certificate) chain[0]).getNotBefore() + " to "
                + ((X509Certificate) chain[0]).getNotAfter());
        System.out.println("\tNUMERO DE SERIE: " + ((X509Certificate) chain[0]).getSerialNumber());
        System.out.println("\tALGORITMOS: " + ((X509Certificate) chain[0]).getSigAlgName());


        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(truststore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();

        this.serverSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(port);
        this.serverSocket.setNeedClientAuth(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.err.println("SERVER RUNNING: WAITING FOR CONNECTIONS...");
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                // Create a new thread to handle the clientSocket
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}