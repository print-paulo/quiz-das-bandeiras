package br.edu.unicid.quiz_das_bandeiras_paulohenrique;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText edtNameInput;
    private Button btnIniciarQuiz, btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Muda as cores das barras de status
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);

        edtNameInput = findViewById(R.id.edtNameInput);
        btnIniciarQuiz = findViewById(R.id.btnIniciarQuiz);
        btnSair = findViewById(R.id.btnSair);

        // Requisito: "INICIAR QUIZ" começa desabilitado por padrão
        btnIniciarQuiz.setEnabled(false);

        edtNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // não precisa fazer nada aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // não precisa fazer nada aqui
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Requisito: habilita com texto digitado, desabilita se o campo voltar a ficar vazio
                boolean temNome = s.toString().trim().length() > 0;
                btnIniciarQuiz.setEnabled(temNome);
            }
        });

        btnIniciarQuiz.setOnClickListener(v -> {
            String nomeAluno = edtNameInput.getText().toString().trim();
            Intent intent = new Intent(MainActivity.this, TelaQuiz.class);
            intent.putExtra("NOME_ALUNO", nomeAluno);
            startActivity(intent);
        });

        // Requisito: SAIR encerra o app independente do nome ter sido digitado ou não
        btnSair.setOnClickListener(v -> finishAffinity());
    }
}