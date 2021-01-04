package com.example.datanor;

import com.example.datanor.controller.CityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CityControllerTest extends AbstractTest {

    @Autowired // parentis konfitud
    private MockMvc mockMvc;


    @Autowired
    private CityController cityController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(cityController).isNotNull();
    }

/*    @Test
    public void getAllCities() throws Exception {
        this.mockMvc.perform(get("/cities?name=Tallinn"))
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }*/


    @Test
    public void addTrackedCitySuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=588334"))
                .andExpect(status().isOk());
    }

    @Test
    public void addTrackedCity_ShouldFail_WhenIdIsNegative() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Incorrect city name\"}"));
    }

    @Test
    public void getMyCities() throws Exception {
        mockMvc.perform(get("/cities/my"))
                .andExpect(status().isOk());
    }


}
