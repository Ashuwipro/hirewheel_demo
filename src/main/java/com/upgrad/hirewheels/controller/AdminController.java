package com.upgrad.hirewheels.controller;

import com.upgrad.hirewheels.dto.VehicleDTO;
import com.upgrad.hirewheels.entities.Vehicle;
import com.upgrad.hirewheels.services.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hirewheels/v1")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(value = "/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity AddVehicle(@RequestBody VehicleDTO vehicleDTO) throws Exception {

        Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);

        Vehicle savedVehicle = adminService.registerVehicle(vehicle);

        VehicleDTO savedVehicleDTO = modelMapper.map(savedVehicle, VehicleDTO.class);
        return new ResponseEntity(savedVehicleDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/vehicles/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity UpdateVehicle(@PathVariable(value = "id") int id,
                                        @RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = adminService.changeAvailability(id, vehicleDTO.getAvailabilityStatus());
        VehicleDTO response = modelMapper.map(vehicle, VehicleDTO.class);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }
}
