package br.jus.stj.skeleto_app.model;

import br.jus.stj.skeleto_app.domain.Municipio;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AeroportoDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    @AeroportoSiglaUnique
    private String sigla;

    @NotNull
    @Size(max = 2)
    private String uf;

    @NotNull
    @Size(max = 255)
    private String cidade;

    @NotNull
    @Size(max = 255)
    private String aeroporto;

    private Municipio localidade;

}
