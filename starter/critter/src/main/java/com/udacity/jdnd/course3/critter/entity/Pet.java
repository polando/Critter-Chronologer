package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

        @Id
        @GeneratedValue
        private long id;

        private PetType type;

        @ManyToOne
        @JoinColumn
        private Customer owner;

        private LocalDate birthDate;
        private String notes;
        private String name;

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

        public Customer getOwner() {
                return owner;
        }

        public void setOwner(Customer owner) {
                this.owner = owner;
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

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
