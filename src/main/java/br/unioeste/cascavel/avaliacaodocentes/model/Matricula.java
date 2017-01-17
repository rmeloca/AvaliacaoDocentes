/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.persistence.Fill;
import br.unioeste.cascavel.avaliacaodocentes.persistence.Persistable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RômuloManciola
 */
public class Matricula implements Persistable, Serializable {

    private final int ano;
    private final int semestre;
    private final Aluno aluno;
    private final Map<Disciplina, Professor> disciplinaTemProfessor;
    private final Collection<Avaliacao> avaliacoes;

    public Matricula(int ano, int semestre, Aluno aluno) throws Exception {
        if (ano < 0) {
            throw new Exception("Ano incorreto");
        }
        if (semestre != 1 && semestre != 2) {
            throw new Exception("Semestre incorreto");
        }
        if (aluno == null) {
            throw new Exception("Aluno nulo");
        }
        this.ano = ano;
        this.semestre = semestre;
        this.aluno = aluno;
        this.disciplinaTemProfessor = new HashMap<>();
        this.avaliacoes = new ArrayList<>();
    }

    protected int getAno() {
        return ano;
    }

    protected int getSemestre() {
        return semestre;
    }

    protected Aluno getAluno() {
        return aluno;
    }

    public void addDisciplina(Disciplina disciplina, Professor professor) {
        disciplinaTemProfessor.put(disciplina, professor);
    }

    protected Set<Map.Entry<Disciplina, Professor>> getDisciplinas() {
        return disciplinaTemProfessor.entrySet();
    }

    protected Set<Map.Entry<Disciplina, Professor>> getDisciplinasNaoAvaliadas() {
        HashMap<Disciplina, Professor> disciplinasNaoavaliadas = new HashMap<>();
        for (Map.Entry<Disciplina, Professor> entry : disciplinaTemProfessor.entrySet()) {
            try {
                if (!avaliacoes.contains(new Avaliacao(this, entry.getKey(), entry.getValue()))) {
                    disciplinasNaoavaliadas.put(entry.getKey(), entry.getValue());
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return disciplinasNaoavaliadas.entrySet();
    }

    protected void addAvaliacao(Avaliacao avaliacao) throws Exception {
        if (avaliacoes.contains(avaliacao)) {
            throw new Exception("Avaliacao ja registrada");
        }
        if (!avaliacao.getMatricula().equals(aluno.getMatriculaAtual())) {
            throw new Exception("Matricula antiga");
        }
        Professor professorEsperado = getProfessor(avaliacao.getDisciplina());
        if (!avaliacao.getProfessor().equals(professorEsperado)) {
            throw new Exception("Professor nao confere");
        }
        avaliacoes.add(avaliacao);
    }

    public Professor getProfessor(Disciplina disciplina) throws Exception {
        for (Map.Entry<Disciplina, Professor> entry : disciplinaTemProfessor.entrySet()) {
            if (entry.getKey().equals(disciplina)) {
                return entry.getValue();
            }
        }
        throw new Exception("Disciplina nao matriculada");
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        Matricula matricula = (Matricula) obj;
        if (this.ano != matricula.ano) {
            return false;
        }
        if (this.semestre != matricula.semestre) {
            return false;
        }
        if (!this.aluno.equals(matricula.aluno)) {
            return false;
        }
        return true;
    }

    @Override
    public Fill getPrimaryKey() {
        Fill fill = new Fill();
        fill.addAttribute("ano", this.ano);
        fill.addAttribute("semestre", this.semestre);
        fill.addAttribute("aluno", this.aluno);
        return fill;
    }

    @Override
    public Fill getValues() {
        Fill fill = new Fill();
        fill.addAttribute("ano", this.ano);
        fill.addAttribute("semestre", this.semestre);
        fill.addAttribute("aluno", this.aluno);
        fill.addAttribute("disciplinaTemProfessor", this.disciplinaTemProfessor);
        fill.addAttribute("avaliacoes", this.avaliacoes);
        return fill;
    }

    @Override
    public void fillEntity(Fill fill) {
        Iterable<Fill> avaliacoesFill = fill.getCollection("avaliacoes");
        this.avaliacoes.clear();
        for (Fill avaliacaoFill : avaliacoesFill) {
            try {
                Fill disciplinaFill = avaliacaoFill.getFill("disciplina");
                String codigo = disciplinaFill.getString("codigo");
                Disciplina disciplina = new Disciplina(codigo);
                disciplina.fillEntity(disciplinaFill);
                Fill professorFill = avaliacaoFill.getFill("professor");
                String login = professorFill.getString("login");
                Professor professor = new Professor(login);
                professor.fillEntity(professorFill);
                Avaliacao avaliacao = new Avaliacao(this, disciplina, professor);
                avaliacao.fillEntity(avaliacaoFill);
                this.avaliacoes.add(avaliacao);
            } catch (Exception ex) {
                Logger.getLogger(Matricula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.disciplinaTemProfessor.clear();
        Iterable<Map.Entry<Fill, Fill>> disciplinaTemProfessorFill = fill.getMap("disciplinaTemProfessor");
        for (Map.Entry<Fill, Fill> entry : disciplinaTemProfessorFill) {
            try {
                String codigo = entry.getKey().getString("codigo");
                Disciplina disciplina = new Disciplina(codigo);
                disciplina.fillEntity(entry.getKey());
                String login = entry.getValue().getString("login");
                Professor professor = new Professor(login);
                professor.fillEntity(entry.getValue());
                this.disciplinaTemProfessor.put(disciplina, professor);
            } catch (Exception ex) {
                Logger.getLogger(Matricula.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
