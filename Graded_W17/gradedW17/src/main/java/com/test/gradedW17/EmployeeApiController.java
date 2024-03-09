package com.test.gradedW17;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.gradedW17.Model.Employee;
import com.test.gradedW17.Model.EmployeeService;
import com.test.gradedW17.Model2.AppUser;
import com.test.gradedW17.Model2.AppUserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class EmployeeApiController {
	
	@Autowired
	EmployeeService empService;
	
	@Autowired
	AppUserService appUserService;
	
	@GetMapping("api/getAllEmployee")
	public List<Employee> getAllEmployees() {
		return empService.read();
	}
	
	@GetMapping("/api/getEmployee")
	public Employee getLibrary (@RequestParam int id) {
		Employee emp1 = empService.getClassById(id);
		return emp1;
	}
	
	@PostMapping("api/addUserRole")
	public String addUserRole(@RequestParam int id, @RequestParam String username, @RequestParam String password, Authentication authentication, SecurityContextHolder auth) {
		String acceptedRole = "admin"; // for db
		boolean roleFound = false;

		//who is currently logging in
		System.out.println("Current login by: " + authentication.getName());

		//find the role of the person who have logged
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		//above - getting the role(s) mapped with the user

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Set<String> authUser = new HashSet<>();
			authUser.add("User");
			
			//create encode object
			PasswordEncoder en = new BCryptPasswordEncoder();

			AppUser appUser = new AppUser(id, username, username, en.encode(password), authUser);
			appUserService.add(appUser);
			return "User added successfully";
		}
		return "User can't access this operation!";
	}
	
	@PostMapping("api/addNewEmployee")
	public String addEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, Authentication authentication, SecurityContextHolder auth) {

		String acceptedRole = "admin"; // for db
		boolean roleFound = false;

		//who is currently logging in
		System.out.println("Current login by: " + authentication.getName());

		//find the role of the person who have logged
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		//above - getting the role(s) mapped with the user

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			if (empService.getClassById(id) != null) {
				return "Duplicate Id";
			}
			Employee emp1 = new Employee(id, firstname, lastname, email);
			empService.create(emp1);
			return "Employee Added";
		}
		return "User can't access this operation!";


	}

	@PutMapping("api/updateEmployee")
	public String updateEmployee(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, Authentication authentication, SecurityContextHolder auth) {

		String acceptedRole = "admin"; // for db
		boolean roleFound = false;

		//who is currently logging in
		System.out.println("Current login by: " + authentication.getName());

		//find the role of the person who have logged
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		//above - getting the role(s) mapped with the user

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Employee emp1 = new Employee(id, firstname, lastname, email);
			empService.update(emp1);
			return "Employee Updated";
		}
		return "User can't access this operation!";
	}
	
	@DeleteMapping("api/deleteEmployee")
	public String deleteEmployee(@RequestParam int id, Authentication authentication, SecurityContextHolder auth) {
		
		String acceptedRole = "admin"; // for db
		boolean roleFound = false;

		//who is currently logging in
		System.out.println("Current login by: " + authentication.getName());

		//find the role of the person who have logged
		@SuppressWarnings("static-access")
		Collection<?extends GrantedAuthority> grantedRoles = auth.getContext().getAuthentication().getAuthorities();
		//above - getting the role(s) mapped with the user

		for (int i=0; i<grantedRoles.size(); i++) {
			String role = grantedRoles.toArray()[i].toString();
			System.out.println("My Role: " + role);

			if (role.equalsIgnoreCase(acceptedRole)) {
				roleFound = true;
			}
		}
		if (roleFound) {
			Employee emp1 = new Employee(id, "", "", "");
			empService.delete(emp1);
			return "Employee Deleted";
		}
		return "User can't access this operation!";
	}
	
	@Operation(summary = "Send Direction 1 for ascending, others for descending.")
	@GetMapping("api/sorting")
	public List<Employee> getBySorting(@RequestParam int direction,@RequestParam String columnName) {
		
		if (direction == 1) {
			List<Employee> lib = empService.getBySortOnly(Direction.ASC, columnName);
			return lib;
		}
		else {
			List<Employee> lib = empService.getBySortOnly(Direction.DESC, columnName);
			return lib;
		}
		
	}
	
	@Operation(summary = "Filtering by firstname by exact method")
	@GetMapping("api/filterByFirstName")
	public List<Employee> filterBySubject(@RequestParam (defaultValue = "firstname") String columnName,@RequestParam String searchKey) {
		return empService.filterByFirstName(columnName, searchKey);
	}

}
