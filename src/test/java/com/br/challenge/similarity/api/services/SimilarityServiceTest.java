package com.br.challenge.similarity.api.services;

import com.br.challenge.similarity.api.domains.SimilarityDomain;
import com.br.challenge.similarity.api.domains.TagsVectorDomain;
import com.br.challenge.similarity.api.repositories.TagsRepository;
import com.br.challenge.similarity.calculate.EuclideanDistance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimilarityServiceTest {
    @Autowired
    SimilarityService service;

    @MockBean
    TagsRepository repository;

    @Before
    public void init() {
        when(repository.getTags()).thenReturn(Arrays.asList("tag1","tag2","tag3","tag4"));
    }

    @Test
    public void testOneProductVector() {
        var tags = Arrays.asList("tag1","tag3");
        var domain = new TagsVectorDomain(999,"Test Product",tags);
        var result = service.getVectors(Arrays.asList(domain));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(999);
        assertThat(result.get(0).getName()).isEqualTo("Test Product");
        assertThat(result.get(0).getTags()).containsExactly("tag1","tag3");
        assertThat(result.get(0).getVectors()).containsExactly(1,0,1,0);
    }

    @Test
    public void testOneProductVectorWongTags() {
        var tags = Arrays.asList("tag5","tag6");
        var domain = new TagsVectorDomain(999, "Test Product", tags);
        var result = service.getVectors(Arrays.asList(domain));
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(999);
        assertThat(result.get(0).getName()).isEqualTo("Test Product");
        assertThat(result.get(0).getTags()).containsExactly("tag5","tag6");
        assertThat(result.get(0).getVectors()).containsExactly(0,0,0,0);
    }

    @Test
    public void testManyProductVector() {
        var domains = new ArrayList<TagsVectorDomain>();
        Stream.of(1,2,3,4).forEach(i -> {
            List tags;
            switch (i) {
                case 1:
                    tags  = Arrays.asList("tag1","tag2","tag3","tag4");
                    break;
                case 2:
                    tags  = Arrays.asList("tag1","tag3");
                    break;
                case 3:
                    tags  = Arrays.asList("tag5","tag6");
                    break;
                default:
                    tags = new ArrayList();
            }
            domains.add(new TagsVectorDomain(i, "Test Product "+i, tags));
        });

        var result = service.getVectors(domains);

        assertThat(result.size()).isEqualTo(4);

        result.stream().forEach(r -> {
            switch (r.getId()) {
                case 1:
                    assertThat(r.getName()).isEqualTo("Test Product 1");
                    assertThat(r.getTags()).containsExactly("tag1","tag2","tag3","tag4");
                    assertThat(r.getVectors()).containsExactly(1,1,1,1);
                    break;
                case 2:
                    assertThat(r.getName()).isEqualTo("Test Product 2");
                    assertThat(r.getTags()).containsExactly("tag1","tag3");
                    assertThat(r.getVectors()).containsExactly(1,0,1,0);
                    break;
                case 3:
                    assertThat(r.getName()).isEqualTo("Test Product 3");
                    assertThat(r.getTags()).containsExactly("tag5","tag6");
                    assertThat(r.getVectors()).containsExactly(0,0,0,0);
                    break;
                case 4:
                    assertThat(r.getId()).isEqualTo(4);
                    assertThat(r.getName()).isEqualTo("Test Product 4");
                    assertThat(r.getTags()).isEmpty();
                    break;
            }
        });
    }

    @Test
    public void testVectorsListNull() {
        assertThatThrownBy(() -> service.getVectors(new ArrayList<>()))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("product list must not be empty");
    }

    @Test
    public void tesSameSimilarity() {
        var map = new HashMap<Integer,TagsVectorDomain>();
        map.put(999, new TagsVectorDomain(999,"Test Product 1",Arrays.asList("tag1","tag3"),Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1,1)));
        map.put(998, new TagsVectorDomain(998,"Test Product 2",Arrays.asList("tag1","tag3"),Arrays.asList(1,1,1,1,1,1,0,1,1,1,1,0)));
        map.put(997, new TagsVectorDomain(997,"Test Product 3",Arrays.asList("tag1","tag3"),Arrays.asList(0,0,0,0,0,0,0,0,0,1,1,1)));
        map.put(996, new TagsVectorDomain(996,"Test Product 4",Arrays.asList("tag1","tag3"),Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0)));
        map.put(995, new TagsVectorDomain(995,"Test Product 5",Arrays.asList("tag1","tag3"),Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1)));
        map.put(996, new TagsVectorDomain(996,"Test Product 6",Arrays.asList("tag1","tag3"),Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0)));
        map.put(994, new TagsVectorDomain(994,"Test Product 7",Arrays.asList("tag1","tag3"),Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1,1)));
        var result = service.getSimilarity(999, map);
        var similarities = Arrays.asList(
                new SimilarityDomain(995,"Test Product 5", 1.0),
                new SimilarityDomain(994,"Test Product 7", 1.0),
                new SimilarityDomain(998,"Test Product 2", 0.4142135623730951)
        );
        assertThat(result).containsAll(similarities);
    }

    @Test
    public void tesNoneSimilarity() {
        var map = new HashMap<Integer,TagsVectorDomain>();
        map.put(999, new TagsVectorDomain(999,"Test Product 1",Arrays.asList("tag1","tag3"),Arrays.asList(1,1)));
        map.put(998, new TagsVectorDomain(998,"Test Product 2",Arrays.asList("tag1","tag3"),Arrays.asList(0,0)));
        var result = service.getSimilarity(999, map);
        var similarity = EuclideanDistance.start().setFirstVector(Arrays.asList(1,1)).setSecondVector(Arrays.asList(0,0)).calculate();
        var expected = new SimilarityDomain(998,"Test Product 2", similarity);
        assertThat(result).containsExactly(expected);
    }

    @Test
    public void testWithoutVectorSimilarity() {
        var map = new HashMap<Integer,TagsVectorDomain>();
        map.put(999, new TagsVectorDomain(999,"Test Product 1",Arrays.asList("tag1","tag3")));
        map.put(998, new TagsVectorDomain(998,"Test Product 2",Arrays.asList("tag1","tag3")));
        var result = service.getSimilarity(999, map);
        var similarity = new SimilarityDomain(998,"Test Product 2", 0.0);
        assertThat(result).containsExactly(similarity);
    }

    @Test
    public void testOnlyMainProductSimilarity() {
        var tags = Arrays.asList("tag1","tag3");
        var map = new HashMap<Integer,TagsVectorDomain>();

        map.put(999, new TagsVectorDomain(999,"Test Product",tags));
        var result = service.getSimilarity(999, map);
        assertThat(result).isEmpty();
    }

    @Test
    public void testSimilarityListNull() {
        assertThatThrownBy(() -> service.getSimilarity(1, new HashMap<>()))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessageContaining("product list must not be empty");
    }
}
