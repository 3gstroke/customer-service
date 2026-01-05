package org.gstroke.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends PanacheEntity {

    @Column(name = "document_number", nullable = false, unique = true)
    public String documentNumber;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String status; // ACTIVE, INACTIVE
}