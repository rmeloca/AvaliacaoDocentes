/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.persistence.Persistable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RômuloManciola
 */
public class Avaliacao implements Serializable, Persistable {

    private final Matricula matricula;
    private final Disciplina disciplina;
    private final Professor professor;
    private final Map<Criterio, Integer> criterioTemNota;
    private String observacoes;
    private boolean lida;

    public Avaliacao(Matricula matricula, Disciplina disciplina, Professor professor) throws Exception {
        this.matricula = matricula;
        this.disciplina = disciplina;
        this.professor = professor;
        this.criterioTemNota = new HashMap<>();
        this.lida = false;
    }

    @Deprecated
    private boolean isDisciplinaAvaliada(Matricula matricula, Disciplina disciplina) {
        for (Map.Entry<Disciplina, Professor> disciplinasNaoAvaliada : matricula.getDisciplinasNaoAvaliadas()) {
            if (disciplinasNaoAvaliada.getKey().equals(disciplina)) {
                return false;
            }
        }
        return true;
    }

    protected Matricula getMatricula() {
        return matricula;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public double getMedia() {
        int soma = 0;
        for (Map.Entry<Criterio, Integer> entry : criterioTemNota.entrySet()) {
            soma += entry.getValue();
        }
        return soma / criterioTemNota.size();
    }

    public int getAno() {
        return matricula.getAno();
    }

    public int getSemestre() {
        return matricula.getSemestre();
    }

    protected Professor getProfessor() {
        return professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    protected boolean isLida() {
        return lida;
    }

    protected void ler() throws Exception {
        if (lida) {
            throw new Exception("Avaliacao ja lida");
        }
        lida = true;
    }

    public void avaliarCriterio(Criterio criterio, int nota) throws Exception {
        if (nota < 1 || nota > 5) {
            throw new Exception("Nota invalida");
        }
        criterioTemNota.put(criterio, nota);
    }

    @Override
    public boolean equals(Object obj) {
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        Avaliacao avaliacao = (Avaliacao) obj;
        if (!this.matricula.equals(avaliacao.matricula)) {
            return false;
        }
        if (!this.professor.equals(avaliacao.professor)) {
            return false;
        }
        if (!this.disciplina.equals(avaliacao.disciplina)) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> map = new HashMap<>();
        map.put("matricula", this.matricula);
        map.put("disciplina", this.disciplina);
        map.put("professor", this.professor);
        return map;
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("criterioTemNota", this.criterioTemNota);
        map.put("observacoes", this.observacoes);
        map.put("lida", this.lida);
        return map;
    }

    @Override
    public void fillEntity(Map<String, Object> values) {
        this.observacoes = (String) values.get("observacoes");
        this.lida = (boolean) values.get("lida");
        Map<Criterio, Integer> criterioTemnota = new HashMap<>();
        Map<String, Integer> criterioTemNotaFill = (Map<String, Integer>) values.get("criterioTemNota");
        for (Map.Entry<String, Integer> entry : criterioTemNotaFill.entrySet()) {
            criterioTemnota.put(Criterio.valueOf(entry.getKey()), entry.getValue());
        }
        this.criterioTemNota.clear();
        this.criterioTemNota.putAll(criterioTemnota);
    }

}
