package com.br.challenge.similarity.api.domains;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class SimilarityDomain {
    @NonNull
    Integer id;

    @NonNull
    String name;

    Double similarity;
}
