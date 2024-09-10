package cl.tbvl.eva1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EnviarChecklistActivity extends AppCompatActivity {

    Button btnCancelar, btnConfirmarEnvioChecklist;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enviar_checklist);


        btnCancelar = findViewById(R.id.btnCancelar);
        btnConfirmarEnvioChecklist = findViewById(R.id.btnConfirmarEnvioChecklist);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mToast != null){ mToast.cancel();}
                mToast.makeText(getApplicationContext(),
                        "Operación cancelada!",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
        btnConfirmarEnvioChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mToast != null){ mToast.cancel();}
                mToast.makeText(getApplicationContext(),
                        "Checklist enviado exitosamente!",
                        Toast.LENGTH_LONG).show();
                mToast.cancel();
                Intent i = new Intent(
                        EnviarChecklistActivity.this,
                        SelectCampusActivity.class);
                startActivity(i);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}