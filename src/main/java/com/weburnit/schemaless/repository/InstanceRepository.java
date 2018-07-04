package com.weburnit.schemaless.repository;

import com.weburnit.schemaless.model.Instance;
import com.weburnit.schemaless.model.Modelling;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstanceRepository extends JpaRepository<Instance, UUID> {

}
