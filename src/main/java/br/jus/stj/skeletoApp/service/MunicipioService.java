package br.jus.stj.skeletoApp.service;

import br.jus.stj.skeletoApp.domain.Aeroporto;
import br.jus.stj.skeletoApp.domain.Estado;
import br.jus.stj.skeletoApp.domain.Municipio;
import br.jus.stj.skeletoApp.domain.Usuario;
import br.jus.stj.skeletoApp.model.MunicipioDTO;
import br.jus.stj.skeletoApp.repos.AeroportoRepository;
import br.jus.stj.skeletoApp.repos.EstadoRepository;
import br.jus.stj.skeletoApp.repos.MunicipioRepository;
import br.jus.stj.skeletoApp.repos.UsuarioRepository;
import br.jus.stj.skeletoApp.util.NotFoundException;
import br.jus.stj.skeletoApp.util.ReferencedWarning;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final EstadoRepository estadoRepository;
    private final AeroportoRepository aeroportoRepository;
    private final UsuarioRepository usuarioRepository;

    public MunicipioService(final MunicipioRepository municipioRepository,
                            final EstadoRepository estadoRepository, final AeroportoRepository aeroportoRepository,
                            final UsuarioRepository usuarioRepository) {
        this.municipioRepository = municipioRepository;
        this.estadoRepository = estadoRepository;
        this.aeroportoRepository = aeroportoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Municipio> findAll() {
        return municipioRepository.findAll(Sort.by("municipio"))
                .stream().limit(20)
                .toList();
    }

    public List<Municipio> findByMunicipio(String municipio) {
        return municipioRepository.findByMunicipioContainingIgnoreCaseOrderByMunicipio(municipio)
                .stream()
                .limit(20)
                .toList();
    }

    public Municipio get(final Integer id) {
        return municipioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final MunicipioDTO municipioDTO) {
        final Municipio municipio = new Municipio();
        mapToEntity(municipioDTO, municipio);
        return municipioRepository.save(municipio).getId();
    }

    public void update(final Integer id, final MunicipioDTO municipioDTO) {
        final Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(municipioDTO, municipio);
        municipioRepository.save(municipio);
    }

    public void delete(final Integer id) {
        municipioRepository.deleteById(id);
    }

    private Municipio mapToEntity(final MunicipioDTO municipioDTO, final Municipio municipio) {
        municipio.setMunicipio(municipioDTO.getMunicipio());
        final Estado uf = municipioDTO.getUf() == null ? null : estadoRepository.findById(municipioDTO.getUf())
                .orElseThrow(NotFoundException::new);
        municipio.setUf(uf);
        return municipio;
    }

    public boolean municipioExists(final String municipio) {
        return municipioRepository.existsByMunicipioIgnoreCase(municipio);
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Municipio municipio = municipioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Aeroporto municipioAeroporto = aeroportoRepository.findFirstByMunicipio(municipio);
        if (municipioAeroporto != null) {
            referencedWarning.setKey("municipio.aeroporto.municipio.referenced");
            referencedWarning.addParam(municipioAeroporto.getId());
            return referencedWarning;
        }
        final Usuario domicilioUsuario = usuarioRepository.findFirstByDomicilio(municipio);
        if (domicilioUsuario != null) {
            referencedWarning.setKey("municipio.usuario.domicilio.referenced");
            referencedWarning.addParam(domicilioUsuario.getId());
            return referencedWarning;
        }
        return null;
    }

}
