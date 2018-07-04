package com.weburnit.schemaless.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class Home {

    @RequestMapping(value = "/", produces = APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    Version home() {
        return new Version("0.0.1", "Schema-Less Service");
    }
}
