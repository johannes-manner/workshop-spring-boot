package de.lion5.spring.dvd.controller;

import de.lion5.spring.dvd.model.Movie;
import de.lion5.spring.dvd.service.MovieService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MovieRESTController {

  private final MovieService movieService;

  @Autowired
  public MovieRESTController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/v1/movies")
  public ResponseEntity<Movie[]> getMovies() {

    this.logInfo("Try to access the DB ");
    List<Movie> movieList = movieService.findAllWithMoreThanNActors(1);
    this.logInfo("My request to DB was executed");

    this.simulateABlockingCall(10_000);

    this.logInfo("Response is generated :)");
    return ResponseEntity.ok(movieList.toArray(new Movie[movieList.size()]));
  }

  @GetMapping("/v1/movies/gen")
  public ResponseEntity<Integer> generateMovies() {

    int numberOfMovies = 10;

    this.logInfo("Try create a lot of fake movies ");
    this.movieService.createFakeMovies(numberOfMovies);
    this.logInfo("Created fake movies");

    this.logInfo("Response is generated :)");
    return ResponseEntity.ok(numberOfMovies);
  }

  private void simulateABlockingCall(int timeToWait) {
    try {
      Thread.sleep(timeToWait);
    } catch (InterruptedException e) {
      this.logInfo("My request was interrupted");
    }
  }

  private void logInfo(String s) {
    log.info(
        s + " Thread: " + Thread.currentThread().getName() + " Controller: " + this.toString());
  }
}
