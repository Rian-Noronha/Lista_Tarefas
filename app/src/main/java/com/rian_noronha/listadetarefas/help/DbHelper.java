package com.rian_noronha.listadetarefas.help;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    //Esta é a versão inicial do meu app.
    public static final int VERSION = 2;
    public static final String NOME_DB = "Db_Tarefas";
    public static final String TABELA_TAREFAS = "tarefas";

    /*O construtor gerado automaticamente.
    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }*/

    //Criei alguns campos estáticos e finais para passar.
    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL)";

        try{

            db.execSQL(sql);
            Log.i("Info DB:", "Sucesso ao criar a tabela-" + "");

        }catch (Exception e){
            Log.i("Info DB: ", "O banco, infelizamente, não foi criado." + e.getMessage());
        }


    }


    //Aqui faço atualizações nos dados do meu app
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS;
        onCreate(db);

        try{

            db.execSQL(sql);
            Log.i("INFO DB: ", "Table deletada.");

        }catch(Exception e){

            Log.i("INFO DB: ", "Table não foi deletada." + e.getMessage());


        }

    }
}
