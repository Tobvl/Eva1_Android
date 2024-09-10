package cl.tbvl.eva1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText tvUsernameText, tvPasswordText; // id: et_loginUsername | id : et_loginPassword
    Button btnLoginButton; // id: btn_loginButton

    private void cambiarFondoEditText(EditText et){
        et.setBackgroundResource(R.drawable.edittext1_borderojo);
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                et.setBackgroundResource(R.drawable.edittext1), 3000);
    }

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnLoginButton = findViewById(R.id.btn_loginButton);
        tvUsernameText = findViewById(R.id.et_loginUsername);
        tvPasswordText = findViewById(R.id.et_loginPassword);

        btnLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if (tvUsernameText.getText().toString().trim().isEmpty() ||
                    tvPasswordText.getText().toString().trim().isEmpty()) {
                    if (mToast != null){
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(getApplicationContext(),
                            "Debes ingresar todos los datos!",
                            Toast.LENGTH_LONG);
                    mToast.show();

                    if (tvUsernameText.getText().toString().trim().isEmpty()){
                        cambiarFondoEditText(tvUsernameText);
                    }
                    if (tvPasswordText.getText().toString().trim().isEmpty()){
                        cambiarFondoEditText(tvPasswordText);
                    }


                } else {
                    // Si el usuario y clave no son test
                    if (!tvUsernameText.getText().toString().trim().equals("test") ||
                            !tvPasswordText.getText().toString().trim().equals("test")){
                        if (mToast != null){
                            mToast.cancel();
                        }
                        mToast = Toast.makeText(getApplicationContext(),
                                "Debes ingresar un usuario válido! (test test)",
                                Toast.LENGTH_LONG);
                        mToast.show();
                        return;
                    }
                    // Cambiar de activity

                    String passUsername = tvUsernameText.getText().toString();

                    Intent i = new Intent(
                            MainActivity.this,
                            SelectCampusActivity.class);
                    i.putExtra("loginUsername",
                            passUsername);
                    startActivity(i);
                    finish();
                }
            }

        });

        Log.i("hola", "mensaje");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}