package br.jus.stj.skeleto_app.rest;

import br.jus.stj.skeleto_app.domain.Estado;
import br.jus.stj.skeleto_app.model.EstadoDTO;
import br.jus.stj.skeleto_app.service.EstadoService;
import br.jus.stj.skeleto_app.util.ReferencedException;
import br.jus.stj.skeleto_app.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoResource {

    private final EstadoService estadoService;

    public EstadoResource(final EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public ResponseEntity<List<Estado>> getAllEstados() {
        return ResponseEntity.ok(estadoService.findAll());
    }

    @GetMapping("/{uf}")
    public ResponseEntity<Estado> getEstado(@PathVariable(name = "uf") final String uf) {
        return ResponseEntity.ok(estadoService.get(uf));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createEstado(@RequestBody @Valid final EstadoDTO estadoDTO) {
        final String createdUf = estadoService.create(estadoDTO);
        return new ResponseEntity<>('"' + createdUf + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{uf}")
    public ResponseEntity<String> updateEstado(@PathVariable(name = "uf") final String uf,
                                               @RequestBody @Valid final EstadoDTO estadoDTO) {
        estadoService.update(uf, estadoDTO);
        return ResponseEntity.ok('"' + uf + '"');
    }

    @DeleteMapping("/{uf}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEstado(@PathVariable(name = "uf") final String uf) {
        final ReferencedWarning referencedWarning = estadoService.getReferencedWarning(uf);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        estadoService.delete(uf);
        return ResponseEntity.noContent().build();
    }

}
