package com.example.cloudnative;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CloudNativeApplicationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CatRepository catRepository;

    @BeforeEach
    public void before() throws Exception {
        Stream.of("Felix", "Garfield", "Whiskers")
                .forEach(n -> catRepository.save(new Cat(n)));
        System.out.println("before completed");
    }

    @Test
    public void catsReflectedInRead() throws Exception {
        MediaType mediaType = MediaType.parseMediaType("application/hal+json");
        this.mvc
                .perform(get("/cats"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(
                        mvcResult -> {
                            String contentAsString = mvcResult.getResponse().getContentAsString();
                            Assertions.assertTrue(contentAsString.split("totalElements")[1]
                                    .split(":")[1].trim()
                                    .split(",")[0].equals("3"));
                        });
    }

}
