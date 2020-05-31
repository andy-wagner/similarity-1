package com.br.challenge.similarity.api.domains;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class TagsVectorDomain {
    @NonNull
    Integer id;

    @NonNull
    String name;

    @NonNull
    List<String> tags;

    List<Integer> vectors;
}
