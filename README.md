# 🎓 Trabalho - Programação Orientada a Objetos (POO)

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

**Disciplina:** Programação Orientada a Objetos (POO)  
**Curso:** Bacharelado em Sistemas de Informação — Ifes Serra (2025/1)

## 📌 Descrição

Este é o projeto da **Atividade 3 da disciplina de Programação Orientada a Objetos (POO), ministrada pelo Prof. Dr. Hilário Seibel Júnior**.
Ele implementa um sistema acadêmico para gerenciamento de professores, alunos, turmas e avaliações (provas e trabalhos), com base no diagrama UML e nos requisitos descritos na atividade.
A principal atualização nesta versão é a inclusão de **persistência de dados**, permitindo que as informações cadastradas sejam salvas em um arquivo `dados.txt` e carregadas automaticamente ao iniciar o programa.

O programa permite:
- Cadastrar professores e alunos.
- Criar turmas com professores, alunos e avaliações.
- Calcular médias dos alunos e da turma.
- Ler dados via teclado ou arquivo (`input.txt`).
- Salvar e carregar dados automaticamente de `dados.txt`.

---

## 🧱 Estrutura do Projeto

📁 **trabalhoPOO** ├── 📄 `input.txt` — Arquivo opcional com entradas automáticas  
├── 📄 `dados.txt` — Arquivo para persistência dos dados (criado e lido automaticamente)
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
    ├── 📄 `ICalculavel.java`
    ├── 📄 `ITextoFormatavel.java`
    └── 📄 `Main.java`

---

## 🛠 Classes Implementadas

| Classe             | Descrição |
|--------------------|-----------|
| `Pessoa`           | Classe base com atributos `nome` e `cpf`. Método `toString()` formatado como `"João da Silva (CPF: 123.456.789-00)"`. |
| `Aluno`            | Herda de `Pessoa`, com atributo adicional `matricula`. Sobrescreve `toString()` para mostrar a matrícula ao invés do CPF. Implementa `ITextoFormatavel`. |
| `Professor`        | Herda de `Pessoa`, com atributo adicional `salario`. Implementa `ITextoFormatavel`. |
| `Data`             | Armazena dia, mês e ano. Tem método `posterior(Data d2)` para comparar datas. |
| `Avaliacao`        | Classe **abstrata** com atributos `nome`, `dtAplicacao` e `valor`. Possui o método **abstrato** `nota(cpf)` que deve ser sobrescrito nas subclasses (`Prova` e `Trabalho`). |
| `Prova`            | Herda de `Avaliacao`. Possui número de questões e uma lista de objetos `AlunoProva`. Retorna a soma total das notas do aluno na prova. |
| `Trabalho`         | Herda de `Avaliacao`. Possui número máximo de integrantes e uma lista de grupos. Usa `GrupoTrabalho` para armazenar os alunos e a nota do grupo. |
| `AlunoProva`       | Armazena um aluno e suas notas por questão. O método `notaTotal()` soma as notas individuais. Implementa `ICalculavel`. |
| `GrupoTrabalho`    | Armazena um grupo de alunos e a nota geral do grupo. O método `alunoNoGrupo(String cpf)` verifica se o aluno faz parte do grupo. Implementa `ICalculavel`. |
| `Turma`            | Representa uma turma com nome, ano, semestre, professor, alunos e avaliações. O método `medias()` calcula e exibe as notas finais dos alunos e da turma. |
| `Sistema`          | Gerencia listas de professores, alunos e turmas. Utiliza `ArrayList` para dinamismo. Responsável por salvar (`salvarSistema`) e carregar (`carregarSistema`) os dados do sistema em `dados.txt`. |
| `Entrada`          | Centraliza toda leitura de dados — seja via teclado ou arquivo (`input.txt` ou `dados.txt`). Garante conformidade com as especificações e inclui tratamento de erros para robustez. |
| `Main`             | Classe principal que inicia o sistema, carrega dados de `dados.txt` automaticamente, roda o menu interativo e salva as alterações em `dados.txt` após cada operação de cadastro. |
| `ICalculavel`      | Interface que define um contrato para classes que possuem um valor calculado, como `AlunoProva` e `GrupoTrabalho`. |
| `ITextoFormatavel` | Interface que define um contrato para classes que podem ser formatadas como texto, utilizado para a persistência de dados. |

---

## ℹ️ Detalhes Adicionais

O sistema foi desenvolvido com foco na aplicação dos principais conceitos de Programação Orientada a Objetos em Java, como **herança**, **polimorfismo**, **encapsulamento**, **reescrita**, **sobrecarga** e **ArrayLists**. As classes `Avaliacao` e seus métodos foram transformados em abstratos quando pertinente para melhorar a modelagem.

A execução do programa ocorre por meio de um **menu interativo** que permite ao usuário realizar as operações de forma prática, como cadastrar dados, visualizar médias ou carregar informações automaticamente a partir de um arquivo `input.txt`. O sistema foi implementado com **tratamento de erros e exceções** para garantir robustez e evitar encerramentos inesperados devido a entradas inválidas do usuário.

**Persistência de Dados**: Uma característica fundamental desta versão é a capacidade de salvar automaticamente todos os dados de professores, alunos e turmas em um arquivo texto (`dados.txt`) e carregá-los na inicialização do programa. Isso garante que o estado do sistema seja preservado entre as execuções. O formato de armazenamento no `dados.txt` utiliza siglas como `PROF`, `ALU`, `TUR`, `PROV`, `TRAB` e `FIM` para identificar os diferentes tipos de dados.

**Ordenação e Visualização**:
Ao listar as turmas, o sistema adota as seguintes regras de ordenação:
1.  **Turmas**: Ordenadas semestralmente, da mais recente para a mais antiga. Em caso de empate, por ordem crescente do nome da disciplina. Se ainda houver empate, pelo nome do professor.
2.  **Alunos (dentro de cada turma)**: Listados da maior nota final para a menor nota final. Em caso de empate, por ordem crescente do nome do aluno. Se ainda houver empate, pela matrícula do aluno.

Além disso, o projeto foi estruturado para favorecer a **manutenção** e a **reutilização de código**, organizando cada classe com responsabilidades bem definidas. Isso garante clareza na arquitetura do sistema e facilita futuras extensões, como novos tipos de avaliação ou regras de cálculo de nota.

O projeto respeita os requisitos definidos na atividade, bem como a modelagem proposta no diagrama UML, refletindo diretamente a estrutura de classes, relacionamentos e comportamentos esperados no sistema acadêmico simulado.

---

## **Integrantes do Projeto**
| Aluno | GitHub | LinkedIn |
|-------|--------|----------|
| Gustavo Saraiva Mariano | [![GitHub](https://img.shields.io/badge/github-black?style=for-the-badge&logo=github)](https://github.com/saraivagustavo) | [![LinkedIn](https://img.shields.io/badge/linkedin-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/gustavo-saraiva-mariano/) |
| Pedro Henrique Albani Nunes | [![GitHub](https://img.shields.io/badge/github-black?style=for-the-badge&logo=github)](https://github.com/PedroAlbaniNunes) | [![LinkedIn](https://img.shields.io/badge/linkedin-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/pedro-henrique-albani-nunes-33a729270/) |
