package br.edu.unicid.quiz_das_bandeiras_paulohenrique;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaQuiz extends AppCompatActivity {

    public static final String EXTRA_NOME_ALUNO = "NOME_ALUNO";

    // Representa uma pergunta: a bandeira, as alternativas e qual delas é a correta
    private static class Pergunta {
        int imagemBandeira;
        String[] alternativas;
        int indiceCorreto;

        Pergunta(int imagemBandeira, String[] alternativas, int indiceCorreto) {
            this.imagemBandeira = imagemBandeira;
            this.alternativas = alternativas;
            this.indiceCorreto = indiceCorreto;
        }
    }

    private Pergunta[] perguntas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;

    private ImageView imgBandeira;
    private RadioGroup radioGroupAlternativas;
    private RadioButton radioAlternativa1, radioAlternativa2, radioAlternativa3, radioAlternativa4;
    private Button btnResponder;
    private String nomeAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nomeAluno = getIntent().getStringExtra(EXTRA_NOME_ALUNO);

        montarPerguntas();

        imgBandeira = findViewById(R.id.imgBandeira);
        radioGroupAlternativas = findViewById(R.id.radioGroupAlternativas);
        radioAlternativa1 = findViewById(R.id.radioAlternativa1);
        radioAlternativa2 = findViewById(R.id.radioAlternativa2);
        radioAlternativa3 = findViewById(R.id.radioAlternativa3);
        radioAlternativa4 = findViewById(R.id.radioAlternativa4);
        btnResponder = findViewById(R.id.btnResponder);

        // Requisito: habilita o RESPONDER somente quando o usuário marca uma alternativa
        radioGroupAlternativas.setOnCheckedChangeListener((group, checkedId) ->
                btnResponder.setEnabled(checkedId != -1));

        btnResponder.setOnClickListener(v -> responder());

        // Requisito: não deixa voltar para a pergunta anterior.
        // Como usamos uma única Activity para todas as perguntas, não há tela anterior
        // para onde voltar — o botão físico/gesto de voltar apenas encerra o quiz
        // e retorna à Tela Principal (finish() volta para quem chamou esta Activity).
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });

        carregarPergunta();
    }

    private void montarPerguntas() {
        // Adicione aqui uma linha para cada bandeira do seu quiz.
        // O último número é o índice (começando em 0) da alternativa correta.
        perguntas = new Pergunta[]{
                new Pergunta(R.drawable.bandeira_brasil,
                        new String[]{"Haiti", "Brasil", "Jamaica", "Guiana"}, 1),
                new Pergunta(R.drawable.bandeira_chile,
                        new String[]{"Texas", "Cuba", "Chile", "Porto Rico"}, 2),
                new Pergunta(R.drawable.bandeira_argentina,
                        new String[]{"Argentina", "Uruguai", "Grécia", "Nicarágua"}, 0),
                new Pergunta(R.drawable.bandeira_alemanha,
                        new String[]{"Bélgica", "Alemanha", "Chade", "Romênia"}, 1),
                new Pergunta(R.drawable.bandeira_japao,
                        new String[]{"Bangladesh", "Palau", "Japão", "Coreia do Sul"}, 2),
                new Pergunta(R.drawable.bandeira_italia,
                        new String[]{"Itália", "México", "Irlanda", "Costa do Marfim"}, 0),
                new Pergunta(R.drawable.bandeira_canada,
                        new String[]{"Peru", "Canadá", "Áustria", "Polônia"}, 1),
                new Pergunta(R.drawable.bandeira_mexico,
                        new String[]{"Itália", "Irlanda", "México", "Índia"}, 2),
                new Pergunta(R.drawable.bandeira_portugal,
                        new String[]{"Portugal", "Cabo Verde", "Guiné-Bissau", "Angola"}, 0),
                new Pergunta(R.drawable.bandeira_espanha,
                        new String[]{"Espanha", "Colômbia", "Venezuela", "Equador"}, 0),
        };
    }

    private void carregarPergunta() {
        if (perguntaAtual >= perguntas.length) {
            finalizarQuiz();
            return;
        }

        Pergunta pergunta = perguntas[perguntaAtual];

        imgBandeira.setImageResource(pergunta.imagemBandeira);

        radioGroupAlternativas.clearCheck();
        radioAlternativa1.setText(pergunta.alternativas[0]);
        radioAlternativa2.setText(pergunta.alternativas[1]);
        radioAlternativa3.setText(pergunta.alternativas[2]);
        radioAlternativa4.setText(pergunta.alternativas[3]);

        // Requisito: RESPONDER começa desabilitado em cada pergunta nova
        btnResponder.setEnabled(false);
    }

    private void responder() {
        Pergunta pergunta = perguntas[perguntaAtual];
        int idSelecionado = radioGroupAlternativas.getCheckedRadioButtonId();

        int indiceSelecionado = -1;
        if (idSelecionado == radioAlternativa1.getId()) indiceSelecionado = 0;
        else if (idSelecionado == radioAlternativa2.getId()) indiceSelecionado = 1;
        else if (idSelecionado == radioAlternativa3.getId()) indiceSelecionado = 2;
        else if (idSelecionado == radioAlternativa4.getId()) indiceSelecionado = 3;

        // Requisito: contabiliza a pontuação a cada acerto
        if (indiceSelecionado == pergunta.indiceCorreto) {
            pontuacao++;
        }
        perguntaAtual++;
        carregarPergunta();
    }

    private void finalizarQuiz() {
        Intent intent = new Intent(TelaQuiz.this, TelaRanking.class);
        intent.putExtra(TelaRanking.EXTRA_NOME_ALUNO, nomeAluno);
        intent.putExtra(TelaRanking.EXTRA_PONTUACAO, pontuacao);
        startActivity(intent);
        finish(); // Sai da TelaQuiz; voltar não deve reabrir o quiz no meio
    }
}