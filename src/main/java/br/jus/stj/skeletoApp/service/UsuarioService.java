package br.jus.stj.skeletoApp.service;

import br.jus.stj.skeletoApp.domain.Banco;
import br.jus.stj.skeletoApp.domain.Municipio;
import br.jus.stj.skeletoApp.domain.Usuario;
import br.jus.stj.skeletoApp.model.UsuarioDTO;
import br.jus.stj.skeletoApp.repos.BancoRepository;
import br.jus.stj.skeletoApp.repos.MunicipioRepository;
import br.jus.stj.skeletoApp.repos.UsuarioRepository;
import br.jus.stj.skeletoApp.util.NotFoundException;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BancoRepository bancoRepository;
    private final MunicipioRepository municipioRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
                          final BancoRepository bancoRepository, final MunicipioRepository municipioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.bancoRepository = bancoRepository;
        this.municipioRepository = municipioRepository;
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll(Sort.by("nome")).stream().limit(20)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Integer id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Integer id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Integer id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setActive(usuario.getActive());
        usuarioDTO.setTelefone(usuario.getTelefone());
        usuarioDTO.setNascimento(usuario.getNascimento());
        usuarioDTO.setBanco(usuario.getBanco() == null ? null : usuario.getBanco().getId());
        usuarioDTO.setDomicilio(usuario.getDomicilio() == null ? null : usuario.getDomicilio().getId());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setNome(usuarioDTO.getNome());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setActive(usuarioDTO.getActive());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setNascimento(usuarioDTO.getNascimento());
        final Banco banco = usuarioDTO.getBanco() == null ? null : bancoRepository.findById(usuarioDTO.getBanco())
                .orElseThrow(() -> new NotFoundException("banco not found"));
        usuario.setBanco(banco);
        final Municipio domicilio = usuarioDTO.getDomicilio() == null ? null : municipioRepository.findById(usuarioDTO.getDomicilio())
                .orElseThrow(() -> new NotFoundException("domicilio not found"));
        usuario.setDomicilio(domicilio);
        return usuario;
    }

    public boolean emailExists(final String email) {
        return usuarioRepository.existsByEmailIgnoreCase(email);
    }

    public boolean bancoExists(final Integer id) {
        return usuarioRepository.existsByBancoId(id);
    }

    public boolean domicilioExists(final Integer id) {
        return usuarioRepository.existsByDomicilioId(id);
    }

}
