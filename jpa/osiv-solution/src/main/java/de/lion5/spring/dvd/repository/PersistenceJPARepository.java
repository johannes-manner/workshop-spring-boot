package de.lion5.spring.dvd.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"de.lion5.spring.dvd.repository"},
    enableDefaultTransactions = true)
public class PersistenceJPARepository {

}
