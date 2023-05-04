package io.github.AugustoMello09.services.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.AugustoMello09.domain.User;
import io.github.AugustoMello09.domain.dto.UserDTO;
import io.github.AugustoMello09.repositories.UserRepository;
import io.github.AugustoMello09.services.UserService;
import io.github.AugustoMello09.services.exception.DataIntegratyViolationException;
import io.github.AugustoMello09.services.exception.ObjectNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository repository;

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("não encontrado"));
	}

	@Override
	public User create(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public void findByEmail(UserDTO obj) {
		Optional<User> user = repository.findByEmail(obj.getEmail());
		if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
			throw new DataIntegratyViolationException("Email já existe");
		}
	}

	@Override
	public User update(UserDTO obj) {
		findByEmail(obj);
		return repository.save(mapper.map(obj, User.class));
	}

	@Override
	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

}
