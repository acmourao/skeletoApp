package br.jus.stj.skeleto_app.repos;

import br.jus.stj.skeleto_app.domain.Banco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BancoRepository extends JpaRepository<Banco, Integer> {

    boolean existsByCompeIgnoreCase(String compe);

    boolean existsByCnpjIgnoreCase(String cnpj);

}
