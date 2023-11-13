package de.lion5.spring.dvd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedEntityGraph(name = "Movie.actors",
    attributeNodes = @NamedAttributeNode(value = "actors"))
@NamedEntityGraph(name = "Movie.actorsFilmStudio",
    attributeNodes = {@NamedAttributeNode(value = "actors"),
        @NamedAttributeNode(value = "filmStudio")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {

  @Id
  private Long id;
  @NotNull(message = "Title must be set")
  @NotEmpty(message = "Title not there")
  private String title;
  private boolean wonOscar;
  @Min(value = 1920, message = "Movies before 1920 are not considered!")
  @Max(value = 2023, message = "Movies after 2022 are not planned now!")
  private int releaseYear;
  @NotNull
  @Pattern(regexp = "(https:\\/\\/).*\\.(?:jpg|gif|png)", message = "Must be a valid URL to a picture.")
  private String coverImage;
  // default fetch type: LAZY

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "movie_actor", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
  private List<Actor> actors;
  // Due to merge, the film studio will be stored when it is not present in the database

  @ManyToOne(cascade = CascadeType.MERGE) // default fetch type: EAGER
  private FilmStudio filmStudio;
}
