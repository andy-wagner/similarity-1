package com.br.challenge.similarity.api.services;

import com.br.challenge.similarity.api.domains.SimilarityDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomain;

import java.util.List;
import java.util.Map;

public interface SimilarityService {
    List<TagsVectorDomain> getVectors(List<TagsVectorDomain> values);
    List<SimilarityDomain> getSimilarity(Integer id, Map<Integer, TagsVectorDomain> values);
}
