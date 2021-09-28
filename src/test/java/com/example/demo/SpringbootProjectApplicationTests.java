package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@SpringBootTest
class SpringbootProjectApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("123@gmail.com");
		user.setPassword("123123");
		user.setRoles(user.getRoles());
		User saveUser = userRepository.save(user);
		User existUser = testEntityManager.find(User.class, saveUser.getId());

		assertThat(existUser.getEmail()).isEqualTo(user.getEmail());

	}
}