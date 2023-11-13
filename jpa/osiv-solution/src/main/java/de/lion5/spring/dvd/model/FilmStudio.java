package de.lion5.spring.dvd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FilmStudio {

  @Id
  private Long id;
  private String name;
  private LocalDate since;
  @JsonIgnore
  @OneToMany(mappedBy = "filmStudio")
  private List<Movie> movies;
}
