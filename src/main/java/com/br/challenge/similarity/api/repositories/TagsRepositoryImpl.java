package com.br.challenge.similarity.api.repositories;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class TagsRepositoryImpl implements TagsRepository {
    private static List<String> tags;

    public TagsRepositoryImpl() {
        tags = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        setTag("neutro");
        setTag("veludo");
        setTag("couro");
        setTag("basics");
        setTag("festa");
        setTag("workwear");
        setTag("inverno");
        setTag("boho");
        setTag("estampas");
        setTag("balada");
        setTag("colorido");
        setTag("casual");
        setTag("liso");
        setTag("moderno");
        setTag("passeio");
        setTag("metal");
        setTag("viagem");
        setTag("delicado");
        setTag("descolado");
        setTag("elastano");
    }

    @Override
    public List<String> getTags() {
        return tags;
    }

    @Override
    public void setTag(String name) {
        tags.add(name);
    }
}
