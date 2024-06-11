package br.jus.stj.skeleto_app.repos;

import br.jus.stj.skeleto_app.domain.Banco;
import br.jus.stj.skeleto_app.domain.Municipio;
import br.jus.stj.skeleto_app.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findFirstByBanco(Banco banco);

    Usuario findFirstByDomicilio(Municipio municipio);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByBancoId(Integer id);

    boolean existsByDomicilioId(Integer id);

}
