package com.example.JPA;

import com.example.JPA.model.*;
import com.example.JPA.repository.*;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			CardPassRepository cardPassRepository,
			UserRepository userRepository,
			TicketRepository ticketRepository,
			PasswordEncoder encoder,
			Faker faker,
			CartRepository cartRepository,
			OrderRepository orderRepository
			){
		return args -> {

			String rawPassword = encoder.encode("asdasd");

			User user = new User("marci","asd@mail.com",rawPassword,22, Role.ADMIN);

			userRepository.save(user);

			List<Ticket> ticketList = IntStream
					.rangeClosed(1, 50)
					.mapToObj(s -> {
						String[] cities = {"Budapest", "Debrecen", "Miskolc"};
						String[] musicGenres = {"Blues", "Jazz", "Rock", "Pop"};

						int randomCityIndex = new Random().nextInt(cities.length);
						int randomMusicIndex = new Random().nextInt(musicGenres.length);

						return new Ticket(
								faker.app().name(),
								cities[randomCityIndex],
								LocalDate.of(2020, Month.JANUARY, 8),
								LocalDate.of(2020, Month.JANUARY, 8),
								faker.number().numberBetween(100, 200),
								musicGenres[randomMusicIndex]
						);
					})
					.toList();

			ticketRepository.saveAll(ticketList);



		};
	}

}
