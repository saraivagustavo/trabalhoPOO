# 🎓 Trabalho - Programação Orientada a Objetos (POO)

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

**Disciplina:** Programação Orientada a Objetos (POO)  
**Curso:** Bacharelado em Sistemas de Informação — Ifes Serra (2025/1)

## 📌 Descrição

Este é o projeto da **Atividade 2 da disciplina de Programação Orientada a Objetos (POO), ministrada pelo Prof. Dr. Hilário Seibel Júnior**.  
Ele implementa um sistema acadêmico para gerenciamento de professores, alunos, turmas e avaliações (provas e trabalhos), com base no diagrama UML e nos requisitos descritos na atividade.

O programa permite:
- Cadastrar professores e alunos
- Criar turmas com professores, alunos e avaliações
- Calcular médias dos alunos e da turma
- Ler dados via teclado ou arquivo (`input.txt`)

---

## 🧱 Estrutura do Projeto

📁 **trabalhoPOO**  
├── 📄 `input.txt` — Arquivo opcional com entradas automáticas  
└── 📁 `src`  
  └── 📁 `Notas`  
    ├── 📄 `Pessoa.java`  
    ├── 📄 `Aluno.java`  
    ├── 📄 `Professor.java`  
    ├── 📄 `Data.java`  
    ├── 📄 `Avaliacao.java`  
    ├── 📄 `Prova.java`  
    ├── 📄 `Trabalho.java`  
    ├── 📄 `AlunoProva.java`  
    ├── 📄 `GrupoTrabalho.java`  
    ├── 📄 `Turma.java`  
    ├── 📄 `Sistema.java`  
    ├── 📄 `Entrada.java`  
    └── 📄 `Main.java`



---

## 🛠 Classes Implementadas

| Classe             | Descrição |
|--------------------|-----------|
| `Pessoa`           | Classe base com atributos `nome` e `cpf`. Método `toString()` formatado como `"João da Silva (CPF: 123.456.789-00)"`. |
| `Aluno`            | Herda de `Pessoa`, com atributo adicional `matricula`. Sobrescreve `toString()` para mostrar a matrícula ao invés do CPF. |
| `Professor`        | Herda de `Pessoa`, com atributo adicional `salario`. |
| `Data`             | Armazena dia, mês e ano. Tem método `posterior(Data d2)` para comparar datas. |
| `Avaliacao`        | Classe abstrata com atributos `nome`, `dtAplicacao` e `valor`. Tem método `nota(cpf)` que será sobrescrito nas subclasses. |
| `Prova`            | Herda de `Avaliacao`. Possui número de questões e uma lista de objetos `AlunoProva`. Retorna a soma total das notas do aluno. |
| `Trabalho`         | Herda de `Avaliacao`. Possui número máximo de integrantes e uma lista de grupos. Usa `GrupoTrabalho` para armazenar os alunos e a nota do grupo. |
| `AlunoProva`       | Armazena um aluno e suas notas por questão. O método `notaTotal()` soma as notas individuais. |
| `GrupoTrabalho`    | Armazena um grupo de alunos e a nota geral do grupo. O método `alunoNoGrupo(String cpf)` verifica se o aluno faz parte do grupo. |
| `Turma`            | Representa uma turma com nome, ano, semestre, professor, alunos e avaliações. O método `medias()` calcula e exibe as notas finais dos alunos e da turma. |
| `Sistema`          | Gerencia listas de professores, alunos e turmas. Usamos `ArrayList` para maior dinamismo e facilidade de uso. |
| `Entrada`          | Centraliza toda leitura de dados — seja via teclado ou arquivo (`input.txt`). Garante conformidade com o PDF. |
| `Main`             | Classe principal que roda o menu interativo e conecta todas as funcionalidades do sistema. |

---

## ℹ️ Detalhes Adicionais

O sistema foi desenvolvido com foco na aplicação dos principais conceitos de Programação Orientada a Objetos em Java, como **herança**, **polimorfismo**, **encapsulamento**, **reescrita**, **sobrecarga** e *ArrayLists**.

A execução do programa ocorre por meio de um **menu interativo** que permite ao usuário realizar as operações de forma prática, como cadastrar dados, visualizar médias ou carregar informações automaticamente a partir de um arquivo `input.txt`.

Além disso, o projeto foi estruturado para favorecer a **manutenção** e a **reutilização de código**, organizando cada classe com responsabilidades bem definidas. Isso garante clareza na arquitetura do sistema e facilita futuras extensões, como novos tipos de avaliação ou regras de cálculo de nota.

O projeto respeita os requisitos definidos na atividade, bem como a modelagem proposta no diagrama UML, refletindo diretamente a estrutura de classes, relacionamentos e comportamentos esperados no sistema acadêmico simulado.

---

## **Integrantes do Projeto**
| Aluno | GitHub | LinkedIn |
|-------|--------|----------|
| Gustavo Saraiva Mariano | [![GitHub](https://img.shields.io/badge/github-black?style=for-the-badge&logo=github)](https://github.com/saraivagustavo) | [![LinkedIn](https://img.shields.io/badge/linkedin-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/gustavo-saraiva-mariano/) |
| Pedro Henrique Albani Nunes | [![GitHub](https://img.shields.io/badge/github-black?style=for-the-badge&logo=github)](https://github.com/PedroAlbaniNunes) | [![LinkedIn](https://img.shields.io/badge/linkedin-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/pedro-henrique-albani-nunes-33a729270/) |

