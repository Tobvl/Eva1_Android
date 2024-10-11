package cl.tbvl.eva1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    EditText tvRegUsernameText, tvRegPasswordText;
    Button btnRegistrarButton, btnVolverLoginButton;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        btnRegistrarButton = findViewById(R.id.btn_Registrar);
        btnVolverLoginButton = findViewById(R.id.btn_VolverLogin);
        tvRegUsernameText = findViewById(R.id.et_RegisterUsername);
        tvRegPasswordText = findViewById(R.id.et_RegisterPassword);


        btnRegistrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvRegPasswordText.getText().toString().trim().isEmpty() ||
                        tvRegPasswordText.getText().toString().trim().isEmpty()) {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getApplicationContext(),
                            "Debes ingresar todos los datos!",
                            Toast.LENGTH_LONG);
                    mToast.show();
                } else if (tvRegPasswordText.getText().toString().length() <= 4) {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getApplicationContext(),
                            "La contraseña debe contener más de 4 caracteres!",
                            Toast.LENGTH_LONG);
                    mToast.show();
                }
                String usuario = tvRegUsernameText.getText().toString();
                String password = tvRegPasswordText.getText().toString();

                boolean existeUser = AdminSQLiteOpenHelper.existeUsuario(getApplicationContext(), usuario);

                if (!existeUser){
                    ContentValues registro = new ContentValues();
                    registro.put("username", usuario);
                    registro.put("password", password);
                    AdminSQLiteOpenHelper.registrarUsuario(getApplicationContext(), registro);

                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getApplicationContext(),
                            "Usuario registrado correctamente!",
                            Toast.LENGTH_LONG);
                    mToast.show();
                }else {
                    if (mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getApplicationContext(),
                            "Usuario ya se encuentra registrado!",
                            Toast.LENGTH_LONG);
                    mToast.show();
                }

            }
        });

        btnVolverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLiteOpenHelper.listarUsuarios(getApplicationContext());
                Intent i = new Intent(
                        RegisterActivity.this,
                        MainActivity.class);
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