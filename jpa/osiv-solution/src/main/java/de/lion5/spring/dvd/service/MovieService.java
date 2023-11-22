package de.lion5.spring.dvd.service;

import de.lion5.spring.dvd.model.Movie;
import de.lion5.spring.dvd.repository.MovieRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  private MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllWithMoreThanNActors(final int noOfActors) {
    List<Movie> movies = this.movieRepository.findAllMoviesAndTheirFilmStudios();

    return movies.stream().filter(m -> m.getActors().size() >= noOfActors)
        .collect(Collectors.toList());
  }


  public void createFakeMovies(Integer numberOfMovies) {
    for (int i = 0; i < numberOfMovies; i++) {
      movieRepository.save(
          new Movie("An amazing film " + i, true, 2023,
              "https://amazing.images.com/" + i + ".png", null, null));
    }
  }

  public long countMovies() {
    return this.movieRepository.count();
  }
}
