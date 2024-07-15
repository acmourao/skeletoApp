package br.jus.stj.skeletoApp.repos;

import br.jus.stj.skeletoApp.domain.Aeroporto;
import br.jus.stj.skeletoApp.domain.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AeroportoRepository extends JpaRepository<Aeroporto, Integer> {

    Aeroporto findFirstByMunicipio(Municipio municipio);

    boolean existsBySiglaIgnoreCase(String sigla);

}
