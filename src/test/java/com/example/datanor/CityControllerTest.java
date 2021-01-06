package com.example.datanor;

import com.example.datanor.controller.CityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CityControllerTest extends AbstractTest {

    @Autowired // parentis konfitud
    private MockMvc mockMvc;

    @Autowired
    private CityController cityController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(cityController).isNotNull();
    }

    @Test
    public void cityNameSearch_WhenExistsOneCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cities?name=Tallinn"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":588409,\"name\":\"Tallinn\",\"countryCode\":\"EE\",\"stateCode\":\"\"}]"));
    }

    @Test
    public void cityNameSearch_WhenExistsMultipleCities() throws Exception {
        mockMvc.perform(get("/cities?name=Türi"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(588153, 7522474)));
    }

    @Test
    public void cityNameSearch_WhenNotExistsCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cities?name=qwerty"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    public void getMyCities() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=588335"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=588153"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/cities/my"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(588153, 588335)));
    }


    @Test
    public void addTrackedCity_WhenIdIsPositive() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=163342"))
                .andExpect(status().isOk());
    }

    @Test
    public void addTrackedCity_WhenIdIsLessThanOne() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Incorrect city name\"}"));
    }

    @Test
    public void addTrackedCity_WhenCityAlreadyExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=588334"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=588334"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"This city already is in your watchlist\"}"));
    }


    @Test
    public void deleteCity_WhenCityExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=588334"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/delete?id=588334"))
                .andExpect(status().isOk())
                .andExpect(content().string("City deleted"));
    }

    @Test
    public void deleteCity_WhenCityDoesntExistPositiveValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/delete?id=588334"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Something went wrong\"}"));
    }

    @Test
    public void deleteCity_WhenCityDoesntExistNegativeValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/delete?id=-134"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Something went wrong\"}"));
    }

    @Test
    public void deleteCity_WhenCityIdIsMissingFromURL() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/delete"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"Something went wrong\"}"));
    }


    @Test
    public void viewCity_WhenCityExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/cities/add?id=589576"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/cities/view/589576"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pärnumaa"));
    }

    @Test
    public void viewCity_WhenCityDoesntExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cities/view/-589578"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"message\": \"Incorrect result size: expected 1, actual 0\"}"));
    }

    @Test
    public void viewCity_WhenCityIdIsMissingFromURL() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cities/view/"))
                .andExpect(status().isNotFound());
    }

}
