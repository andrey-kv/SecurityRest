package com.learnspring.SecurityRest;

import com.learnspring.SecurityRest.models.Role;
import com.learnspring.SecurityRest.repositories.RoleRepository;
import com.learnspring.SecurityRest.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityRestApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {

		return args -> {

			Role adminRole = roleRepository.findByRole("ADMIN");
			if (adminRole == null) {
				Role newAdminRole = new Role();
				newAdminRole.setRole("ADMIN");
				roleRepository.save(newAdminRole);
				adminRole = roleRepository.findByRole("ADMIN");
			}

		};

	}
}
