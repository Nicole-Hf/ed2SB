/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafoNoPesados;

import ed2202101.excepciones.ExcepcionAristaNoExiste;
import ed2202101.excepciones.ExcepcionAristaYaExiste;
import ed2202101.excepciones.ExcepcionNroVerticesInvalido;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Nicole
 */
public class Grafo {
    protected List<List<Integer>> listaDeAdyacencias; 
    protected BFS bfs;
    protected DFS dfs;
    protected UtilsRecorridos marcados;
    
    public Grafo() {
        this.listaDeAdyacencias = new ArrayList<>();
    }
    
    public Grafo(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        if (nroInicialDeVertices <= 0) {
            throw new ExcepcionNroVerticesInvalido();
        }
        this.listaDeAdyacencias = new ArrayList<>();
        for (int i = 0; i < nroInicialDeVertices; i++) {
            this.insertarVertice();
        }
    }

    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencias.add(adyacentesDeNuevoVertice);        
    }
    
    public int cantidadDeVertices() {
        return listaDeAdyacencias.size();
    }
    
    public int gradoDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        return adyacentesDelVertice.size();
    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || 
                posicionDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("No existe vértice en la posición " 
                    + posicionDeVertice + " en este grafo.");
        }       
    }
    
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        //no dirigido
        if (posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            adyacentesDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }
    
    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        return adyacentesDelOrigen.contains(posVerticeDestino);
    }
    
    public Iterable<Integer> adyacentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice = this.listaDeAdyacencias.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacentes = adyacentesDelVertice;
        return iterableDeAdyacentes;
    }
    
    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.listaDeAdyacencias.get(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (i == posDeAdyacente) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        return cantLazos + (cantAristas / 2);
    }
    
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
        if (posVerticeOrigen != posicionDelDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencias.get(posVerticeDestino);
            int posicionDelOrigen = adyacentesDelDestino.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }
    
    public void eliminarVertice(int posDeVertice) throws ExcepcionAristaNoExiste {
        validarVertice(posDeVertice);
        List<Integer> adyacentesVertice = this.listaDeAdyacencias.get(posDeVertice);
        while (adyacentesVertice.size() > 0) {
            int posVerticeDestino = adyacentesVertice.get(0);
            this.eliminarArista(posDeVertice, posVerticeDestino);
        }       
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesAlVertice = this.listaDeAdyacencias.get(i);
            for (Integer posAdyacente : adyacentesAlVertice) {
                if (posAdyacente > posDeVertice) {
                    int posicion = adyacentesAlVertice.indexOf(posAdyacente);
                    adyacentesAlVertice.set(posicion, posAdyacente - 1);
                }               
            }           
        }
        this.listaDeAdyacencias.remove(posDeVertice);       
    }

    @Override
    public String toString() {
        if (this.cantidadDeVertices() == 0) {
            return "(Grafo Vacío)";
        }
        //desmarcarTodos();
        String grafo = "";       
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            grafo = grafo + "(" + i + ")" + "->";
            List<Integer> adyacentesDeVertice = this.listaDeAdyacencias.get(i);
            for (Integer posAdyacente : adyacentesDeVertice) {
                grafo += "{" + i + "," + posAdyacente + "}";               
            }
            grafo = grafo + "\n";
        }
        return grafo;
    }
    
    /** Grafo Conexo
     * - Hacer un recorrido BFS o DFS desde el primer vertice disponible.
     * - Luego preguntar si todos los vertices están marcados
     * - Retornar True o False 
     */
    public boolean esConexo() {     
        bfs.ejecutarBFS(0);
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            if (!marcados.estaVerticeMarcado(i)) {
                return false;
            }
        }
        return true;      
    }
    
    /** Cantidad de Islas
     * - Desmarcar todos los vertices
     * - Recorrer el grafo y preguntar si no está marcado 
     * - Si no está marcado, hacer un recorrido desde ese vertice y contar
     * - Retornar el contador
     */
    public int cantidadDeIslas() {
        int contador = 0;
        marcados.desmarcarTodos();
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            if (!marcados.estaVerticeMarcado(i)) {
                dfs.procesarDFS(i);
                contador++;
            }
        }
        return contador;
    }
    
    /** Si hay ciclo en un Grafo
     * - Hacer un recorrido desde el primer vértice disponible
     * - Preguntar si todos los vértice están marcados
     * y si solo tienen un adyacente
     * - Si es así ver si el último vértice visitado tiene como adyacente al 
     * primer vértice
     */
    public boolean hayCiclo() {
        bfs.ejecutarBFS(0);
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesAlVertice = this.listaDeAdyacencias.get(i);
            if (marcados.estaVerticeMarcado(i) && adyacentesAlVertice.size() == 1) {
                return true;
            }
        }
        return false;
    }
}
