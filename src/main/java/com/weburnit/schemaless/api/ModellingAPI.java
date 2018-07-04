package com.weburnit.schemaless.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.weburnit.schemaless.model.Modelling;
import com.weburnit.schemaless.schema.SchemaAssertionException;
import com.weburnit.schemaless.service.SchemaService;
import java.rmi.activation.UnknownObjectException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class ModellingAPI {

    @Autowired
    SchemaService schemaService;

    @RequestMapping(value = "/model", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public Modelling createModelling(@Valid @RequestBody JsonNode data)
        throws UnknownObjectException {
        return schemaService.createModel(data);
    }

    @RequestMapping(value = "/model/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Object createInstance(@PathVariable String id, @RequestBody JsonNode data)
        throws UnknownObjectException, SchemaAssertionException {
        return schemaService.createInstance(data, id);
    }
}
