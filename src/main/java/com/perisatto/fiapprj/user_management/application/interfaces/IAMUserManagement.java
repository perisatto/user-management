package com.perisatto.fiapprj.user_management.application.interfaces;

import com.perisatto.fiapprj.user_management.domain.entities.user.IAMUser;

public interface IAMUserManagement {
	IAMUser createUser(IAMUser user) throws Exception;
}
