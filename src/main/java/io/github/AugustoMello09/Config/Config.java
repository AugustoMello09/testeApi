package io.github.AugustoMello09.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.github.AugustoMello09.domain.User;
import io.github.AugustoMello09.repositories.UserRepository;

@Configuration
@Profile("local")
public class Config {
	
	@Autowired
	private UserRepository repository;
	
	@Bean
	public void startDB() {
		User u1 = new User(null, "josé", "example0@email.com", "123");
		User u2 = new User(null, "Augusto", "example1@email.com", "123");
		repository.saveAll(List.of(u1,u2));
	}
}
