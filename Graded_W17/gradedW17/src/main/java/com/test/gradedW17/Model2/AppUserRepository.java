package com.test.gradedW17.Model2;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
	
	//creating custom methods using JPA
	Optional<AppUser> findByName(String name);
	
	//must always start from "findBy" - select query where condition
	//column name - 'name'
	//findByName(parameter - column name)
	
	//select * from app_user where name = 'abcd';
	//only one output

}
