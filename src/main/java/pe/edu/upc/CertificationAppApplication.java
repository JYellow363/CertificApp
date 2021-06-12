package pe.edu.upc;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CertificationAppApplication {

    @PostConstruct
    public void init(){
		System.setProperty("jasypt.encryptor.password", "tdp");
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CertificationAppApplication.class, args);
	}
}
