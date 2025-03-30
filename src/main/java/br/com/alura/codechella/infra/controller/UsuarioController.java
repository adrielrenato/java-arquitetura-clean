package br.com.alura.codechella.infra.controller;

import br.com.alura.codechella.application.usecases.AlterarUsuario;
import br.com.alura.codechella.application.usecases.CriarUsuario;
import br.com.alura.codechella.application.usecases.ExcluirUsuario;
import br.com.alura.codechella.application.usecases.ListarUsuarios;
import br.com.alura.codechella.domain.entities.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final CriarUsuario criarUsuario;
    private final ListarUsuarios listarUsuarios;
    private final AlterarUsuario alterarUsuario;
    private final ExcluirUsuario excluirUsuario;

    public UsuarioController(
            CriarUsuario criarUsuario,
            ListarUsuarios listarUsuarios,
            AlterarUsuario alterarUsuario,
            ExcluirUsuario excluirUsuario
    ) {
        this.criarUsuario = criarUsuario;
        this.listarUsuarios = listarUsuarios;
        this.alterarUsuario = alterarUsuario;
        this.excluirUsuario = excluirUsuario;
    }

    @PostMapping
    public UsuarioDto cadastrarUsuario(@RequestBody @Valid UsuarioDto dto) {
        Usuario usuarioSalvo = criarUsuario.cadastrarUsuario(new Usuario(dto.cpf(), dto.nome(),
                dto.nascimento(), dto.email()));

        return new UsuarioDto(usuarioSalvo.getCpf(), usuarioSalvo.getNome(),
                usuarioSalvo.getNascimento(), usuarioSalvo.getEmail());
    }

    @GetMapping
    public List<UsuarioDto> listarTodos() {
        List<Usuario> listaUsuarios = listarUsuarios.listarTodos();

        return listaUsuarios.stream()
                .map(u -> new UsuarioDto(u.getCpf(), u.getNome(), u.getNascimento(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{cpf}")
    public UsuarioDto atualizarUsuario(@PathVariable String cpf, @RequestBody UsuarioDto dto) {
        Usuario atualizado = alterarUsuario.alteraDadosUsuario(cpf,
                new Usuario(dto.cpf(), dto.nome(), dto.nascimento(), dto.email()));
        return new UsuarioDto(atualizado.getCpf(), atualizado.getNome(), atualizado.getNascimento(), atualizado.getEmail());
    }

    @DeleteMapping("/{cpf}")
    public void excluirUsuario(@PathVariable String cpf) {
        excluirUsuario.excluirUsuario(cpf);
    }
}
