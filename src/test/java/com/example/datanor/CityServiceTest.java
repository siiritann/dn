package com.example.datanor;

import com.example.datanor.controller.CityController;
import com.example.datanor.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityServiceTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private CityController cityController;

//    @MockBean
//    CityController cityController;


    @Test
    void contextLoads() throws Exception {
        assertThat(cityController).isNotNull();
    }


    @Test
    public void getMyCities() throws Exception {
        mockMvc.perform(get("/cities/my"))
                .andExpect(status().isOk());
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
