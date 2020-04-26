package com.rodzyn.homework10testmockmvn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodzyn.homework10testmockmvn.model.Car;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void should_get_cars() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].mark", Is.is("audi")));
    }

    @Test
    void should_get_car_by_id() throws Exception {
        mockMvc.perform(get("/cars/{id}", 1l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mark", Is.is("audi")))
                .andExpect(jsonPath("$.color", Is.is("black")));
    }

    @Test
    void should__not_get_car_by_id() throws Exception {
        mockMvc.perform(get("/cars/{id}", 9l)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); 
    }

    @Test
    void should_get_car_by_color() throws Exception {
        mockMvc.perform(get("/cars/color?color=red"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].mark", Is.is("porsche")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].model", Is.is("i8")));
    }

    @Test
    void should_not_get_car_by_color() throws Exception {
        mockMvc.perform(get("/cars/color?color=yellow")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_car_by_mark() throws Exception {
        mockMvc.perform(get("/cars/mark/{mark}", "audi"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].model", Is.is("A5")));
    }

    @Test
    void should_not_get_car_by_mark() throws Exception {
        mockMvc.perform(get("/cars/mark/{mark}", "star"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_add_car() throws Exception {
//        String json = "{\n" +
//                "  \"id\": 6,\n" +
//                "  \"mark\": \"Polonez\",\n" +
//                "  \"model\": \"Caro\",\n" +
//                "  \"color\": \"blue\"\n" +
//                "}";
        mockMvc.perform(post("/cars")
                .content(asJsonString(new Car(6l, "Polonez", "Caro", "blue")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("true"));
    }

    @Test
    void should_mod_car() throws Exception {
        mockMvc.perform(put("/cars/{id}", 5)
                .content(asJsonString(new Car(5l, "Kia", "Ceed", "white")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Kia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("Ceed")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("white")));
    }

    @Test
    void should_remove_car() throws Exception {
        mockMvc.perform(delete("/cars/5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_not_remove_car() throws Exception {
        mockMvc.perform(delete("/cars/8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}