package com.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.demo.entity.Employee;


public interface EmployeeRespository extends CrudRepository<Employee, Long>{

	List<Employee> findByName(String name);

	 

}
