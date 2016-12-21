CREATE CLASS Usuario EXTENDS V ABSTRACT;

CREATE CLASS Aluno EXTENDS Usuario;
CREATE VERTEX Aluno CONTENT { "login" : "rmeloca", "senha" : "meloca", "nome" : "Rômulo", "email" : "rmeloca@gmail.com" }
CREATE VERTEX Aluno CONTENT { "login" : "bmeloca", "senha" : "meloca", "nome" : "Bianca", "email" : "bmeloca@gmail.com" }
CREATE VERTEX Aluno CONTENT { "login" : "ameloca", "senha" : "meloca", "nome" : "Alzira", "email" : "ameloca@gmail.com" }
CREATE VERTEX Aluno CONTENT { "login" : "jmeloca", "senha" : "meloca", "nome" : "José L", "email" : "jmeloca@gmail.com" }

CREATE CLASS Professor EXTENDS Usuario;
CREATE VERTEX Professor CONTENT { "login" : "liberato", "senha" : "meloca", "nome" : "Rafael Liberato", "email" : "liberato@gmail.com" }
CREATE VERTEX Professor CONTENT { "login" : "foleiss", "senha" : "meloca", "nome" : "Juliano Foleiss", "email" : "foleiss@gmail.com" }
CREATE VERTEX Professor CONTENT { "login" : "hubner", "senha" : "meloca", "nome" : "Rodrigo Hubner", "email" : "hubner@gmail.com" }
CREATE VERTEX Professor CONTENT { "login" : "shwerz", "senha" : "meloca", "nome" : "Andre Luiz Shwerz", "email" : "shwerz@gmail.com" }

CREATE CLASS Disciplina EXTENDS V;
CREATE VERTEX Disciplina CONTENT { "codigo" : "BCC35A", "nome" : "Linguagens de Programação" }
CREATE VERTEX Disciplina CONTENT { "codigo" : "BCC35B", "nome" : "Teoria dos Grafos" }
CREATE VERTEX Disciplina CONTENT { "codigo" : "BCC35C", "nome" : "Projeto Integrador" }
CREATE VERTEX Disciplina CONTENT { "codigo" : "BCC35D", "nome" : "Redes de Computadores II" }

CREATE CLASS Matricula EXTENDS V;
CREATE VERTEX Matricula SET ano = 2016, semestre = 2, aluno = (select @rid from Usuario where login = "rmeloca")

CREATE EDGE E FROM #22:33 TO #22:55 CONTENT { "name": "Jay", "surname": "Miner" }