package com.gumeinteligencia.integracao_moskit_powerbi.controller;

import com.gumeinteligencia.integracao_moskit_powerbi.application.service.AtualizaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("atualizacoes")
@RequiredArgsConstructor
public class AtualizaController {

    private final AtualizaService service;

    @PostMapping
    public ResponseEntity<String> atualiza() {
        String resposta = service.gatewayAtualizacoes();
        return ResponseEntity.ok(resposta);
    }
}
