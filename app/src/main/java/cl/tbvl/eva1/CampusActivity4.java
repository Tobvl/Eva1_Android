package cl.tbvl.eva1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class CampusActivity4 extends AppCompatActivity {

    private Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_campus4);

        Button btnVolverAtras, btnEnviarChecklist;

        btnVolverAtras = findViewById(R.id.btnVolver);
        btnEnviarChecklist = findViewById(R.id.btnEnviarChecklist);

        btnVolverAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mToast != null){ mToast.cancel();}
                finish();
            }
        });
        btnEnviarChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mToast != null){ mToast.cancel();}

                Intent i = new Intent(
                        CampusActivity4.this,
                        EnviarChecklistActivity.class);
                startActivity(i);

            }
        });

        LinearLayout contenedorItems = findViewById(R.id.linearLayoutItemContainer);
        String archivoCampusItems = "campusItems_campus4.json";
        String archivoJSON = cargarJSON(archivoCampusItems);

        try {
            JSONObject objetoJSON = new JSONObject(archivoJSON);
            JSONArray arraySecciones = objetoJSON.getJSONArray("items");

            // Iterar el array de secciones
            for (int i=0; i<arraySecciones.length(); i++){
                JSONObject seccion = arraySecciones.getJSONObject(i); // saco el item de la seccion
                String nombreSeccion = seccion.getString("nombreSeccion");

                //creo un textview para la sección
                agregarSeccion(contenedorItems, nombreSeccion);

                // agarro los items de la sección como array
                JSONArray itemsSeccion = seccion.getJSONArray("itemsSeccion");

                // iterar los items de cada sección
                for (int j=0; j<itemsSeccion.length(); j++){
                    JSONObject itemSeccion = itemsSeccion.getJSONObject(j);
                    String nombreItem = itemSeccion.getString("nombre");
                    String descripcionItem = itemSeccion.getString("descripcion");

                    agregarItemSeccion(contenedorItems, nombreItem, descripcionItem);

                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // agregar seccion al LinearLayout padre
    private void agregarSeccion(LinearLayout contenedorPadre, String nombreSeccion){
        TextView tituloSeccion = new TextView(this);
        tituloSeccion.setText(nombreSeccion);
        tituloSeccion.setTextSize(20f);
        tituloSeccion.setPadding(0, 48, 0, 16);
        tituloSeccion.setGravity(Gravity.CENTER);
        contenedorPadre.addView(tituloSeccion);
    }

    // agregar items a sección
    private void agregarItemSeccion(LinearLayout contenedorPadre, String nombre, String descripcion){
        // Crear TextView para el nombre
        TextView itemTitle = new TextView(this);
        itemTitle.setText(nombre);
        itemTitle.setTextSize(18f);
        itemTitle.setPadding(0, 28, 0, 8);
        contenedorPadre.addView(itemTitle);

        // Crear TextView para la descripción
        TextView itemDescription = new TextView(this);
        itemDescription.setText(descripcion);
        itemDescription.setPadding(0, 0, 0, 16);
        contenedorPadre.addView(itemDescription);

        // Crear RadioGroup para OK/NO OK
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(RadioGroup.HORIZONTAL);

        RadioButton radioOk = new RadioButton(this);
        radioOk.setText("OK");
        radioGroup.addView(radioOk);

        RadioButton radioNoOk = new RadioButton(this);
        radioNoOk.setText("NO OK");
        radioGroup.addView(radioNoOk);

        contenedorPadre.addView(radioGroup);

        // Crear EditText para el detalle opcional
        EditText detalleOpcional = new EditText(this);
        detalleOpcional.setHint("Detalle opcional");
        contenedorPadre.addView(detalleOpcional);
    }

    private String cargarJSON(String archivoCampusItems) {
        String json;
        try {
            InputStream is = getAssets().open(archivoCampusItems);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}