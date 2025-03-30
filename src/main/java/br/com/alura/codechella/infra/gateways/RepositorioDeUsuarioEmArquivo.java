package br.com.alura.codechella.infra.gateways;

import br.com.alura.codechella.application.gateways.RepositorioDeUsuario;
import br.com.alura.codechella.domain.entities.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioDeUsuarioEmArquivo implements RepositorioDeUsuario {
    List<Usuario> usuarios = new ArrayList<>();

    @Override
    public Usuario cadastrarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        return this.usuarios;
    }

    @Override
    public Usuario alteraUsuario(String cpf, Usuario usuarioAtualizado) {
        return usuarios.stream()
                .filter(usuario -> usuario.getCpf().equals(cpf))
                .findFirst()
                .map(usuario -> {
                    usuarios.set(usuarios.indexOf(usuario), usuarioAtualizado);
                    return usuarioAtualizado;
                })
                .orElse(null);
    }

    @Override
    public void excluiUsuario(String cpf) {
        usuarios.removeIf(usuario -> usuario.getCpf().equals(cpf));
    }
}