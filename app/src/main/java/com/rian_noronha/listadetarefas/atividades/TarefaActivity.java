package com.rian_noronha.listadetarefas.atividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.rian_noronha.listadetarefas.R;
import com.rian_noronha.listadetarefas.help.TarefaDao;
import com.rian_noronha.listadetarefas.models.Tarefa;

public class TarefaActivity extends AppCompatActivity {

    private Tarefa tarefaAtual;
    private EditText editTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        this.editTarefa = findViewById(R.id.editTarefa);

        //recuperar a tarefa que está vindo.
        this.tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar minha tarefa atual
        if(this.tarefaAtual != null){
            editTarefa.setText(this.tarefaAtual.getNomeTarefa());
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adicionar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id){
            case R.id.idSalvar:

                TarefaDao tarefaDao = new TarefaDao(getApplicationContext());
                //Quer dizer que eu posso editar a tarefa.
                if(this.tarefaAtual != null){

                    String nomeTarefa = this.editTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(this.tarefaAtual.getId());

                        //agora, atualizo lá meu banco.
                        if(tarefaDao.atualizar(tarefa)){

                            finish();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Tarefa atualizada:)",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Tarefa não atualizada:(",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }


                    }

                }else{
                    //Vamo pra cima do botão salvar
                    String nomeTarefa = this.editTarefa.getText().toString();

                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        if(tarefaDao.salvar(tarefa)){
                            finish();
                            //Quando o usuário quiser finalizar a entrada da tarefa, termino a activity.
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Tarefa está dentro:)",
                                    Toast.LENGTH_SHORT
                            ).show();



                        }else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Tarefa não foi adicionada:(",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }


                    }
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}