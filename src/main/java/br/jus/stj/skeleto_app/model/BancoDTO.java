package br.jus.stj.skeleto_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BancoDTO {

    private Integer id;

    @NotNull
    @Size(max = 3)
    @BancoCompeUnique
    private String compe;

    @NotNull
    @Size(max = 18)
    @BancoCnpjUnique
    private String cnpj;

    @NotNull
    @Size(max = 127)
    private String razaoSocial;

    @Size(max = 127)
    private String nome;

    @Size(max = 127)
    private String produtos;

    @Size(max = 127)
    private String url;

}
