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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void deletePatientById_norecord() throws Exception {
        Mockito.when(houseRepository.getHouseByAddress(house2.getAddress())).thenReturn(null);


        //doNothing().when(houseRepository).deleteById(anyInt());
      //  houseService.deleteHouseByAddress("house2.getAddress()");

        verify(houseService, times(1)).deleteHouseByAddress("house2.getAddress()");

    //    ResponseEntity<String> responseEntity = houseService.deleteHouseByAddress("house2.getAddress()");

       /* assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("House with given address deleted successfully!", responseEntity.getBody());
*/

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/deleteHouseByAddress/house2.getAddress()")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                /*.andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof NotFoundException))*/
                .andExpect(result ->
                        assertEquals("Patient with ID 5 does not exist.", result.getResolvedException().getMessage()));
    }


}
