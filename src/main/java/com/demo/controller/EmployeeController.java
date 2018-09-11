package com.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Employee;
import com.demo.exception.DataFormatException;
import com.demo.message.producer.MessageSender;
import com.demo.service.EmployeeService;

@RestController
@RequestMapping(value = "/demo")
public class EmployeeController extends AbstractRestHandler {
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private MessageSender messageSender;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createEmployee(@RequestBody Employee employee, HttpServletRequest request,
			HttpServletResponse response) {
		Employee createEmployee = this.employeeService.createEmployee(employee);
		response.setHeader("Employee", request.getRequestURL().append("/").append(createEmployee.getId()).toString());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void updateEmployee(@PathVariable("id") Long id, @RequestBody Employee Employee, HttpServletRequest request,
			HttpServletResponse response) {
		checkResourceFound(this.employeeService.getEmployee(id));
		if (id != Employee.getId())
			throw new DataFormatException("ID doesn't match!");
		this.employeeService.updateEmployee(Employee);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Employee getEmployee(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.employeeService.getEmployee(id));

		return this.employeeService.getEmployee(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public void deleteEmployee(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.employeeService.getEmployee(id));
		this.employeeService.deleteEmployee(id);
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<Employee> getAllEmployees() {
		return this.employeeService.getAllEmployees();
	}

	@RequestMapping(value = "/messaging/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
			"application/json" })
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void updateEmployeeWithMessaging(@PathVariable("id") Long id, @RequestBody Employee Employee, HttpServletRequest request,
			HttpServletResponse response) {
		
		log.info("sending message to the queue");
		messageSender.sendMessage( Employee );		
	}

}
