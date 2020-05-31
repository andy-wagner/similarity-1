package com.br.challenge.similarity.api.domains;

import com.br.challenge.similarity.api.controllers.VectorDomainDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Map;
@JsonDeserialize(using = VectorDomainDeserializer.class)
@Getter
@Setter
public class TagsVectorDomainMap {
    private Map<Integer, TagsVectorDomain> map;
}
