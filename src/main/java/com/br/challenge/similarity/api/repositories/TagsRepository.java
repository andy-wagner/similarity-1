package com.br.challenge.similarity.api.repositories;

import java.util.List;

public interface TagsRepository {
    List<String> getTags();
    void setTag(String name);
}
