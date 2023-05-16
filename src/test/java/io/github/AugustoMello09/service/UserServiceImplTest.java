package io.github.AugustoMello09.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.AugustoMello09.domain.User;
import io.github.AugustoMello09.domain.dto.UserDTO;
import io.github.AugustoMello09.repositories.UserRepository;
import io.github.AugustoMello09.services.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

	private static final Integer ID = 1;

	private static final String PASSWORD = "123";

	private static final String EMAIL = "jos@gmail.com";

	private static final String NAME = "josé";

	@Mock
	private ModelMapper mapper;

	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserServiceImpl service;

	private User user;

	private UserDTO userDTO;

	private Optional<User> optionalUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void whenFindByIdThenReturnAnUserInstance() {
		when(repository.findById(anyInt())).thenReturn(optionalUser);

		User response = service.findById(ID);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
	}

	@Test
	void create() {

	}

	@Test
	void update() {

	}

	@Test
	void delete() {

	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
}
