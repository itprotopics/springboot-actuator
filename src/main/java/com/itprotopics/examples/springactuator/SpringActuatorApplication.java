package com.itprotopics.examples.springactuator;

import java.util.Date;

import javax.annotation.PostConstruct;

import com.itprotopics.examples.springactuator.entities.User;
import com.itprotopics.examples.springactuator.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringActuatorApplication {

	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringActuatorApplication.class, args);
	}

	@PostConstruct
	private void initDb() {
		User user = new User();

		user.setUserName("PeterM");
		user.setPassword("ABC123abc*");
		user.setDateofBirth(new Date());
		user.setCreationTime(new Date());
		userRepository.save(user);
	}

}
