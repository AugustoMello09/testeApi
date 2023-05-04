package io.github.AugustoMello09.services;

import java.util.List;

import io.github.AugustoMello09.domain.User;
import io.github.AugustoMello09.domain.dto.UserDTO;

public interface UserService {
	
	User findById(Integer id);
	
	List<User> findAll();
	
	User create(UserDTO obj);
	
	User update(UserDTO obj);
	
	void delete(Integer id);
}
