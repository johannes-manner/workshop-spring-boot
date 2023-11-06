package de.lion5.spring.dvd.model;


import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Actor {

  @Id
  private Long id;
  private String name;
  private boolean wonOscar;
  private LocalDate birthday;
  // default fetch type: LAZY
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "actors", cascade = {MERGE, PERSIST})
  private List<Movie> movies;
}
