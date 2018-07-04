package com.weburnit.schemaless;

import com.weburnit.schemaless.schema.ElementReaderInterface;
import com.weburnit.schemaless.schema.ElementReaderInterfaceImpl;
import com.weburnit.schemaless.service.SchemaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebMvc
public class SchemalessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchemalessApplication.class, args);
    }

    @Bean
    ElementReaderInterface elementReaderInterface() {
        return new ElementReaderInterfaceImpl();
    }

    @Bean
    SchemaService schemaService() {
        return new SchemaService();
    }
}
