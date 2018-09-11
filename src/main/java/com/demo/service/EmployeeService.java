package com.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.entity.Employee;
import com.demo.repository.EmployeeRespository;

@Component
public class EmployeeService {
	private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	private EmployeeRespository employeeRepository;

	public Employee createEmployee(Employee station) {

		log.info("creating an employee");
		return employeeRepository.save(station);
	}

	public Employee getEmployee(Long id) {
		log.info("creating new employee" + id);
		return employeeRepository.findOne(id);
	}

	@Transactional
	public void updateEmployee(Employee station) {
		employeeRepository.save(station);

	}

	public void deleteEmployee(Long id) {
		employeeRepository.delete(id);

	}

	public List<Employee> getAllEmployees() {
		return (List<Employee>) employeeRepository.findAll();
	}

}
