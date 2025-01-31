package uk.co.sainsburys.serviceconnector;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public record Book(@Id Long id, String name) {}
