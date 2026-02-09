package com.mvalentin.agendadortarefas.infrastructure.client;

import com.mvalentin.agendadortarefas.business.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usaurio", url = "${usuario.url}")
public interface UsuarioClient {
    @GetMapping("/usuario")
    UsuarioDto buscaUsuario(@RequestParam("email")String email,
                            @RequestHeader("Authorization") String token);
}