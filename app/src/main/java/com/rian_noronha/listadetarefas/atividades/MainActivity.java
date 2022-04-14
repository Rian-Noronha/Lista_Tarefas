package com.rian_noronha.listadetarefas.atividades;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rian_noronha.listadetarefas.R;
import com.rian_noronha.listadetarefas.adapter.Adapter;
import com.rian_noronha.listadetarefas.databinding.ActivityMainBinding;
import com.rian_noronha.listadetarefas.help.DbHelper;
import com.rian_noronha.listadetarefas.help.RecyclerItemClickListener;
import com.rian_noronha.listadetarefas.help.TarefaDao;
import com.rian_noronha.listadetarefas.models.Tarefa;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Adapter adapter;
    private List<Tarefa> tarefas = new ArrayList<>();
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerTarefa;
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



       /* //Usando o DB
        DbHelper db  = new DbHelper(getApplicationContext());

        //Chamar o ContenValues para passar valores para meu banco
        ContentValues cv = new ContentValues();
        cv.put("nome", "Teste");

        db.getWritableDatabase().insert("tarefas", null, cv);*/


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TarefaActivity.class);
                startActivity(intent);

            }
        });






    }

    @Override
    protected void onStart() {
        criarListaTarefas();
        configurarMeuRecyclerView();
        configurarTouchRecyclerView();
        super.onStart();
    }

    public void criarListaTarefas(){

        //Listar tarefas

        TarefaDao tarefaDao = new TarefaDao(getApplicationContext());

        //Pego pela TarefaDao a lista de tarefas postas pelo user no BD.
        this.tarefas = tarefaDao.listar();

        /*
        Exibe a lista de tarefas do RecyclerView

         */


    }

    public void configurarMeuRecyclerView(){
        //1.Criar meu adapter
        this.adapter = new Adapter(this.tarefas);
        //2.Criar meu layoutManager
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        //3.Configurar meu recycler
        this.recyclerTarefa = findViewById(R.id.recyclerTarefas);

        this.recyclerTarefa.setLayoutManager(layout);
        this.recyclerTarefa.setHasFixedSize(true);
        this.recyclerTarefa.addItemDecoration( new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        this.recyclerTarefa.setAdapter(adapter);
    }

    public void configurarTouchRecyclerView(){
        recyclerTarefa.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerTarefa,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                //Pego a tarefa na qual quero mexer
                                Tarefa tarefaSelecionada = tarefas.get(position);

                                //Envio para a tela de TarefaActivity
                                Intent intent = new Intent(MainActivity.this, TarefaActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar a tarefa selecionada.
                                tarefaSelecionada = tarefas.get(position);


                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                //Configurar título e mensagem
                                alert.setTitle("Confirmar exclusão da tarefa");
                                alert.setMessage("Confirmar a exclusão da tarefa: " + tarefaSelecionada.getNomeTarefa() + "?");

                                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TarefaDao tarefaDao = new TarefaDao(getApplicationContext());

                                        if(tarefaDao.deletar(tarefaSelecionada)){

                                            //Para a lista tirar a tarefa que foi excluída.
                                            onStart();
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Tarefa excluída:)",
                                                    Toast.LENGTH_SHORT
                                            ).show();

                                        }else{

                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Tarefa excluída:)",
                                                    Toast.LENGTH_SHORT
                                            ).show();


                                        }



                                    }
                                });


                                /*Se eu fosse configurar algo para o negative:
                                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }

                                });*/

                                //Mas, não vou, então:
                                alert.setNegativeButton("Não", null);

                                //Mostrar a dialog
                                alert.create();
                                alert.show();


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );
    }


   /* @Override
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

                //Vamo pra cima do botão salvar

                break;

        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.content);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}