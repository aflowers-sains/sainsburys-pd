package uk.co.sainsburys.serviceconnector;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  @Id private Long id;
  private String name;

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
