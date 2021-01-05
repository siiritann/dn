package com.example.datanor;

import com.example.datanor.model.City;
import com.example.datanor.repository.CityRepository;
import com.example.datanor.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class CityServiceTest  {


    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;


 /*  @Test
    public void getMyCities() throws Exception {
        mockMvc.perform(get("/cities/my"))
                .andExpect(status().isOk());
    }*/



    @Test
    public void getMyCities2() throws Exception {
        when(cityRepository.getMyCities()).thenReturn(List.of(new City(123, "Tartu", "EE", "")));
        List <City> result = cityService.getMyCities();
        assertThat(result.size()).isEqualTo(1);
//        assertThat(result.get(0).getName()).isEqualTo("abc");
        verify(cityRepository,times(1)).getMyCities();
    }


    @Test
    public void getCityByName(){
    }

    // TODO WIP
//    @Test
//    public void getMyCities2(){
//        List<City> list = cityService.getMyCities();
//        List<City> expectedCities = new ArrayList<>();
//        City narva = new City();
//        narva.setId(590031);
//        narva.setName("Narva");
//        narva.setCountryCode("EE");
//        expectedCities.add(narva);
//        assertEquals(expectedCities, list);
//    }

}
