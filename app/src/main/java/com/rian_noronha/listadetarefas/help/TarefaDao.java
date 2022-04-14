package com.rian_noronha.listadetarefas.help;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rian_noronha.listadetarefas.atividades.TarefaActivity;
import com.rian_noronha.listadetarefas.models.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDao implements ITarefaDao {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDao(Context context){
        DbHelper db = new DbHelper(context);
        this.escreve = db.getWritableDatabase();
        this.le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        try{

            //O content me permite colocar valores no meu bd. Passo a chave da minha tabela e o valor que quero entrar.
            ContentValues cv = new ContentValues();

            cv.put("nome", tarefa.getNomeTarefa());

            //O escreve, de fato, entra com o valores. Pega a tabela, o null diz que só entrará valores se houverem e o cv contém o que quero passar.
            escreve.insert(DbHelper.TABELA_TAREFAS, null, cv);

            Log.i("INFO DB: " , "Sua tarefa foi salva com sucesso!");

        }catch (Exception e){
            Log.i("INFO DB: ", "Dados não foram salvos. " + e.getMessage());

            return false;
        }


        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {


       try{

           ContentValues cv = new ContentValues();
           cv.put("nome", tarefa.getNomeTarefa());

          //Devo criar um array porque no quarto parâmetro do escreve() será usado como clausúla do where.
          String[] args = {tarefa.getId().toString()};

          //id=? é um caractere coringa que dirá à atualização que esperará pelo args para
           //determinar o id no qual a atualização vai vingar.
          escreve.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args);

          Log.i("Info Bd: ", "Tarefa atualizada:)");

       }catch (Exception e){

           Log.i("Info Bd: ", "Tarefa não atualizada:( | " + e.getMessage() );

       }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{

            String[] args = {tarefa.getId().toString()};
            escreve.delete(DbHelper.TABELA_TAREFAS, "id=?", args);
            Log.i("INFO BD: ", "Tarefa excluída com sucesso:)");



        }catch (Exception e){

            Log.i("INFO BD: ", "Tarefa não foi excluída:(");
            return false;

        }


        return true;
    }

    @Override
    public List<Tarefa> listar() {

        List<Tarefa> listaTarefas = new ArrayList<>();



        String sql = "SELECT * FROM " + DbHelper.TABELA_TAREFAS + ";";

        //cursor meio que guarda a referência de cada registro da minha tabela
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            Tarefa tarefa = new Tarefa();

            Long id = c.getLong( c.getColumnIndexOrThrow("id"));

            String nomeTarefa = c.getString( c.getColumnIndexOrThrow("nome"));

            tarefa.setId(id);

            tarefa.setNomeTarefa(nomeTarefa);

            listaTarefas.add(tarefa);

        }


        //enquanto houve registro


        return listaTarefas;
    }
}
