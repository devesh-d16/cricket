package com.devesh.cricket;

import com.devesh.cricket.controller.TournamentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CricketGameApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(CricketGameApplication.class, args);
		TournamentController game = context.getBean(TournamentController.class);
	}
}
