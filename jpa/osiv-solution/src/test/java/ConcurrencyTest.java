import de.lion5.spring.dvd.model.Movie;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


/**
 * Start the Spring Boot service first before executing this test :)
 */
public class ConcurrencyTest {


  private static final int NUMBER_OF_CLIENTS = 35;
  private static AtomicInteger number = new AtomicInteger();

  @Test
  public void requestMoviesEndpointNTimes() {

    LocalDateTime start = LocalDateTime.now();

    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CLIENTS);

    boolean isTwo = true;
    for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
      executorService.execute(() -> {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Movie[]> response = restTemplate.getForEntity(
            "http://localhost:8080/v1/movies", Movie[].class);
        System.out.println("Executed iteration: " + number.incrementAndGet());
      });
    }
    shutdownAndAwaitTermination(executorService);

    LocalDateTime end = LocalDateTime.now();
    System.out.println("Test took: " + ChronoUnit.SECONDS.between(start, end) + " seconds");
  }

  @Test
  public void generateRandomData() {

    LocalDateTime start = LocalDateTime.now();

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<Integer> response = restTemplate.getForEntity(
        "http://localhost:8080/v1/movies/gen", Integer.class);

    LocalDateTime end = LocalDateTime.now();
    System.out.println("Test took: " + ChronoUnit.MILLIS.between(start, end) + " milliseconds");
  }

  // Copied from: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html
  void shutdownAndAwaitTermination(ExecutorService pool) {
    pool.shutdown(); // Disable new tasks from being submitted
    try {
      // Wait a while for existing tasks to terminate
      if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        pool.shutdownNow(); // Cancel currently executing tasks
        // Wait a while for tasks to respond to being cancelled
        if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
          System.err.println("Pool did not terminate");
        }
      }
    } catch (InterruptedException ex) {
      // (Re-)Cancel if current thread also interrupted
      pool.shutdownNow();
      // Preserve interrupt status
      Thread.currentThread().interrupt();
    }
  }
}
