package com.weburnit.schemaless.repository;

import com.weburnit.schemaless.model.Modelling;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModellingRepository extends JpaRepository<Modelling, UUID> {

    Optional<Modelling> findByName(String name);
}
