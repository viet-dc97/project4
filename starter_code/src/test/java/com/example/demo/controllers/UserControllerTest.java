package com.example.demo.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
	@InjectMocks
	UserController userController;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	CartRepository cartRepository;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Test
	void findById() {
		Mockito.doReturn(Optional.of(new User())).when(userRepository).findById(Mockito.any());
		ResponseEntity<User> response = userController.findById(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void findByUserName_NotFound() {
		Mockito.doReturn(null).when(userRepository).findByUsername(Mockito.any());
		ResponseEntity<User> response = userController.findByUserName("hoag");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	void findByUserName_Success() {
		Mockito.doReturn(new User()).when(userRepository).findByUsername(Mockito.any());
		ResponseEntity<User> response = userController.findByUserName("hoag");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	void createUser_passwordLengthLessThan7() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("nhut");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void createUser_invalidConfirmPassword() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("1234567");
		request.setConfirmPassword("12345678");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	void createUser_Success() {
		Mockito.doReturn(new Cart()).when(cartRepository).save(Mockito.any());
		CreateUserRequest request = createUserRequest();
		request.setPassword("1234567");
		request.setConfirmPassword("1234567");
		ResponseEntity<User> response = userController.createUser(request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	private CreateUserRequest createUserRequest() {
		CreateUserRequest request = new CreateUserRequest();
		request.setUsername("nhut");
		return request;
	}
}
