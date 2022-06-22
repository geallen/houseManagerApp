package com.house.management.controller;

import com.house.management.model.House;
import com.house.management.service.HouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class HouseControllerTest {
    @InjectMocks
    HouseController houseController;

    @Mock
    HouseService houseService;
    House house1;
    House house2;
    House house3;

    @BeforeEach
    void setUp(){
        house1 = new House(1, 2, 23, "85632 Munich");
        house2 = new House(1, 2, 23, "86541 Berlin");
        house3 = new House(1, 2, 23, "12456 Hamburg");
    }

    @Test
    public void testGetAllHouses() throws Exception {

        List<House> houseList = new ArrayList<>(Arrays.asList(house1, house2, house3));

        Mockito.when(houseService.getAllHouses()).thenReturn(houseList);

        List<House> houseListTest = houseController.getAllHouses();

        assertEquals(3, houseList.size());

        assertEquals("Address should be 85632 Munich", house1.getAddress(), houseListTest.get(0).getAddress());
        assertEquals("Size should be 23", house2.getSize(), houseListTest.get(1).getSize());

    }

    @Test
    public void testGetHouseByAddressWhenHouseExists() throws Exception {
        Mockito.when(houseService.getHouseByAddress(house1.getAddress())).thenReturn(house1);

        House houseTest = houseController.getHouseByAddress(house1.getAddress());

        assertEquals(23, houseTest.getSize());
        assertEquals(1, houseTest.getRoomNumber());
        assertEquals(2, houseTest.getFloor());

    }

    @Test
    public void testGetHouseByAddressWhenHouseNotExists() throws Exception {
        Mockito.when(houseService.getHouseByAddress("Izmir Turkey")).thenReturn(null);

        ResponseStatusException thrown = Assertions
                .assertThrows(ResponseStatusException.class, () -> {
                    houseController.getHouseByAddress("Izmir Turkey");
                }, "House with given address not found!");

        Assertions.assertEquals("House with given address not found!", thrown.getReason());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());

    }

    @Test
    public void testAddHouse() throws Exception {
        House record = new House(5, 3, 154, "45231 Ulm");

        Mockito.when(houseService.addHouse(record)).thenReturn(record);
        ModelMap model = new ModelMap();
        ResponseEntity<String> responseEntity = houseController.addHouse(model, record);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("House added successfully!", responseEntity.getBody());

    }



    @Test
    public void testDeleteHouseWhenHouseExists() throws Exception {
        doNothing().when(houseService).deleteHouseByAddress(anyString());
        houseController.deleteHouseByAddress(house2.getAddress());

        verify(houseService, times(1)).deleteHouseByAddress("86541 Berlin");

        ResponseEntity<String> responseEntity = houseController.deleteHouseByAddress(house2.getAddress());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("House with given address deleted successfully!", responseEntity.getBody());

    }

    @Test
    public void testDeleteHouseWhenHouseNotExists() throws NoSuchElementException {
        doThrow(new NoSuchElementException()).when(houseService).deleteHouseByAddress("Izmir Turkey");

        ResponseEntity<String> responseEntity = houseController.deleteHouseByAddress("Izmir Turkey");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Delete operation failed.Because no house found with given address!", responseEntity.getBody());

    }

}