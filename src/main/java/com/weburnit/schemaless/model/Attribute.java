package com.weburnit.schemaless.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "attributes")
@NoArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, targetEntity = Instance.class)
    @JoinColumn(name = "fk_instance", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Instance instance;

    @Column(name = "field")
    String field;

    @Column(name = "value")
    String value;

    @Column(name = "version")
    Long version;

    public Attribute(Instance instance, String field, Object value) {
        this.instance = instance;
        this.field = field;
        this.value = value.toString();
        this.version = instance.getVersion();
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
