package pe.edu.upc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CertificationAppApplication {

	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password", "tdp");
		SpringApplication.run(CertificationAppApplication.class, args);
	}
}
