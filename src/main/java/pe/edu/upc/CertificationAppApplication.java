package pe.edu.upc;

import java.nio.file.FileSystems;
import java.nio.file.Paths;

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
		System.out.println(FileSystems.getDefault());
		System.out.println(Paths.get("").toFile().getAbsolutePath());
	}
}
