package br.jus.stj.skeleto_app.repos;

import br.jus.stj.skeleto_app.domain.Aeroporto;
import br.jus.stj.skeleto_app.domain.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {

    Aeroporto findFirstByLocalidade(Municipio municipio);

    boolean existsBySiglaIgnoreCase(String sigla);

}
