/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.view;

import br.unioeste.cascavel.avaliacaodocentes.controller.Controller;
import br.unioeste.cascavel.avaliacaodocentes.controller.UsuarioController;
import br.unioeste.cascavel.avaliacaodocentes.model.Administrador;
import br.unioeste.cascavel.avaliacaodocentes.model.Aluno;
import br.unioeste.cascavel.avaliacaodocentes.model.Avaliacao;
import br.unioeste.cascavel.avaliacaodocentes.model.Criterio;
import br.unioeste.cascavel.avaliacaodocentes.model.Disciplina;
import br.unioeste.cascavel.avaliacaodocentes.model.Matricula;
import br.unioeste.cascavel.avaliacaodocentes.model.Professor;
import br.unioeste.cascavel.avaliacaodocentes.model.Usuario;
import br.unioeste.cascavel.avaliacaodocentes.persistence.SerializePersistence;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author RômuloManciola
 */
public class View {

    private static final Controller CONTROLLER = new Controller(new SerializePersistence<>());

    public static void main(String[] args) {
        init();
        UsuarioController usuarioController;
        String login;
        String senha;
        String opcao;
        Scanner scanner = new Scanner(System.in);
        try {
            usuarioController = new UsuarioController();
            while (true) {
                System.out.print("Login: ");
                login = scanner.next();

                if (login.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.print("Senha: ");
                senha = scanner.next();
                try {
                    Usuario user = usuarioController.login(login, senha);
                    System.out.println("Bem-vindo " + user.getNome());
                    if (user instanceof Aluno) {
                        Aluno aluno = (Aluno) user;
                        Set<Map.Entry<Disciplina, Professor>> possiveisAvaliacoes;
                        possiveisAvaliacoes = aluno.getPossiveisAvaliacoes();

                        Map.Entry<Disciplina, Professor> next = possiveisAvaliacoes.iterator().next();
                        System.out.println("avaliada" + next.getKey().getNome() + next.getValue().getNome());
                        Avaliacao avaliacao = new Avaliacao(aluno.getMatriculaAtual(), next.getKey(), next.getValue());
                        avaliacao.avaliarCriterio(Criterio.CONTEUDO, 5);
                        aluno.avaliar(avaliacao);

                        while (true) {
                            listarPossiveisAvaliacoes(possiveisAvaliacoes);
                            System.out.print("Opcao: ");
                            opcao = scanner.next();
                            if (opcao.equals("logout")) {
                                break;
                            }
                        }
                    } else if (user instanceof Professor) {
                        Professor professor = (Professor) user;
                        List<Avaliacao> avaliacoesNaoLidas;
                        avaliacoesNaoLidas = professor.getAvaliacoesNaoLidas();

                        listarAvaliacoes(avaliacoesNaoLidas);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void init() {
        InputStream resourceAsStream = View.class.getResourceAsStream("/users.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        Stream<String> lines = bufferedReader.lines();
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            next = next.replace(", ", ",");
            String[] split = next.split(",");
            try {
                switch (split[0]) {
                    case "Administrador":
                        CONTROLLER.add(new Administrador(split[1], split[2], split[3], split[4]));
                        break;
                    case "Professor":
                        CONTROLLER.add(new Professor(split[1], split[2], split[3], split[4]));
                        break;
                    case "Aluno":
                        CONTROLLER.add(new Aluno(split[1], split[2], split[3], split[4]));
                        break;
                    case "Disciplina":
                        CONTROLLER.add(new Disciplina(split[1], split[2]));
                        break;
                    case "Matricula":
                        Aluno aluno = (Aluno) CONTROLLER.get(new Aluno(split[3]));
                        Matricula matricula;
                        int ano = Integer.valueOf(split[1]);
                        int semestre = Integer.valueOf(split[2]);
                        try {
                            matricula = aluno.getMatricula(ano, semestre);
                        } catch (Exception ex) {
                            matricula = new Matricula(ano, semestre, aluno);
                            aluno.matricular(matricula);
                        }
                        Disciplina disciplina = (Disciplina) CONTROLLER.get(new Disciplina(split[4]));
                        Professor professor = (Professor) CONTROLLER.get(new Professor(split[5]));
                        matricula.addDisciplina(disciplina, professor);
                        CONTROLLER.update(aluno);
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public static void exibirAvaliacao(Avaliacao avaliacao) {
        System.out.print(avaliacao);
    }

    public static void listarPossiveisAvaliacoes(Set<Map.Entry<Disciplina, Professor>> possiveisAvaliacoes) {
        int i = 1;
        for (Map.Entry<Disciplina, Professor> possivelAvaliacao : possiveisAvaliacoes) {
            System.out.print(i);
            System.out.print("\t");
            System.out.print(possivelAvaliacao.getKey().getNome());
            System.out.print("\t");
            System.out.print(possivelAvaliacao.getValue().getNome());
            System.out.println();
            i++;
        }
    }

    public static void listarAvaliacoes(List<Avaliacao> avaliacoes) {
        int i = 1;
        for (Avaliacao avaliacao : avaliacoes) {
            System.out.print(i);
            System.out.print("\t");
            System.out.print(avaliacao);
            System.out.println();
            i++;
        }
    }
}
