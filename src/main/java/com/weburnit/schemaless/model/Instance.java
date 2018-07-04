package com.weburnit.schemaless.model;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "instances")
@NoArgsConstructor
public class Instance {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_modelling", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Modelling modelling;

    @OneToMany(mappedBy = "instance", cascade = CascadeType.ALL)
    private Collection<Attribute> attributes;

    public Instance(Modelling modelling) {
        this.id = UUID.randomUUID();
        this.modelling = modelling;
    }

    public Long getVersion() {
        return this.modelling.getVersion();
    }

    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }
}
