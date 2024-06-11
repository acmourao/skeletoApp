package br.jus.stj.skeleto_app.repos;

import br.jus.stj.skeleto_app.domain.Estado;
import br.jus.stj.skeleto_app.domain.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    Municipio findFirstByUf(Estado estado);

    boolean existsByMunicipioIgnoreCase(String municipio);

    List<Municipio> findByMunicipioContainingIgnoreCase(String cidade);
}
