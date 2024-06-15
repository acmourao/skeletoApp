package br.jus.stj.skeleto_app.service;

import br.jus.stj.skeleto_app.domain.Aeroporto;
import br.jus.stj.skeleto_app.domain.Estado;
import br.jus.stj.skeleto_app.domain.Municipio;
import br.jus.stj.skeleto_app.domain.Usuario;
import br.jus.stj.skeleto_app.model.MunicipioDTO;
import br.jus.stj.skeleto_app.repos.AeroportoRepository;
import br.jus.stj.skeleto_app.repos.EstadoRepository;
import br.jus.stj.skeleto_app.repos.MunicipioRepository;
import br.jus.stj.skeleto_app.repos.UsuarioRepository;
import br.jus.stj.skeleto_app.util.NotFoundException;
import br.jus.stj.skeleto_app.util.ReferencedWarning;

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
        return municipioRepository.findAll(Sort.by("municipio")).stream().limit(20)
                .toList();
    }

    public List<Municipio> findByMunicipio(String municipio) {
        return municipioRepository.findByMunicipioContainingIgnoreCaseOrderByMunicipio(municipio).stream().limit(20).toList();
        //Sort.by("municipio")).stream().limit(20)
        //.toList();
    }

//    public List<MunicipioDTO> findAll() {
//        return municipioRepository.findAll(Sort.by("municipio")).stream().limit(20)
//                .map(municipio -> mapToDTO(municipio, new MunicipioDTO()))
//                .toList();
//    }

    //    public MunicipioDTO get(final Integer id) {
//        return municipioRepository.findById(id)
//                .map(municipio -> mapToDTO(municipio, new MunicipioDTO()))
//                .orElseThrow(NotFoundException::new);
//    }
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

    private MunicipioDTO mapToDTO(final Municipio municipio, final MunicipioDTO municipioDTO) {
        municipioDTO.setId(municipio.getId());
        municipioDTO.setMunicipio(municipio.getMunicipio());
        municipioDTO.setUf(municipio.getUf() == null ? null : municipio.getUf().getUf());
        return municipioDTO;
    }

    private Municipio mapToEntity(final MunicipioDTO municipioDTO, final Municipio municipio) {
        municipio.setMunicipio(municipioDTO.getMunicipio());
        final Estado uf = municipioDTO.getUf() == null ? null : estadoRepository.findById(municipioDTO.getUf())
                .orElseThrow(() -> new NotFoundException("uf not found"));
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
