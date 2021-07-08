/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafoNoPesados;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Nicole
 */
public class BFS {
    private final UtilsRecorridos controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;
    
    public BFS(Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        //ya esta todo desmarcado
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices());
        ejecutarBFS(posVerticePartida);
    }
    
    public void ejecutarBFS(int posVertice) {
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVertice);
        controlMarcados.marcarVertice(posVertice);
        do {
            int posVerticeEnTurno = cola.poll();
            recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
                if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    controlMarcados.marcarVertice(posVerticeAdyacente);
                }
            }
        } while (!cola.isEmpty());
    }
    
    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }
    
    public Iterable<Integer> elRecorrido() {
        return this.recorrido;
    }
    
    public boolean hayCaminoATodos() {
        return controlMarcados.estanTodosMarcado();
    }   
}
