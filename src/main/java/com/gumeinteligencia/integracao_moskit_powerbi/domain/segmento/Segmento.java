package com.gumeinteligencia.integracao_moskit_powerbi.domain.segmento;

import lombok.Getter;

@Getter
public enum Segmento {
    EDUCACAO("Educação"),
    INDUSTRI("Indústri"),
    TECNOLOGIA("Tecnologia"),
    VAREJO("Varejo"),
    PRESTADORES_DE_SERVICE("Prestadores de service"),
    AGENCIA_MARKETING("Agência de Marketing"),
    JOGOS("Jogos"),
    CLINICA_MEDICA("Clínica Médica"),
    SEM_SEGMENTO( "Sem segmento");

    private Integer codigo;
    private String descricao;

    Segmento(String key) {
        this.descricao = key;
    }

    public static void carregarCodigos(SegmentoProperties properties) {
        for (Segmento segmento : values()) {
            String chave = segmento.name().toLowerCase(); // igual às chaves do .properties
            segmento.codigo = properties.getCodigo(chave);
            segmento.descricao = formatarDescricao(chave);
        }
    }

    private static String formatarDescricao(String key) {
        return key.replace("_", " ").substring(0, 1).toUpperCase() + key.replace("_", " ").substring(1);
    }

}
