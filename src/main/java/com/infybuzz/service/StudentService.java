package com.infybuzz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infybuzz.entity.Student;
import com.infybuzz.repository.StudentRepository;
import com.infybuzz.request.CreateAddressRequest;
import com.infybuzz.request.CreateStudentRequest;
import com.infybuzz.response.AddressResponse;
import com.infybuzz.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	WebClient webClient;

	@Autowired
	private ObjectMapper objectMapper;

	public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {

		Student student = new Student();
		student.setFirstName(createStudentRequest.getFirstName());
		student.setLastName(createStudentRequest.getLastName());
		student.setEmail(createStudentRequest.getEmail());
		
		student.setAddressId(createStudentRequest.getAddressId());
		student = studentRepository.save(student);
		
		StudentResponse studentResponse = new StudentResponse(student);
		
		studentResponse.setAddressResponse(getAddressById(student.getAddressId()));

		return studentResponse;
	}
	
	public StudentResponse getById (long id) {
		Student student = studentRepository.findById(id).get();
		StudentResponse studentResponse = new StudentResponse(student);
		
		studentResponse.setAddressResponse(getAddressById(student.getAddressId()));
		
		return studentResponse;
	}
	
	public AddressResponse getAddressById (long addressId) {
		Mono<AddressResponse> addressResponse = 
				webClient.get().uri("/getById/" + addressId)
		.retrieve().bodyToMono(AddressResponse.class);
		
		return addressResponse.block();
	}

	public ResponseEntity updateAddress(long addressId, CreateAddressRequest addressRequest) throws JsonProcessingException {
		// retrieve list of addresses by invoking address micro-service
		Mono<List<AddressResponse>> monoAddressResponse = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("/addresses").build())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToMono(new ParameterizedTypeReference<List<AddressResponse>>() {});
		List<AddressResponse> addressList = monoAddressResponse.block();
		System.out.println(objectMapper.writeValueAsString(addressList));
		for(AddressResponse address : addressList) {
			if(Objects.nonNull(address.getAddressId())) {
				if(addressId == address.getAddressId())
					System.out.println(objectMapper.writeValueAsString(address));
				// update call
			}
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}


}
