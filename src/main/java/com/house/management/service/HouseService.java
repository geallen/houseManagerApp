package com.house.management.service;

import com.house.management.model.House;
import com.house.management.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    public House addHouse(House houseToAdd) {

        houseRepository.save(houseToAdd);
        return houseToAdd;
    }

    public List<House> getAllHouses(){
        List<House> houseList = houseRepository.findAll();
        if(houseList.size()> 0) {
            return houseList;
        }
        return new ArrayList<House>();
    }

    public House getHouseByAddress(String address){

        return houseRepository.getHouseByAddress(address);
    }

    public void deleteHouseByAddress(String address){
        House houseWithGivenAddress = houseRepository.getHouseByAddress(address);

        if(houseWithGivenAddress != null) {
            houseRepository.deleteById(houseWithGivenAddress.getId());
        }else {
            throw new NoSuchElementException();
        }

    }
    /* public House getSubscriberByUsername(String username){

        return subscriberRepository.findByUsername(username);
    }

    public void deleteSubscriberByUsername(String username){
        House subscriberWithGivenName = subscriberRepository.findByUsername(username);

        if(subscriberWithGivenName != null) {
            subscriberRepository.deleteById(subscriberWithGivenName.getId());
        }else {
            throw new NoSuchElementException();
        }

    }
*/
}
