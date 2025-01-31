package com.perisatto.fiapprj.user_management.domain.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import com.perisatto.fiapprj.user_management.domain.entities.user.IAMUser;

@ActiveProfiles(value = "test")
public class IAMUserTest {

	@Test
	void givenValidProperties_thenCreateUser() {
		Long id = 1L;
		String customerName = "Roberto Machado";
		String customerEmail = "roberto.machado@bestmail.com";
		String documentNumber = "90779778057";

		IAMUser user = new IAMUser();
		user.setEmail(customerEmail);
		user.setId(id);
		user.setName(customerName);
		user.setPassword(documentNumber);
		
		assertThat(user.getId()).isEqualTo(id);
		assertThat(user.getName()).isEqualTo(customerName);
		assertThat(user.getEmail()).isEqualTo(customerEmail);
		assertThat(user.getPassword()).isEqualTo(documentNumber);
	}
}
