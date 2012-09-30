package com.uphea;

import com.uphea.domain.User;
import com.uphea.service.EmailBuilder;

public class EmailRunner extends AppWebRunner {

	public static void main(String[] args) {
		new EmailRunner().runWebApp();
	}

	@Override
	public void run() {
		EmailBuilder emailBuilder = petite.getBean(EmailBuilder.class);

		User user = createUser();

		emailBuilder.createWelcomeMessage(user);
	}

	User createUser() {
		User user = new User();
		user.setEmail("igor.spasic@gmail.com");
		user.setName("Igor Spasic");
		return user;
	}

}
