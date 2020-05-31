package com.br.challenge.similarity.api.controllers;

import com.br.challenge.similarity.api.domains.SimilarityDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomain;
import com.br.challenge.similarity.api.services.SimilarityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SimilarityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SimilarityService service;

    @Test
    public void testVectorsReturn200() throws Exception {
        var domain = new TagsVectorDomain(1,"test", Arrays.asList("xpto"));
        domain.setVectors(Arrays.asList(1));

        when(service.getVectors(any())).thenReturn(Arrays.asList(domain));

        var call = mockMvc.perform(post("/v1/vectors")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Arrays.asList(domain))));

        call.andExpect(status().isOk());
        call.andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(domain))));
    }

    @Test
    public void testVectorsReturn400() throws Exception {
        var domain = new TagsVectorDomain(1,"test", Arrays.asList("xpto"));

        when(service.getVectors(any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        var call = mockMvc.perform(post("/v1/vectors")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Arrays.asList(domain))));

        call.andExpect(status().isBadRequest());
    }

    @Test
    public void testVectorsReturn500() throws Exception {
        var domain = new TagsVectorDomain(1,"test", Arrays.asList("xpto"));

        when(service.getVectors(any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        var call = mockMvc.perform(post("/v1/vectors")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Arrays.asList(domain))));

        call.andExpect(status().isInternalServerError());
    }

    @Test
    public void testSimilarityReturn200() throws Exception {
        var domain = new TagsVectorDomain(1,"test", Arrays.asList("xpto"));
        domain.setVectors(Arrays.asList(1));

        var similarity = new SimilarityDomain(1,"test",1.0);

        when(service.getSimilarity(any(),any())).thenReturn(Arrays.asList(similarity));

        var call = mockMvc.perform(post("/v1/similarity/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Arrays.asList(domain))));

        call.andExpect(status().isOk());
    }

    @Test
    public void testSimilarityReturn400() throws Exception {
        var domain = new TagsVectorDomain(1,"test", Arrays.asList("xpto"));

        when(service.getSimilarity(any(),any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        var call = mockMvc.perform(post("/v1/similarity/{id}",1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Arrays.asList(domain))));

        call.andExpect(status().isBadRequest());
    }

    @Test
    public void testSimilarityReturn500() throws Exception {
        var domain = new TagsVectorDomain(1,"test", Arrays.asList("xpto"));

        when(service.getSimilarity(any(),any())).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        var call = mockMvc.perform(post("/v1/similarity/{id}",1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Arrays.asList(domain))));

        call.andExpect(status().isInternalServerError());
    }
}
