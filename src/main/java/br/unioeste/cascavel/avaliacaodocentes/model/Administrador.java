/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.model;

import br.unioeste.cascavel.avaliacaodocentes.controller.AdministradorController;
import br.unioeste.cascavel.avaliacaodocentes.controller.AlunoController;
import br.unioeste.cascavel.avaliacaodocentes.controller.DisciplinaController;
import br.unioeste.cascavel.avaliacaodocentes.controller.ProfessorController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 *
 * @author RômuloManciola
 */
public class Administrador extends Usuario {

    public Administrador(String login, String senha, String nome, String email) throws Exception {
        super(login, senha, nome, email);
    }

    public Administrador(String login) throws Exception {
        super(login);
    }

    public void atualizarDados(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Stream<String> lines = bufferedReader.lines();
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] fields = line.split(",");
            for (int i = 0; i < fields.length; i++) {
                fields[i] = fields[i].trim();
            }
            try {
                AdministradorController administradorController = new AdministradorController();
                ProfessorController professorController = new ProfessorController();
                AlunoController alunoController = new AlunoController();
                DisciplinaController disciplinaController = new DisciplinaController();

                switch (fields[0]) {
                    case "Administrador":
                        administradorController.add(new Administrador(fields[1], fields[2], fields[3], fields[4]));
                        break;
                    case "Professor":
                        professorController.add(new Professor(fields[1], fields[2], fields[3], fields[4]));
                        break;
                    case "Aluno":
                        alunoController.add(new Aluno(fields[1], fields[2], fields[3], fields[4]));
                        break;
                    case "Disciplina":
                        disciplinaController.add(new Disciplina(fields[1], fields[2]));
                        break;
                    case "Matricula":
                        Aluno aluno = alunoController.get(new Aluno(fields[3]));
                        Matricula matricula;
                        int ano = Integer.valueOf(fields[1]);
                        int semestre = Integer.valueOf(fields[2]);
                        try {
                            matricula = aluno.getMatricula(ano, semestre);
                        } catch (Exception ex) {
                            matricula = new Matricula(ano, semestre, aluno);
                            aluno.matricular(matricula);
                        }
                        Disciplina disciplina = disciplinaController.get(new Disciplina(fields[4]));
                        Professor professor = professorController.get(new Professor(fields[5]));
                        matricula.addDisciplina(disciplina, professor);
                        alunoController.update(aluno);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
        bufferedReader.close();
        inputStreamReader.close();
    }

}
