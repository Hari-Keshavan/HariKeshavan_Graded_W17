package com.test.gradedW17.Model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repo;

	// method for create operation
	public void create(Employee emp) {
		repo.save(emp);
	}

	// method for read operation
	public List<Employee> read() {
		List<Employee> myList = repo.findAll();
		return myList;
	}

	// method to retrieve particular class
	public Employee getClassById(int id) {		
		if (repo.findById(id).isEmpty()) {
			return null;
		}
		else {
			return repo.findById(id).get();
		}
	}

	// method for update operation
	public void update(Employee emp) {
		repo.save(emp);
	}

	// method for delete operation
	public void delete(Employee emp) {
		repo.delete(emp);
	}
	
	public List<Employee> getBySortOnly(Direction direction, String columnName) {
		return repo.findAll(Sort.by(direction, columnName));
	}
	
	public List<Employee> filterByFirstName(String columnName, String searchKey) {
		//1. Create a dummy object based on the searchKey
		Employee dummy = new Employee();
		dummy.setFirstname(searchKey);

		
		//2. Create Example JPA - where
		ExampleMatcher exMatcher = ExampleMatcher.matching().withMatcher(columnName, ExampleMatcher.GenericPropertyMatchers.exact()).withIgnorePaths("id", "lastname", "email");
		
		//3. Combining Dummy with Where
		Example<Employee> example = Example.of(dummy, exMatcher);
		
		return repo.findAll(example);
	}

}

