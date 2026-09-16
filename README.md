# Quiz das Bandeiras

Aplicativo Android desenvolvido para a disciplina de ADS (Análise e Desenvolvimento de Sistemas) da Unicid. O app apresenta um quiz de bandeiras de países, onde o usuário informa seu nome, responde a uma sequência de perguntas de múltipla escolha e, ao final, visualiza sua pontuação em uma tela de ranking.

## Funcionalidades

### Tela Inicial
- Campo obrigatório para o nome do usuário.
- Botão **INICIAR QUIZ** desabilitado por padrão, habilitado somente quando há texto no campo de nome (e desabilitado novamente se o campo for esvaziado).
- Botão **SAIR** encerra o aplicativo independentemente do nome ter sido preenchido.

### Tela de Perguntas
- Exibe a bandeira do país atual em um `ImageView`.
- Quatro alternativas em `RadioButton`, das quais apenas uma é correta.
- Botão **RESPONDER** habilitado somente após o usuário marcar uma alternativa.
- Pontuação incrementada a cada acerto.
- Não é possível voltar para perguntas anteriores — o botão de voltar do dispositivo encerra o quiz e retorna à Tela Principal.

### Tela de Ranking
- Exibe o nome do usuário e a quantidade de acertos.
- Botão **RESPONDER NOVAMENTE**: reinicia o quiz a partir da primeira pergunta.
- Botão **TELA PRINCIPAL**: retorna à tela inicial, limpando a pilha de telas anteriores.

## Estrutura do projeto

```
app/src/main/java/br/edu/unicid/quiz_das_bandeiras_paulohenrique/
├── MainActivity.java      # Tela inicial (nome do usuário)
├── TelaQuiz.java          # Tela de perguntas
└── TelaRanking.java       # Tela de resultado final

app/src/main/res/
├── layout/
│   ├── activity_main.xml
│   ├── activity_tela_quiz.xml
│   └── activity_tela_ranking.xml
├── color/
│   └── button_color_selector.xml   # Cor customizada para estado enabled/disabled dos botões
└── drawable/                       # Ícones e bandeiras dos países
```

## Tecnologias

- Java
- Android Studio
- ConstraintLayout / CardView (AndroidX)
- Gradle (Kotlin DSL)

## Como rodar

1. Clone o repositório e abra a pasta no Android Studio.
2. Aguarde a sincronização do Gradle.
3. Rode em um emulador ou dispositivo físico com **API mínima** conforme definida em `app/build.gradle.kts`.

## Como adicionar novas bandeiras ao quiz

Em `TelaQuiz.java`, no método `montarPerguntas()`, adicione uma nova linha seguindo o padrão:

```java
new Pergunta(R.drawable.bandeira_NOME_DO_PAIS,
        new String[]{"Alternativa 1", "Alternativa 2", "Alternativa 3", "Alternativa 4"},
        indiceDaAlternativaCorreta),
```

Não esqueça de adicionar a imagem correspondente em `res/drawable/` com o nome `bandeira_nome_do_pais`.

## Autor

Paulo Henrique — ADS, Unicid
