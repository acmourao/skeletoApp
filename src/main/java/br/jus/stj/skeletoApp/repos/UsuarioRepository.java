package br.jus.stj.skeletoApp.repos;

import br.jus.stj.skeletoApp.domain.Banco;
import br.jus.stj.skeletoApp.domain.Municipio;
import br.jus.stj.skeletoApp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByBanco(Banco banco);

    Usuario findFirstByDomicilio(Municipio municipio);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByBancoId(Integer id);

    boolean existsByDomicilioId(Integer id);

}
