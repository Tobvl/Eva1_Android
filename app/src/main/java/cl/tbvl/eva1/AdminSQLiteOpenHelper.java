package cl.tbvl.eva1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;

import java.util.ArrayList;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper (
            @Nullable Context context,
            @Nullable String name,
            @Nullable SQLiteDatabase.CursorFactory factory,
            int version){
        super(context, name, factory, version);
    }

    public static void instantiate(Context ctx) {
        try {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                AdminSQLiteOpenHelper.dbName,
                null,
                1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BaseDeDatos.execSQL("SELECT * from usuarios");
        BaseDeDatos.execSQL("SELECT * from checklists");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String db = "CREATE TABLE IF NOT EXISTS usuarios(" +
            "id integer primary key AUTOINCREMENT, " +
            "username text, " +
            "password text, " +
            "rut text" +
            ")";

    public static String dbChecklists = "CREATE TABLE IF NOT EXISTS checklists(" +
            "id integer primary key AUTOINCREMENT, " +
            "username text, " +
            "checklist text, " +
            "date text" +
            ")";

    public static String dbName = "administracion";
    public static String tableNameChecklists = "checklists";

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL(AdminSQLiteOpenHelper.db);
        BaseDeDatos.execSQL(AdminSQLiteOpenHelper.dbChecklists);
    }

    @Override
    public void onUpgrade(SQLiteDatabase BaseDeDatos, int i, int i1) {
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuarios");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS checklists");
        onCreate(BaseDeDatos);
    }

    public static void clearDatabase(Context ctx, String dbname) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                dbname,
                null,
                1);

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        BaseDeDatos.execSQL("DROP TABLE IF EXISTS usuarios");
        BaseDeDatos.execSQL("DROP TABLE IF EXISTS checklists");
        BaseDeDatos.execSQL(AdminSQLiteOpenHelper.db);
        BaseDeDatos.execSQL(AdminSQLiteOpenHelper.dbChecklists);
        BaseDeDatos.close();

        ctx.deleteDatabase("administracion");

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

    public static boolean login(Context ctx, String user, String pass){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                "administracion",
                null,
                1
        );

        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        // Consulta para obtener la contraseña del usuario
        String query = "SELECT password FROM usuarios WHERE username = ?";
        Cursor cursor = BaseDeDatos.rawQuery(query, new String[]{user});

        // Verificar si el usuario existe
        if (cursor.moveToFirst()) {
            // Obtener la contraseña almacenada
            String passwordAlmacenada = cursor.getString(0);

            // Comparar la contraseña proporcionada con la almacenada
            if (passwordAlmacenada.equals(pass)) {
                // Si las contraseñas coinciden
                cursor.close();
                BaseDeDatos.close();
                return true;
            } else {
                // Si las contraseñas no coinciden
                cursor.close();
                BaseDeDatos.close();
                return false;
            }
        } else {
            // El usuario no existe
            cursor.close();
            BaseDeDatos.close();
            return false;
        }

    }

    public static void sendChecklist(Context ctx, String user, String data){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                ctx,
                "administracion",
                null,
                1
        );

        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();


        Date fechaActual = Calendar.getInstance().getTime();

        ContentValues registro = new ContentValues();
        registro.put("username", user);
        registro.put("checklist", data);
        registro.put("date", fechaActual.toString());


        BaseDeDatos.insert(
                "checklists",
                null,
                registro);
        BaseDeDatos.close();

    }

}
