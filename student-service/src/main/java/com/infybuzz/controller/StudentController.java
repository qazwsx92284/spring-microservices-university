package com.infybuzz.controller;

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

	@PatchMapping("/students")
	public ResponseEntity<Void> updateAddresses()
	
}
