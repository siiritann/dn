package com.example.datanor;

import com.example.datanor.controller.CityController;
import com.example.datanor.exception.ObjectNotFoundException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

//import static com.example.datanor.testcontainers.PostgreSQLTestImages.POSTGRES_TEST_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Testcontainers
public class CityControllerTest extends AbstractTest {

    @Autowired // parentis konfitud
    private MockMvc mockMvc;

    @Autowired
    private CityController cityController;

//    @ClassRule
//    public PostgreSQLContainer postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
//
//    @BeforeEach
//    public void setUp(){
//        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
//    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(cityController).isNotNull();
    }

    @Test
    public void cityNameSearch_WhenExistsOneCity() throws Exception {
//        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))) {
//            postgres.start();
            mockMvc.perform(MockMvcRequestBuilders.get("/cities?name=Tallinn"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id", is(588409)))
                    .andExpect(jsonPath("$[0].name", is("Tallinn")))
                    .andExpect(jsonPath("$[0].countryCode", is("EE")))
                    .andExpect(jsonPath("$[0].stateCode", is("")));
//        }
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

// TODO küsi testide sõltuvuste kohta
//    @Test
//    public void getMyCities() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/cities/add?id=588335"))
//                .andExpect(status().isOk());
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/cities/add?id=588153"))
//                .andExpect(status().isOk());
//        mockMvc.perform(get("/cities/my"))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.*", hasSize(2)))
//                .andExpect(jsonPath("$[*].id", containsInAnyOrder(588153, 588335)));
//    }


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
                .post("/cities/add?id=7522388"))
                .andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/delete?id=7522388"))
                .andExpect(status().isOk())
                .andExpect(content().string("City deleted"));
    }

    @Test
    public void deleteCity_WhenCityDoesntExistPositiveValue() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/cities/delete?id=589782"))
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
                .andExpect(status().isBadRequest());
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
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
    }

    @Test
    public void viewCity_WhenCityIdIsStringNotLong() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/cities/view/abs"))
                .andExpect(status().isBadRequest());
    }

}
