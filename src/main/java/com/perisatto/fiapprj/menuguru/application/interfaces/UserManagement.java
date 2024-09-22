package com.perisatto.fiapprj.menuguru.application.interfaces;

import com.perisatto.fiapprj.menuguru.domain.entities.user.User;

public interface UserManagement {
	User createUser(User user) throws Exception;
}
