package br.jus.stj.skeletoApp.repos;

import br.jus.stj.skeletoApp.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EstadoRepository extends JpaRepository<Estado, String> {

    boolean existsByUfIgnoreCase(String uf);

}
