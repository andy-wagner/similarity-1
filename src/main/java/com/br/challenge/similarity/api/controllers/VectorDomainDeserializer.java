package com.br.challenge.similarity.api.controllers;

import com.br.challenge.similarity.api.domains.TagsVectorDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomainMap;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VectorDomainDeserializer extends JsonDeserializer {
    @Override
    public TagsVectorDomainMap deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = parser.getCodec();
        JsonNode jsonNode = objectCodec.readTree(parser);
        if (!jsonNode.isArray())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        var map = new HashMap<Integer,TagsVectorDomain>();
        var mapper = new ObjectMapper();
        var elements = jsonNode.elements();
        elements.forEachRemaining(node -> {
            try {
                var value = mapper.readValue(node.traverse(), TagsVectorDomain.class);
                map.put(value.getId(),value);
            } catch (IOException e) {
                e.printStackTrace();
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
        });
        var result = new TagsVectorDomainMap();
        result.setMap(map);
        return result;
    }
}
