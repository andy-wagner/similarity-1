package com.br.challenge.similarity.api.services;

import com.br.challenge.similarity.api.domains.SimilarityDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomain;
import com.br.challenge.similarity.api.repositories.TagsRepository;
import com.br.challenge.similarity.calculate.EuclideanDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
public class SimilarityServiceImpl implements SimilarityService {
    @Autowired
    TagsRepository repository;

    @Override
    public List<TagsVectorDomain> getVectors(List<TagsVectorDomain> product) {
        if (product.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"product list must not be empty");
        return product.stream().map(v -> setVector(v)).collect(toUnmodifiableList());
    }

    @Override
    public List<SimilarityDomain> getSimilarity(Integer id, Map<Integer, TagsVectorDomain> products) {
        if (products.isEmpty())
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"product list must not be empty");
        var main = products.get(id);
        products.remove(id);
        var ordered = products.values().stream()
                .map(p -> setSimilarity(main.getVectors(),p))
                .sorted(Comparator.comparingDouble(SimilarityDomain::getSimilarity).reversed())
                .limit(3)
                .collect(toList());
        return ordered;

    }

    private SimilarityDomain setSimilarity(List<Integer> vector, TagsVectorDomain product) {
        if (isNull(vector) || isNull(product) || isNull(product.getVectors()))
            return new SimilarityDomain(product.getId(),product.getName(),0.0);
        var similarity = EuclideanDistance.start().setFirstVector(vector).setSecondVector(product.getVectors()).calculate();
        return new SimilarityDomain(product.getId(),product.getName(),similarity);
    }

    private TagsVectorDomain setVector(TagsVectorDomain product) {
        var vector = repository.getTags().stream().map(t -> hasTag(product, t)).collect(toList());
        product.setVectors(vector);
        return product;
    }

    private int hasTag(TagsVectorDomain product, String tag) {
        return product.getTags().contains(tag) ? 1 : 0;
    }
}
