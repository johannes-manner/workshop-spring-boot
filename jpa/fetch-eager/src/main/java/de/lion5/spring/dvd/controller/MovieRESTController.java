package de.lion5.spring.dvd.controller;

import de.lion5.spring.dvd.model.Movie;
import de.lion5.spring.dvd.repository.MovieRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MovieRESTController {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieRESTController(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @GetMapping("/v1/movies")
  public ResponseEntity<Movie[]> getMovies() {
    //log.info("Try to access the DB");
    List<Movie> movieList = movieRepository.findAll();
    try {
      Thread.sleep(10_000);
    } catch (InterruptedException e) {
      log.info("My request was interrupted");
    }
    //log.info("My request was executed :)");
    return ResponseEntity.ok(movieList.toArray(new Movie[movieList.size()]));
  }
}
