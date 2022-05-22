package com.infybuzz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infybuzz.request.CreateAddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infybuzz.request.CreateStudentRequest;
import com.infybuzz.response.StudentResponse;
import com.infybuzz.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@PostMapping("/create")
	public StudentResponse createStudent (@RequestBody CreateStudentRequest createStudentRequest) {
		return studentService.createStudent(createStudentRequest);
	}
	
	@GetMapping("/getById/{id}")
	public StudentResponse getById (@PathVariable long id) {
		return studentService.getById(id);
	}

	// retrieve all addresses and update address corresponding to the id in path with the provided address
	@PatchMapping("/students/address/{addressId}")
	public ResponseEntity<Void> updateAddress(@PathVariable long addressId, @RequestBody CreateAddressRequest addressRequest)
			throws JsonProcessingException {
		return studentService.updateAddress(addressId, addressRequest);
	}
	
}
