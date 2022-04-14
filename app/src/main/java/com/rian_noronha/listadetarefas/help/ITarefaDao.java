package com.rian_noronha.listadetarefas.help;

import com.rian_noronha.listadetarefas.models.Tarefa;

import java.util.List;

public interface ITarefaDao {

    boolean salvar(Tarefa tarefa);
    boolean atualizar(Tarefa tarefa);
    boolean deletar(Tarefa tarefa);
    List<Tarefa> listar();
}
