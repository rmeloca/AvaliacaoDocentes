/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RômuloManciola
 */
public class Professor extends Usuario {

    private final List<Avaliacao> avaliacoes;

    public Professor(String login, String senha, String nome, String email) throws Exception {
        super(login, senha, nome, email);
        this.avaliacoes = new ArrayList<>();
    }

    public Professor(String login) throws Exception {
        super(login);
        this.avaliacoes = new ArrayList<>();
    }

    protected void addAvaliacao(Avaliacao avaliacao) throws Exception {
        if (avaliacoes.contains(avaliacao)) {
            throw new Exception("Avaliacao ja postada");
        }
        if (avaliacao.isLida()) {
            throw new Exception("Avaliacao ja lida");
        }
        avaliacoes.add(avaliacao);
    }

    private Avaliacao getAvaliacao(Avaliacao avaliacao) throws Exception {
        for (Avaliacao avaliacaoRegistrada : avaliacoes) {
            if (avaliacaoRegistrada.equals(avaliacao)) {
                return avaliacao;
            }
        }
        throw new Exception("Avaliacao nao existente");
    }

    public void lerAvaliacao(Avaliacao avaliacao) throws Exception {
        Avaliacao avaliacaoRegistrada = getAvaliacao(avaliacao);
        avaliacaoRegistrada.ler();
    }

    public List<Avaliacao> getAvaliacoesNaoLidas() {
        List<Avaliacao> avaliacoesNaoLidas = new ArrayList<>();
        for (Avaliacao avaliacao : avaliacoes) {
            if (!avaliacao.isLida()) {
                avaliacoesNaoLidas.add(avaliacao);
            }
        }
        return avaliacoesNaoLidas;
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> map = super.getValues();
        map.put("avaliacoes", avaliacoes);
        return map;
    }

}
