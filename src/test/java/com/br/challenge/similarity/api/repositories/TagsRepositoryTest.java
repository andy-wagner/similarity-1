package com.br.challenge.similarity.api.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TagsRepositoryTest {
    @Autowired
    TagsRepository repository;

    @Test
    public void testInitiation() {
        var tags = repository.getTags();
        assertThat(tags).isNotNull();
        assertThat(tags.isEmpty()).isFalse();
        assertThat(tags.contains("neutro"));
        assertThat(tags.contains("veludo"));
        assertThat(tags.contains("couro"));
        assertThat(tags.contains("basics"));
        assertThat(tags.contains("festa"));
        assertThat(tags.contains("workwear"));
        assertThat(tags.contains("inverno"));
        assertThat(tags.contains("boho"));
        assertThat(tags.contains("estampas"));
        assertThat(tags.contains("balada"));
        assertThat(tags.contains("colorido"));
        assertThat(tags.contains("casual"));
        assertThat(tags.contains("liso"));
        assertThat(tags.contains("moderno"));
        assertThat(tags.contains("passeio"));
        assertThat(tags.contains("metal"));
        assertThat(tags.contains("viagem"));
        assertThat(tags.contains("delicado"));
        assertThat(tags.contains("descolado"));
        assertThat(tags.contains("elastano"));
    }

    @Test
    public void testAddValue() {
        repository.setTag("XPTO");
        var tags = repository.getTags();
        assertThat(tags.size()).isEqualTo(21);
        assertThat(tags.contains("XPTO"));
    }
}
