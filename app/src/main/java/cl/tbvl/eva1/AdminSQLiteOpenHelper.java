package cl.tbvl.eva1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper (
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version){
        super(context, name, factory, version);
    }

    public static String dbName = "administracion";

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("CREATE TABLE usuarios(id integer primary key AUTOINCREMENT, username text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int i, int i1) {
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(BaseDeDatos);
    }

    public static boolean existeUsuario(Context ctx, String usuario) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                AdminSQLiteOpenHelper.dbName,
                null,
                1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String query = "SELECT * from usuarios WHERE username = ?";

        Cursor cursor = BaseDeDatos.rawQuery(query, new String[]{usuario});
        boolean existe = cursor.getCount() > 0;

        cursor.close();
        BaseDeDatos.close();

        return existe; // True si existe, False si no existe el user
    }


    public static void registrarUsuario(Context ctx, ContentValues registro) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                AdminSQLiteOpenHelper.dbName,
                null,
                1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BaseDeDatos.insert(
                "usuarios",
                null,
                registro);
        BaseDeDatos.close();

    }

    public static void listarUsuarios(Context ctx) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                "administracion",
                null,
                1
        );

        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        // Consulta para obtener todos los usuarios
        String query = "SELECT username FROM usuarios";
        Cursor cursor = BaseDeDatos.rawQuery(query, null);

        // Iterar sobre el cursor para obtener los usuarios
        if (cursor.moveToFirst()) {
            do {
                // Obtener el nombre de usuario desde el cursor
                String username = cursor.getString(0);
                // Imprimir el nombre de usuario
                System.out.println("Usuario: " + username);
            } while (cursor.moveToNext());
        } else {
            System.out.println("No hay usuarios en la base de datos.");
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        BaseDeDatos.close();
    }

}
