package br.jus.stj.skeletoApp.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("br.jus.stj.skeletoApp.domain")
@EnableJpaRepositories("br.jus.stj.skeletoApp.repos")
@EnableTransactionManagement
public class DomainConfig {
}
