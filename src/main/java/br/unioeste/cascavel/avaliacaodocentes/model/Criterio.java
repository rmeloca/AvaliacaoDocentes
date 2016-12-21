/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

/**
 *
 * @author RômuloManciola
 */
public enum Criterio {
    AVALIACAO, CONTEUDO, DIDATICA, PLANEJAMENTO, RELACIONAMENTO;

    @Override
    public String toString() {
        switch (this) {
            case AVALIACAO:
                return "Parâmetros de Avaliação";
            case CONTEUDO:
                return "Conteudo";
            case DIDATICA:
                return "Didatica";
            case PLANEJAMENTO:
                return "Planejamento";
            case RELACIONAMENTO:
                return "Relacionamento";
            default:
                return "Outro";
        }
    }

    public String getCodigo() {
        switch (this) {
            case AVALIACAO:
                return "AVALIACAO";
            case CONTEUDO:
                return "CONTEUDO";
            case DIDATICA:
                return "DIDATICA";
            case PLANEJAMENTO:
                return "PLANEJAMENTO";
            case RELACIONAMENTO:
                return "RELACIONAMENTO";
            default:
                return "OUTRO";
        }
    }
}
