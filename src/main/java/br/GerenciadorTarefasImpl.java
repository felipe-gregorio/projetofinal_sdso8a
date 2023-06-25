package br;

/**
 *
 * @author felps
 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class GerenciadorTarefasImpl extends UnicastRemoteObject implements GerenciadorTarefas {
    private List<Tarefa> tarefas;
    private ReentrantLock lock;

    public GerenciadorTarefasImpl() throws RemoteException {
        super();
        tarefas = new ArrayList<>();
        lock = new ReentrantLock();
    }

    @Override
    public void adicionarTarefa(String descricao) throws RemoteException {
        lock.lock();
        try {
            Tarefa tarefa = new Tarefa(descricao);
            tarefas.add(tarefa);
            System.out.println("Tarefa adicionada: " + descricao);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removerTarefa(int id) throws RemoteException {
        lock.lock();
        try {
            if (id >= 0 && id < tarefas.size()) {
                Tarefa descricao = tarefas.remove(id);
                System.out.println("Tarefa removida: " + descricao);
            } else {
                System.out.println("ID de tarefa inválido");
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String listarTarefas() throws RemoteException {
        lock.lock();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Tarefas:\n");
            for (int i = 0; i < tarefas.size(); i++) {
                Tarefa tarefa = tarefas.get(i);
                sb.append(i).append(": ").append(tarefa.getDescricao()).append("\n");
            }
            return sb.toString();
        } finally {
            lock.unlock();
        }
    }
}
