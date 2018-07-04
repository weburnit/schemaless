package com.weburnit.schemaless.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import com.weburnit.schemaless.schema.SchemaDocument;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@TypeDefs({
    @TypeDef(name = "string-array", typeClass = StringArrayType.class),
    @TypeDef(name = "int-array", typeClass = IntArrayType.class),
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
    @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
    @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
@Entity
@Table(name = "modelling")
@NoArgsConstructor
public class Modelling {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @Column(unique = true)
    private String name;

    @Column
    private Long version;

    @Column(columnDefinition = "jsonb")
    @Type(type = "jsonb-node")
    private JsonNode body;

    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date creationDate;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastModifiedDate;

    @OneToMany(mappedBy = "modelling")
    Collection<Instance> instances;

    public Modelling(String name, JsonNode body, Long version) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.version = version;
        this.body = body;
    }

    public Long getVersion() {
        return version;
    }

    public JsonNode getBody() {
        return body;
    }

    public String getName() {
        return name;
    }
}
