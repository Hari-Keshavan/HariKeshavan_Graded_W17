package com.test.gradedW17;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.test.gradedW17.Model2.AppUser;
import com.test.gradedW17.Model2.AppUserService;

@SpringBootApplication
public class GradedW17Application implements CommandLineRunner {
	
	@Autowired
	AppUserService appUserService;

	public static void main(String[] args) {
		SpringApplication.run(GradedW17Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Set<String> authAdmin = new HashSet<>();
		authAdmin.add("Admin");
		
		//create encode object
		PasswordEncoder en = new BCryptPasswordEncoder();
		
		AppUser appAdmin = new AppUser(1, "admin", "admin", en.encode("adminPassword"), authAdmin);
		appUserService.add(appAdmin);
		
	}

}
