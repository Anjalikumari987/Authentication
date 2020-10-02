package com.app.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.registration.model.User;
import com.app.registration.service.RegistrationService;

@RestController
public class RegistrationController {
	
	@Autowired
	private RegistrationService service;
	
	@PostMapping("/registeruser")
	@CrossOrigin(origins = "http://localhost:4200")
	public User registerUser(@RequestBody User user) throws Exception 
	{
		String tempEmailId = user.getEmailId();
		if(tempEmailId != null &&  !"".equals(tempEmailId))
		{
			User userobj = service.fetchUserByEmailId(tempEmailId);
			if(userobj != null)
			{
				throw new Exception ("user with " + tempEmailId + " is already exist");
			}
		} 
		User userObj = null;
		userObj = service.saveUser(user);
		return userObj;
	}
	
	
	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public User loginUser(@RequestBody User user) throws Exception
	{
		String tempEmailId = user.getEmailId();
		String tempPass = user.getPassword();
		User userObj = null;
		if(tempEmailId != null && tempPass != null )
		{
			userObj = service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
		}
		if(userObj == null) {
			throw new Exception("Bad credentials");
		}
		return userObj;
	}
	
	@GetMapping("/update")
	@CrossOrigin(origins = "http://localhost:4200")
	public User updateUser(@RequestBody User user) throws Exception{
		int Id = user.getId();
		User userObj = null;
		if(Id != 0)
		{
		    userObj = service.fetchUserById(Id);
		}
		
		if(userObj == null) {
			throw new Exception("Bad credentials");
		}
			userObj = service.saveUser(user);
		
		return userObj;
	}
	@CrossOrigin(origins = "http://localhost:4200")
	 @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
	       // LOG.info("Deleting user with id: {}", id);
	        User user = service.fetchUserById(id);

	        if (user == null) {
	            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	        }

	        service.delete(id);
	        return new ResponseEntity<Void>(HttpStatus.OK);
	    }
	 
	}


