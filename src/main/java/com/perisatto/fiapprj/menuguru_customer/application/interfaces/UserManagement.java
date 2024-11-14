package com.perisatto.fiapprj.menuguru_customer.application.interfaces;

import com.perisatto.fiapprj.menuguru_customer.domain.entities.user.User;

public interface UserManagement {
	User createUser(User user) throws Exception;
}
