package br.jus.stj.skeleto_app.rest;

import br.jus.stj.skeleto_app.domain.Municipio;
import br.jus.stj.skeleto_app.model.MunicipioDTO;
import br.jus.stj.skeleto_app.service.MunicipioService;
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
@RequestMapping(value = "/api/municipios", produces = MediaType.APPLICATION_JSON_VALUE)
public class MunicipioResource {

    private final MunicipioService municipioService;

    public MunicipioResource(final MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    @GetMapping
    public ResponseEntity<List<Municipio>> getAllMunicipios() {
        return ResponseEntity.ok(municipioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Municipio> getMunicipio(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(municipioService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMunicipio(
            @RequestBody @Valid final MunicipioDTO municipioDTO) {
        final Integer createdId = municipioService.create(municipioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @GetMapping("/buscar/{municipio}")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<Municipio>> listaMunicipioByName(@PathVariable String municipio) {
        return ResponseEntity.ok(municipioService.findByMunicipio(municipio));
    }

//    @PostMapping(value = "/buscar", consumes = "application/json")
//    @ApiResponse(responseCode = "200")
//    public ResponseEntity<List<Municipio>> listaMunicipioPorNome(@RequestBody String municipio) {
//        return ResponseEntity.ok(municipioService.findByMunicipio(municipio));
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateMunicipio(@PathVariable(name = "id") final Integer id,
                                                   @RequestBody @Valid final MunicipioDTO municipioDTO) {
        municipioService.update(id, municipioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMunicipio(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = municipioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        municipioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
