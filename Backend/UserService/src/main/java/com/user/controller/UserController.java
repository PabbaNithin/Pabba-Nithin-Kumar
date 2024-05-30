package com.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.bean.User;
import com.user.service.UserService;
import com.user.service.PasswordServiceClient;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordServiceClient passwordServiceClient;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		String strength = passwordServiceClient.checkPasswordStrength(user.getPassword());

		if ("Strong".equals(strength)) {
			User registeredUser = userService.registerUser(user, strength);

			if (registeredUser != null) {
				// User registered successfully
				return ResponseEntity.ok("User registered successfully. Password strength: " + strength);
			} else {
				return ResponseEntity.badRequest().body("Username already exists. Please choose a different username.");
			}
		} else {
			// Password does not meet the strength requirements
			return ResponseEntity.badRequest().body("Password is " + strength + ". Please choose a stronger password.");
		}
	}

	@PostMapping("/check-password-strength")
	public ResponseEntity<String> checkPasswordStrength(@RequestBody String password) {
		String strength = passwordServiceClient.checkPasswordStrength(password);
		return ResponseEntity.ok(strength);
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody User user) {
		User existingUser = userService.findUserByUsername(user.getUsername());
		if (existingUser != null) {
			// Verify if the provided password matches the stored hashed password
			if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
				return ResponseEntity.ok("Login successful!");
			} else {
				return ResponseEntity.badRequest().body("Invalid username or password.");
			}
		} else {
			return ResponseEntity.badRequest().body("Invalid username or password.");
		}
	}

}
