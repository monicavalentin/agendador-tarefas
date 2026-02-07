package com.mvalentin.agendadortarefas.infrastructure.security;

import com.mvalentin.agendadortarefas.business.dto.UsuarioDto;
import com.mvalentin.agendadortarefas.infrastructure.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  {

    @Autowired
    private UsuarioClient usuarioClient;

    public UserDetails carregaDadosUsuario(String email, String token){
        UsuarioDto usuarioDto = usuarioClient.buscaUsuario(email,token);
        return User
                .withUsername(usuarioDto.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDto.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}