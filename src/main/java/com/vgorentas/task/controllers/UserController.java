package com.vgorentas.task.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vgorentas.task.data.UserRepository;
import com.vgorentas.task.model.User;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/")
	public String home() {
		return "home";
	}
	
	@PostMapping(value = "/signup")
	public User signUp(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping(value = "/getuser/{id}")
	public Optional<User> getuser(@PathVariable("id") int id) {
		return userRepository.findById(id);
		//return id;
	}
	
	@GetMapping(value = "/getallusers")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@DeleteMapping(value = "/deleteuser/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		Optional<User> user = userRepository.findById(id);
		if(user != null) {
			userRepository.deleteById(id);
			return user.get().getName() + " user deleted.";
		}else {
			return "user not found";
		}
	}
	
	@PutMapping(value = "/updateuser")
	public String updateUser(@RequestBody User user){
		if(getUserByName(user.getName())) {
			userRepository.save(user);
			return user.getName() + " updated";
		}
		return "user not found";
	}
	
	@GetMapping(value = "/getbyusername/{name}")
	public Optional<User> getByUserName(@PathVariable("name") String name){
		Optional<User> user = userRepository.getusers(name);
		if(user != null) {
			return user;
		}
		return userRepository.getusers(name);
	}
	
	@GetMapping(value = "/login")
	public String login(@RequestBody User user) {
		if(getUserByName(user.getName())) {
			return "welcome " + user.getName();
		}
		return user.getName() + " user not found";
	}
	
	public boolean getUserByName(String name) {
		Optional<User> user = userRepository.getusers(name);
		if(!StringUtils.isEmpty(user)) {
			return true;
		}else {
			return false;
		}
	}
}
