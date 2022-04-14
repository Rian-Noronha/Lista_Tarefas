package com.rian_noronha.listadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rian_noronha.listadetarefas.R;
import com.rian_noronha.listadetarefas.models.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Tarefa> tarefas = new ArrayList<>();

    public Adapter(List<Tarefa> tarefas){
        this.tarefas = tarefas;
    }

    public Adapter(){

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View tarefaLista = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.layout_adapter, parent, false);

        return new MyViewHolder(tarefaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Tarefa tarefa = tarefas.get(position);

        holder.textTarefa.setText(tarefa.getNomeTarefa());


    }

    @Override
    public int getItemCount() {
        return this.tarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textTarefa;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textTarefa = itemView.findViewById(R.id.textTarefa);

        }
    }


}
