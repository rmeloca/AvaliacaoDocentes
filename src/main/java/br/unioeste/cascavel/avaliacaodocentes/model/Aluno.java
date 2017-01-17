/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.persistence.Fill;
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
    public Fill getValues() {
        Fill fill = super.getValues();
        fill.addAttribute("matriculas", matriculas);
        return fill;
    }

    @Override
    public void fillEntity(Fill fill) {
        super.fillEntity(fill);

        Iterable<Fill> matriculasFill = fill.getCollection("matriculas");
        this.matriculas.clear();
        for (Fill matriculaFill : matriculasFill) {
            int ano = (int) matriculaFill.getAttribute("ano");
            int semestre = (int) matriculaFill.getAttribute("semestre");
            try {
                Matricula matricula = new Matricula(ano, semestre, this);
                matricula.fillEntity(matriculaFill);
                this.matriculas.add(matricula);
            } catch (Exception ex) {
                Logger.getLogger(Aluno.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
