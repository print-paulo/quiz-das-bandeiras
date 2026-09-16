package br.edu.unicid.quiz_das_bandeiras_paulohenrique;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaRanking extends AppCompatActivity {

    public static final String EXTRA_NOME_ALUNO = "NOME_ALUNO";
    public static final String EXTRA_PONTUACAO = "PONTUACAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_ranking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String nomeAluno = getIntent().getStringExtra(EXTRA_NOME_ALUNO);
        int pontuacao = getIntent().getIntExtra(EXTRA_PONTUACAO, 0);

        TextView txtNomeAluno = findViewById(R.id.txtNomeAluno);
        TextView txtPontuacao = findViewById(R.id.txtPontuacao);
        txtNomeAluno.setText(nomeAluno);
        txtPontuacao.setText(String.valueOf(pontuacao));

        Button btnResponderNovamente = findViewById(R.id.btnResponderNovamente);
        Button btnTelaPrincipal = findViewById(R.id.btnTelaPrincipal);

        // Requisito: "RESPONDER NOVAMENTE" abre o quiz do zero, na primeira pergunta
        btnResponderNovamente.setOnClickListener(v -> {
            Intent intent = new Intent(TelaRanking.this, TelaQuiz.class);
            intent.putExtra(TelaQuiz.EXTRA_NOME_ALUNO, nomeAluno);
            startActivity(intent);
            finish();
        });

        // Requisito: "TELA PRINCIPAL" retorna à primeira tela do app
        btnTelaPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(TelaRanking.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}