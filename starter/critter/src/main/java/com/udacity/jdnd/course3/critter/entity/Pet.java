package com.udacity.jdnd.course3.critter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udacity.jdnd.course3.critter.pet.PetType;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Create Entity for Pet
 */
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private PetType type;

    @Nationalized
    private String name;

    private LocalDate birthDate;
    private String notes;

    @JsonIgnore
    @ManyToOne
    private Customer customer;

    public Pet(PetType type, String name, LocalDate birthDate, String notes) {
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Pet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
