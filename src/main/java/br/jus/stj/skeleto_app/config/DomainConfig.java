package br.jus.stj.skeleto_app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("br.jus.stj.skeleto_app.domain")
@EnableJpaRepositories("br.jus.stj.skeleto_app.repos")
@EnableTransactionManagement
public class DomainConfig {
}
