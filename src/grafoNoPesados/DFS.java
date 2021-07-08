/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafoNoPesados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class DFS {
    private UtilsRecorridos controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;
    
    public DFS(Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        //ya esta todo desmarcado
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices());
        procesarDFS(posVerticePartida);
    }

    public void procesarDFS(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyacentesDeVertice(posVertice);
        for (Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if (!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                procesarDFS(posVerticeAdyacente);
            }
        }
    }
    
    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }
    
    public Iterable<Integer> elRecorrido() {
        return this.recorrido;
    }
    
    public boolean hayCaminosATodos() {
        return controlMarcados.estanTodosMarcado();
    }
    
}
