package com.weburnit.schemaless.repository;

import com.weburnit.schemaless.model.Attribute;
import com.weburnit.schemaless.model.Instance;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

}
