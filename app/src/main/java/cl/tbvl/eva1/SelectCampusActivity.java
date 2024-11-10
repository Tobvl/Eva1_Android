package cl.tbvl.eva1;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SelectCampusActivity extends AppCompatActivity {


    String usernameText;
    private Toast mToast;
    private LinearLayout selectedLayout;
    Class selectedActivity = null;

    private void cambiarFondoSeleccionCampus(LinearLayout ly){
        if (selectedLayout != null){
            selectedLayout.setBackground(null);
        }
        ly.setBackgroundResource(R.drawable.linearlayout1_selected);
        selectedLayout = ly;
    }

    private void moverBoton(View view){
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(300);
        shake.setRepeatCount(3);
        shake.setRepeatMode(Animation.REVERSE);

        // rotar el botón
        ObjectAnimator rotacion = ObjectAnimator.ofFloat(view, "rotation", 0, 3, -3, 3, -3, 0);
        rotacion.setDuration(1200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotacion);
        view.startAnimation(shake);
        animatorSet.start();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_campus);

        Button btnSelectCampus1 = findViewById(R.id.btnSelectCampus1);
        Button btnSelectCampus2 = findViewById(R.id.btnSelectCampus2);
        Button btnSelectCampus3 = findViewById(R.id.btnSelectCampus3);
        Button btnSelectCampus4 = findViewById(R.id.btnSelectCampus4);
        Button btnSelectCampus5 = findViewById(R.id.btnSelectCampus5);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        Button btnIniciarChecklist = findViewById(R.id.btnIniciarChecklist);

        TextView tvBienvenidoUsuarioText = findViewById(R.id.tvBienvenidoUsuario);
        Bundle b = getIntent().getExtras();
        // Si el assert es falso, ocurre un error
        assert b != null;
        usernameText = b.getString("loginUsername");
        tvBienvenidoUsuarioText.setText("Bienvenido! \n " + usernameText);



        View.OnClickListener listenerComun = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mToast != null){ mToast.cancel();}

                if (view.getId() == btnSelectCampus1.getId()) {
                    selectedActivity = CampusActivity1.class;
                    // Cambiar fondo de linearLayoutCampus1
                    cambiarFondoSeleccionCampus((LinearLayout) view.getParent());

                }else if (view.getId() == btnSelectCampus2.getId()) {
                    selectedActivity = CampusActivity2.class;
                    cambiarFondoSeleccionCampus((LinearLayout) view.getParent());

                }else if (view.getId() == btnSelectCampus3.getId()) {
                    selectedActivity = CampusActivity3.class;
                    cambiarFondoSeleccionCampus((LinearLayout) view.getParent());

                }else if (view.getId() == btnSelectCampus4.getId()) {
                    selectedActivity = CampusActivity4.class;
                    cambiarFondoSeleccionCampus((LinearLayout) view.getParent());

                }else if (view.getId() == btnSelectCampus5.getId()) {
                    selectedActivity = CampusActivity5.class;
                    cambiarFondoSeleccionCampus((LinearLayout) view.getParent());

                }else if (view.getId() == btnCerrarSesion.getId()){
                    Intent i = new Intent(
                            SelectCampusActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }else if (view.getId() == btnIniciarChecklist.getId()){
                    if (selectedActivity != null){
                        Intent i = new Intent(
                                SelectCampusActivity.this,
                                selectedActivity);
                        i.putExtra("loginUsername",
                                usernameText);
                        startActivity(i);
                    }else {
                        moverBoton(btnSelectCampus1);
                        moverBoton(btnSelectCampus2);
                        moverBoton(btnSelectCampus3);
                        moverBoton(btnSelectCampus4);
                        moverBoton(btnSelectCampus5);
                        mToast = Toast.makeText(getApplicationContext(),
                                "Debes seleccionar un campus!",
                                Toast.LENGTH_LONG);
                        mToast.show();

                    }

                }
            }
        };

        btnSelectCampus1.setOnClickListener(listenerComun);
        btnSelectCampus2.setOnClickListener(listenerComun);
        btnSelectCampus3.setOnClickListener(listenerComun);
        btnSelectCampus4.setOnClickListener(listenerComun);
        btnSelectCampus5.setOnClickListener(listenerComun);
        btnCerrarSesion.setOnClickListener(listenerComun);
        btnIniciarChecklist.setOnClickListener(listenerComun);





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}