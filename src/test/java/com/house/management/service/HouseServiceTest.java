package com.house.management.service;

import com.house.management.model.House;
import com.house.management.repository.HouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class HouseServiceTest {

    @InjectMocks
    HouseService houseService;

    @Mock
    HouseRepository houseRepository;

    House house1;
    House house2;
    House house3;

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        house1 = new House(1, 2, 23, "85632 Munich");
        house2 = new House(1, 2, 23, "86541 Berlin");
        house3 = new House(1, 2, 23, "12456 Hamburg");
    }


    @Test
    public void testGetAllHouses() throws Exception {

        List<House> houseList = new ArrayList<>(Arrays.asList(house1, house2, house3));

        Mockito.when(houseRepository.findAll()).thenReturn(houseList);

        List<House> houseListTest = houseService.getAllHouses();

        assertEquals(3, houseList.size());

        assertEquals("Address should be 85632 Munich", house1.getAddress(), houseListTest.get(0).getAddress());
        assertEquals("Size should be 23", house2.getSize(), houseListTest.get(1).getSize());

    }

    @Test
    public void testGetHouseByAddressWhenHouseExists() throws Exception {
        Mockito.when(houseRepository.getHouseByAddress(house1.getAddress())).thenReturn(house1);

        House houseTest = houseService.getHouseByAddress(house1.getAddress());

        assertEquals(23, houseTest.getSize());
        assertEquals(1, houseTest.getRoomNumber());
        assertEquals(2, houseTest.getFloor());

    }

    @Test
    public void testGetHouseByAddressWhenHouseNotExists() throws Exception {
        Mockito.when(houseRepository.getHouseByAddress("Izmir Turkey")).thenReturn(null);

        House houseTest = houseService.getHouseByAddress("Izmir Turkey");

        assertNull(houseTest);
    }

    @Test
    public void testAddHouse() throws Exception {
        House record = new House(5, 3, 154, "45231 Ulm");

        Mockito.when(houseRepository.save(record)).thenReturn(record);
        House addedHouse = houseService.addHouse(record);

        assertEquals(record.getAddress(), addedHouse.getAddress());
        assertEquals(record.getRoomNumber(), addedHouse.getRoomNumber());
        assertEquals(record.getFloor(), addedHouse.getFloor());
        assertEquals(record.getSize(), addedHouse.getSize());

    }


    @Test
    public void testDeleteHouseWhenHouseExists() throws Exception {
        when(houseRepository.getHouseByAddress(anyString())).thenReturn(house2);

        houseService.deleteHouseByAddress(house2.getAddress());

        verify(houseRepository, times(1)).deleteById(house2.getId());

    }

    @Test
    public void testDeleteHouseWhenHouseNotExists() throws Exception {

        when(houseRepository.getHouseByAddress(house2.getAddress())).thenReturn(null);
        Throwable exception = assertThrows(NoSuchElementException.class, () -> houseService.deleteHouseByAddress(house2.getAddress()));
        assertEquals(NoSuchElementException.class, exception.getClass());


    }

}
