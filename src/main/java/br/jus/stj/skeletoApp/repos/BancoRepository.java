package br.jus.stj.skeletoApp.repos;

import br.jus.stj.skeletoApp.domain.Banco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BancoRepository extends JpaRepository<Banco, Integer> {

    boolean existsByCompeIgnoreCase(String compe);

    boolean existsByCnpjIgnoreCase(String cnpj);

}
