package io.github.AugustoMello09.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
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
import io.github.AugustoMello09.services.exception.DataIntegratyViolationException;
import io.github.AugustoMello09.services.exception.ObjectNotFoundException;
import io.github.AugustoMello09.services.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

	private static final int INDEX = 0;

	private static final String NAO_ENCONTRADO = "não encontrado";

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

	private UserDTO userDto;

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
	void whenFindByIdThenReturnAnObjectNotFoundException() {
		when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(NAO_ENCONTRADO));
		try {
			service.findById(ID);
		} catch (Exception e) {
			assertEquals(ObjectNotFoundException.class, e.getClass());
			assertEquals(NAO_ENCONTRADO, e.getMessage());
		}
	}

	@Test
	void whenFindAllThenReturnAnListOfUsers() {
		when(repository.findAll()).thenReturn(List.of(user));

		List<User> response = service.findAll();

		assertNotNull(response);
		assertEquals(1, response.size());
		assertEquals(User.class, response.get(INDEX).getClass());

		assertEquals(ID, response.get(INDEX).getId());
		assertEquals(NAME, response.get(INDEX).getName());
		assertEquals(EMAIL, response.get(INDEX).getEmail());
		assertEquals(PASSWORD, response.get(INDEX).getPassword());
	}

	@Test
	void whenCreateThenReturnSuccess() {
		when(repository.save(any())).thenReturn(user);

		User response = service.create(userDto);

		assertNotNull(response);
		assertEquals(User.class, response.getClass());
		assertEquals(ID, response.getId());
		assertEquals(NAME, response.getName());
		assertEquals(EMAIL, response.getEmail());
		assertEquals(PASSWORD, response.getPassword());
	}

	@Test
	void whenCreateThenReturnAnDataIntegrityViolationException() {
		when(repository.findByEmail(anyString())).thenReturn(optionalUser);

		try {
			optionalUser.get().setId(2);
			service.create(userDto);
		} catch (Exception ex) {
			assertEquals(DataIntegratyViolationException.class, ex.getClass());
			assertEquals("Email já existe", ex.getMessage());
		}

	}

	@Test
	void update() {

	}

	@Test
	void delete() {

	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
		optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
	}
}
