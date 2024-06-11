package br.jus.stj.skeleto_app.repos;

import br.jus.stj.skeleto_app.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstadoRepository extends JpaRepository<Estado, String> {

    boolean existsByUfIgnoreCase(String uf);

}
