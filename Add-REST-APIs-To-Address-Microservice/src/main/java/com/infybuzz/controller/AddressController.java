package com.infybuzz.controller;

import com.infybuzz.request.CreateAddressRequest;
import com.infybuzz.response.AddressResponse;
import com.infybuzz.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	AddressService addressService;

	@PostMapping("/create")
	public AddressResponse createAddress (@RequestBody CreateAddressRequest createAddressRequest) {
		return addressService.createAddress(createAddressRequest);
	}
	
	@GetMapping("/getById/{id}")
	public AddressResponse getById(@PathVariable long id) {
		return addressService.getById(id);
	}

	@GetMapping("/addresses")
	public ResponseEntity<Flux<AddressResponse>> getAddresses() {
		return new ResponseEntity(addressService.getAllAddresses(), HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateAddress(@PathVariable long id, @RequestBody CreateAddressRequest addressRequest) {
		return addressService.updateAddress(id, addressRequest);
	}
	
}
