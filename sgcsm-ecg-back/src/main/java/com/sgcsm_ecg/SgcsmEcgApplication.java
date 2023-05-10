package com.sgcsm_ecg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SgcsmEcgApplication {

	public static void main(String[] args) {
		SpringApplication.run(SgcsmEcgApplication.class, args);
		MessageServer server = null;
		try {
			server = new MessageServer(9999, "ca.p12", "ppppp", "cacertificado.pem", "oooooo");
		} catch (Exception e) {
			System.err.println("SocketServer Error...");
			e.printStackTrace();
		}
		Thread serverThread = new Thread(server);
		serverThread.start();
	}

}
