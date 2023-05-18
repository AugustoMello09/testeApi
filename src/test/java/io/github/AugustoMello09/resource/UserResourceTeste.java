package io.github.AugustoMello09.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.AugustoMello09.domain.User;
import io.github.AugustoMello09.domain.dto.UserDTO;
import io.github.AugustoMello09.resources.UserResource;
import io.github.AugustoMello09.services.impl.UserServiceImpl;

@SpringBootTest
public class UserResourceTeste {
	private static final int INDEX = 0;

	private static final Integer ID = 1;

	private static final String PASSWORD = "123";

	private static final String EMAIL = "jos@gmail.com";

	private static final String NAME = "josé";

	@Mock
	private ModelMapper mapper;

	@Mock
	private UserServiceImpl service;

	@InjectMocks
	private UserResource resource;

	private User user;

	private UserDTO userDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void whenFindByIdThenReturnSuccess() {
		when(service.findById(anyInt())).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDto);
		ResponseEntity<UserDTO> response = resource.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UserDTO.class, response.getBody().getClass());
		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());
		assertEquals(PASSWORD, response.getBody().getPassword());

	}

	@Test
	void whenFindAllThenReturnAListOfUserDTO() {
		when(service.findAll()).thenReturn(List.of(user));
		when(mapper.map(any(), any())).thenReturn(userDto);
		ResponseEntity<List<UserDTO>> response = resource.findAll();
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ArrayList.class, response.getBody().getClass());
		assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
		assertEquals(ID, response.getBody().get(INDEX).getId());
		assertEquals(NAME, response.getBody().get(INDEX).getName());
		assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
		assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());

	}

	@Test
	void whenCreateThenReturnCreated() {
		when(service.create(any())).thenReturn(user);
		ResponseEntity<UserDTO> response = resource.create(userDto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertNotNull(response.getHeaders().get("Location"));
	}

	@Test
	void whenUpdateThenReturnSuccess() {
		when(service.update(userDto)).thenReturn(user);
		when(mapper.map(any(), any())).thenReturn(userDto);
		ResponseEntity<UserDTO> response = resource.update(userDto, ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertNotNull(response);
		assertEquals(UserDTO.class, response.getBody().getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(ID, response.getBody().getId());
		assertEquals(NAME, response.getBody().getName());
		assertEquals(EMAIL, response.getBody().getEmail());

	}

	@Test
	void whenDeleteThenReturnSuccess() {
		doNothing().when(service).delete(anyInt());
		ResponseEntity<UserDTO> response = resource.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		verify(service, times(1)).delete(anyInt());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		
	}

	private void startUser() {
		user = new User(ID, NAME, EMAIL, PASSWORD);
		userDto = new UserDTO(ID, NAME, EMAIL, PASSWORD);

	}
}
