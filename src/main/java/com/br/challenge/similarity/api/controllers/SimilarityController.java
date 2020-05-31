package com.br.challenge.similarity.api.controllers;

import com.br.challenge.similarity.api.domains.SimilarityDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomainMap;
import com.br.challenge.similarity.api.services.SimilarityService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping()
public class SimilarityController {
    @Autowired
    SimilarityService service;

    @PostMapping("/v1/vectors")
    public ResponseEntity<List<TagsVectorDomain>> vectors(@RequestBody @NotEmpty List<@Valid TagsVectorDomain> domains) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getVectors(domains));
    }

    @PostMapping("/v1/similarity/{id}")
    public ResponseEntity<List<SimilarityDomain>> similarity(
            @PathVariable("id") Integer id,
            @RequestBody TagsVectorDomainMap domains
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getSimilarity(id,domains.getMap()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public @ResponseBody ResponseEntity<String> handler(HttpClientErrorException e) {
        return ResponseEntity.status(e.getRawStatusCode()).body(e.getMessage());
    }
}
