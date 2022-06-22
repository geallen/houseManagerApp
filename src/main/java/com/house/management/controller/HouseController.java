package com.house.management.controller;

import com.house.management.model.House;
import com.house.management.service.HouseService;
import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping(path = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody List<House> getAllHouses() {
        return houseService.getAllHouses();
    }

    @PostMapping(path = "/addHouse",produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<String> addHouse(ModelMap model, @Valid @RequestBody House houseToAdd) {
        try{
            houseService.addHouse(houseToAdd);
            model.clear();

        } catch (Exception e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                return new ResponseEntity<>("House with same properties already recorded!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("House added successfully!", HttpStatus.OK);


    }

    @GetMapping(path = "getHouseByAddress/{address}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody House getHouseByAddress(@Valid @PathVariable String address) {

        House house = houseService.getHouseByAddress(address);
        if(house == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "House with given address not found!");

        }
        return house;
    }

    @DeleteMapping(path = "deleteHouseByAddress/{address}")
    public ResponseEntity<String> deleteHouseByAddress(@Valid @PathVariable String address) {

        try {
            houseService.deleteHouseByAddress(address);

            return new ResponseEntity<String>("House with given address deleted successfully!", HttpStatus.OK);

        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return new ResponseEntity<String>("Delete operation failed.Because no house found with given address!", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "updateHouse/{address}", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<String> updateHouse(@PathVariable String address,
                                               @RequestBody House updatedHouse) {

        try {
            houseService.updateHouse(address, updatedHouse);
            return new ResponseEntity<String>("House updated succesfully!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>("Update failed because no house with given address found!",
                    HttpStatus.NOT_FOUND);
        }
    }

}
