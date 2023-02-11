package com.example.JPA;

import com.example.JPA.repository.FestivalCardPassRepository;
import com.example.JPA.model.FestivalCardPass;
import com.example.JPA.model.User;
import com.example.JPA.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			FestivalCardPassRepository festivalCardPassRepository,
			UserRepository userRepository
			){
		return args -> {
			User user = new User("marci","asd@mail.com",22);

			FestivalCardPass festivalCardPass = new FestivalCardPass("marci",567,user);

			user.setFestivalCardPass(festivalCardPass);

			userRepository.save(user);


		};
	}

}
