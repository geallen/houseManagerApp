package com.house.management.controller;

import com.house.management.model.House;
import com.house.management.service.HouseService;
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

    @RequestMapping(value = "/showTodo", method = RequestMethod.GET)
    @ResponseBody
    public String showTodoPage() {
        return "todo";
    }

    @GetMapping(path = "/getAll", produces = { MediaType.APPLICATION_JSON_VALUE })
    //  @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
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
    //@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public @ResponseBody House getHouseByAddress(@Valid @PathVariable String address) {

        House house = houseService.getHouseByAddress(address);
        if(house == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "House with given address not found!");

        }
        return house;

    }

    @DeleteMapping(path = "deleteHouseByAddress/{address}")
  //  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteHouseByAddress(@Valid @PathVariable String address) {

        try {
            houseService.deleteHouseByAddress(address);

            return new ResponseEntity<String>("House with given address deleted successfully!", HttpStatus.OK);

        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return new ResponseEntity<String>("Delete operation failed.Because no house found with given address!", HttpStatus.NOT_FOUND);
        }

    }

    /*@GetMapping(path = "getSubscriberByUsername/{username}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public @ResponseBody Subscriber getSubscriberByUsername(@Valid @PathVariable String username) {

        Subscriber subscriber = subscriberService.getSubscriberByUsername(username);
        if(subscriber == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Subscriber with given username not found!");

        }
        return subscriber;

    }*/


   /* @PostMapping(path = "/addSubscriber", consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addSubscriber(ModelMap model, @Valid @RequestBody Subscriber subscriber) {

        try{
            subscriberService.createOrUpdateTodo(subscriber);
            model.clear();

        } catch (Exception e) {
            Throwable t = e.getCause();
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
            if (t instanceof ConstraintViolationException) {
                return new ResponseEntity<>("Subscriber with username already recorded!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Subscriber recorded successfully!", HttpStatus.OK);

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public @ResponseBody List<Subscriber> getAllSubscribers() {
        return subscriberService.getAllSubscribers();
    }

    @GetMapping(path = "getSubscriberByUsername/{username}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public @ResponseBody Subscriber getSubscriberByUsername(@Valid @PathVariable String username) {

        Subscriber subscriber = subscriberService.getSubscriberByUsername(username);
        if(subscriber == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Subscriber with given username not found!");

        }
        return subscriber;

    }

    @DeleteMapping(path = "deleteSubscriberByUsername/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteSubscriberByUsername(@Valid @PathVariable String username) {

        try {
            subscriberService.deleteSubscriberByUsername(username);

            return new ResponseEntity<String>("Subcriber with givenusername deleted successfully!", HttpStatus.OK);

        } catch (EmptyResultDataAccessException | NoSuchElementException e) {
            return new ResponseEntity<String>("Delete operation failed.Because no subscriber found with given username!", HttpStatus.NOT_FOUND);
        }

    }*/


}
