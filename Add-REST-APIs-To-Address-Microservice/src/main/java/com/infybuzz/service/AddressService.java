package com.infybuzz.service;

import com.infybuzz.entity.Address;
import com.infybuzz.repository.AddressRepository;
import com.infybuzz.request.CreateAddressRequest;
import com.infybuzz.response.AddressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService {

	Logger logger = LoggerFactory.getLogger(AddressService.class);
	
	@Autowired
	AddressRepository addressRepository;

	public AddressResponse createAddress(CreateAddressRequest createAddressRequest) {
		
		Address address = new Address();
		address.setStreet(createAddressRequest.getStreet());
		address.setCity(createAddressRequest.getCity());
		
		addressRepository.save(address);
		
		return new AddressResponse(address);
		
	}
	
	public AddressResponse getById (long id) {
		
		logger.info("Inside getById " + id);
		
		Address address = addressRepository.findById(id).get();
		
		return new AddressResponse(address);
	}

	private AddressResponse mapToAddressResponse(Address address) {
		AddressResponse addressResponse = new AddressResponse(address);
		return addressResponse;
	}

	public Flux<AddressResponse> getAllAddresses() {
		List<Address> addressList = addressRepository.findAll();
		List<AddressResponse> addressResponseList = new ArrayList<>();

		Mono<List<Address>> addressResponseMono = Mono.just(addressList);
		Flux<AddressResponse> fluxAddressResponse = addressResponseMono.flatMapMany(Flux::fromIterable)
		.map(address -> mapToAddressResponse(address))
				.doFinally(s -> logger.debug("Exiting getAllAddress() :: {}", AddressService.class.getName()));
		return fluxAddressResponse;
	}

	public ResponseEntity updateAddress(long id, CreateAddressRequest addressRequest) {
		Optional<Address> optionalAddress = addressRepository.findById(id);
		if(Objects.isNull(optionalAddress)) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Address addressInDB = optionalAddress.get();
		addressInDB.setCity(addressRequest.getCity());
		addressInDB.setStreet(addressRequest.getStreet());
		addressRepository.save(addressInDB);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
}
