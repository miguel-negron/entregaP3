package com.dw2a.SpringQuiz_MiguelNegron;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dw2a.SpringQuiz_MiguelNegron.entidades.Puntuacion;
import com.dw2a.SpringQuiz_MiguelNegron.repositories.PuntuacionRepository;

@SpringBootApplication
public class SpringQuizMiguelNegronApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringQuizMiguelNegronApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(PuntuacionRepository repositorio) {
	    return (args) -> {
	        Puntuacion p = new Puntuacion( "Miguel", "El mejor");
	        Puntuacion p3 = new Puntuacion( "Pablo", "Meh");
	        Puntuacion p2 = new Puntuacion( "Javi", "Majete");
	        
	        repositorio.save(p);
	        repositorio.save(p3);
	        repositorio.save(p2);
	        
	    };
	}
}
