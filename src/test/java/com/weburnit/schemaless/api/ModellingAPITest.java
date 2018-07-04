package com.weburnit.schemaless.api;

import static org.junit.Assert.assertEquals;

import com.weburnit.schemaless.SchemalessApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SchemalessApplication.class})
public class ModellingAPITest {

    @Autowired
    WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();
    }

    @Test
    public void testCreateModel() throws Exception {
        RequestBuilder create = MockMvcRequestBuilders.post("/model")
            .content("{}")
            .contentType(MediaType.APPLICATION_JSON_VALUE);
        MockHttpServletResponse response = mockMvc.perform(create).andReturn()
            .getResponse();
        assertEquals(200, response.getStatus());
    }
}