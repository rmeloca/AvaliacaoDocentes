/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RômuloManciola
 */
public class Aluno extends Usuario {

    private final Collection<Matricula> matriculas;

    public Aluno(String login, String senha, String nome, String email) throws Exception {
        super(login, senha, nome, email);
        this.matriculas = new ArrayList<>();
    }

    public Aluno(String login) throws Exception {
        super(login);
        this.matriculas = new ArrayList<>();
    }

    public void matricular(Matricula matricula) throws Exception {
        if (matriculas.contains(matricula)) {
            throw new Exception("Matricula ja efetuada");
        }
        matriculas.add(matricula);
    }

    public void avaliar(Avaliacao avaliacao) throws Exception {
        if (!equals(avaliacao.getMatricula().getAluno())) {
            throw new Exception("Aluno nao confere");
        }
        Matricula matriculaAtual = getMatriculaAtual();
        if (!matriculaAtual.equals(avaliacao.getMatricula())) {
            throw new Exception("Matricula nao atual");
        }
        Professor professor = avaliacao.getProfessor();
        professor.addAvaliacao(avaliacao);
        matriculaAtual.addAvaliacao(avaliacao);
    }

    public Matricula getMatricula(int ano, int semestre) throws Exception {
        for (Matricula matricula : matriculas) {
            if (matricula.getAno() == ano && matricula.getSemestre() == semestre) {
                return matricula;
            }
        }
        throw new Exception("Matricula nao encontrada");
    }

    public Matricula getMatriculaAtual() throws Exception {
        if (matriculas.isEmpty()) {
            throw new Exception("Nenhuma Matricula");
        }
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int semestre = calendar.get(Calendar.MONTH) <= 6 ? 1 : 2;
        try {
            return getMatricula(ano, semestre);
        } catch (Exception ex) {
            throw new Exception("Nao matriculado neste semestre");
        }
    }

    public Set<Map.Entry<Disciplina, Professor>> getPossiveisAvaliacoes() throws Exception {
        Matricula matriculaAtual = getMatriculaAtual();
        return matriculaAtual.getDisciplinasNaoAvaliadas();
    }

    @Override
    public Map<String, Object> getValues() {
        Map<String, Object> map = super.getValues();
        map.put("matriculas", matriculas);
        return map;
    }

    @Override
    public void fillEntity(Map<String, Object> values) {
        super.fillEntity(values);

        Collection<Map<String, Object>> matriculasFill = (Collection<Map<String, Object>>) values.get("matriculas");
        if (matriculasFill == null) {
            return;
        }
        Collection<Matricula> matriculas = new ArrayList<>();
        for (Map<String, Object> matriculaFill : matriculasFill) {
            int ano = (int) matriculaFill.get("ano");
            int semestre = (int) matriculaFill.get("semestre");
            try {
                Matricula matricula = new Matricula(ano, semestre, this);
                matricula.fillEntity(matriculaFill);
                matriculas.add(matricula);
            } catch (Exception ex) {
                Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.matriculas.clear();
        this.matriculas.addAll(matriculas);
    }

}
