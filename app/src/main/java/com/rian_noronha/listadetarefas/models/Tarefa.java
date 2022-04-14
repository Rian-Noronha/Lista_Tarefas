package com.rian_noronha.listadetarefas.models;

import java.io.Serializable;

//uso a interface Serializable para poder portar objetos entrea activities.
public class Tarefa implements Serializable {


    private Long id;
    private String nomeTarefa;

    public Tarefa(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }
}
