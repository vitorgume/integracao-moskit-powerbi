package com.gumeinteligencia.integracao_moskit_powerbi.application.exceptions;

public class MotivoPerdaNaoEncontradoException extends RuntimeException{
    public MotivoPerdaNaoEncontradoException(Integer id) {
        super("Motivo de perda não encontrado com o id: " + id);
    }
}
