package br.com.audoraselection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.audoraselection"})
public class AudoraSelectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudoraSelectionApplication.class, args);
	}

}
