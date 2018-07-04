package com.weburnit.schemaless.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.weburnit.schemaless.schema.ElementPrototype.SourcingInterface;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

public class SchemaDocumentTest {

    private ElementReaderInterfaceImpl reader;
    private ObjectMapper mapper;
    private ObjectNode node;
    private SchemaDocument document;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper();
        reader = new ElementReaderInterfaceImpl();
        node = mapper.createObjectNode();
        String schema = "{\n"
            + "  \"type\": \"object\",\n"
            + "  \"required\": [\n"
            + "    \"age\"\n"
            + "  ],\n"
            + "  \"properties\": {\n"
            + "    \"firstName\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"minLength\": 2,\n"
            + "      \"maxLength\": 20\n"
            + "    },\n"
            + "    \"lastName\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"minLength\": 5,\n"
            + "      \"maxLength\": 15\n"
            + "    },\n"
            + "    \"age\": {\n"
            + "      \"type\": \"integer\",\n"
            + "      \"minimum\": 18,\n"
            + "      \"maximum\": 100\n"
            + "    },\n"
            + "    \"gender\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"enum\": [\n"
            + "        \"Male\",\n"
            + "        \"Female\",\n"
            + "        \"Undisclosed\"\n"
            + "      ]\n"
            + "    },\n"
            + "    \"height\": {\n"
            + "      \"type\": \"integer\"\n"
            + "    },\n"
            + "    \"dateOfBirth\": {\n"
            + "      \"type\": \"string\",\n"
            + "      \"format\": \"date\"\n"
            + "    },\n"
            + "    \"rating\": {\n"
            + "      \"type\": \"integer\"\n"
            + "    },\n"
            + "    \"committer\": {\n"
            + "      \"type\": \"integer\"\n"
            + "    },\n"
            + "    \"address\": {\n"
            + "      \"type\": \"object\",\n"
            + "      \"properties\": {\n"
            + "        \"street\": {\n"
            + "          \"type\": \"string\"\n"
            + "        },\n"
            + "        \"streetnumber\": {\n"
            + "          \"type\": \"string\"\n"
            + "        },\n"
            + "        \"postalCode\": {\n"
            + "          \"type\": \"string\"\n"
            + "        },\n"
            + "        \"city\": {\n"
            + "          \"type\": \"string\"\n"
            + "        }\n"
            + "      }\n"
            + "    }\n"
            + "  }\n"
            + "}";
        ObjectNode data = mapper.readValue(schema, ObjectNode.class);
        document = new SchemaDocument("testing");
        document.bind(data, reader);
    }

    @Test
    public void getSource() throws Exception {
        String data = "{\n"
            + "\t\"firstName\": \"Paul Aan\",\n"
            + "\t\"lastName\": \"Nguyen\",\n"
            + "\t\"age\": 20,\n"
            + "\t\"height\": 20\n"
            + "}";
        ObjectNode request = mapper.readValue(data, ObjectNode.class);
        HashMap<String, Object> attributes = new HashMap<>();
        document.extract(request, new SourcingInterface() {
            @Override
            public void binding(String key, Object value) {
                attributes.put(key, value);
            }
        }, null);

        assert attributes.size() == 4;
    }
}