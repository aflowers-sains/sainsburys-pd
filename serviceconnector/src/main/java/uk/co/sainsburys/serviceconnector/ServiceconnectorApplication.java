package uk.co.sainsburys.serviceconnector;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceconnectorApplication {

  @Autowired
  private BookRepository bookRepository;

  public static void main(String[] args) {
    SpringApplication.run(ServiceconnectorApplication.class, args);
  }

  @PostConstruct
  public void init() {
    LocalDateTime nineAMGMT = LocalDateTime.of(2025, 3, 29, 9, 0);
    LocalDateTime nineAMBST = LocalDateTime.of(2025, 3, 30, 9, 0);

    System.out.println("Current time in GMT: " + nineAMGMT);
    System.out.println("Current time in BST: " + nineAMBST);

    System.out.println("There are initially " + bookRepository.count() + " books in the database.");
    bookRepository.save(new Book(1L, "The Hitchhiker's Guide to the Galaxy"));
    bookRepository.save(new Book(2L, "The Restaurant at the End of the Universe"));
    System.out.println("There are now " + bookRepository.count() + " books in the database.");

    bookRepository.findAll().forEach(System.out::println);
  }
}
