package de.lion5.spring.dvd.repository;

import de.lion5.spring.dvd.model.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  @EntityGraph(value = "Movie.actors")
  @Override
  public List<Movie> findAll();


  @EntityGraph(value = "Movie.actorsFilmStudio")
  @Query("SELECT m FROM Movie m")
  public List<Movie> findAllMoviesAndTheirFilmStudios();
}
