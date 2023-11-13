package de.lion5.spring.dvd.model;


import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors", cascade = {MERGE, PERSIST})
  private List<Movie> movies;
}
