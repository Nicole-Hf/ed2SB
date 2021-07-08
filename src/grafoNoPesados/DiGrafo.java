/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafoNoPesados;

import ed2202101.excepciones.ExcepcionAristaNoExiste;
import ed2202101.excepciones.ExcepcionAristaYaExiste;
import ed2202101.excepciones.ExcepcionNroVerticesInvalido;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Nicole
 */
public class DiGrafo extends Grafo {
    public DiGrafo() {
        super();
    }
    
    public DiGrafo(int nroInicialDeVertices) throws ExcepcionNroVerticesInvalido {
        super(nroInicialDeVertices);
    }

    @Override
    //sumar todos los size de todas las listas existentes
    public int cantidadDeAristas() {
        int cantidad = 0;
        for (int i = 0; i < this.listaDeAdyacencias.size(); i++) {
            List<Integer> adyacentesVertice = this.listaDeAdyacencias.get(i);
            int cantidadAdyacentes = adyacentesVertice.size();
            cantidad = cantidad + cantidadAdyacentes;
        }
        return cantidad;
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaNoExiste {
        if (this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaNoExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        int posicionDelDestino = adyacentesDelOrigen.indexOf(posVerticeDestino);
        adyacentesDelOrigen.remove(posicionDelDestino);
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) throws ExcepcionAristaYaExiste {
        if (super.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new ExcepcionAristaYaExiste();
        }
        List<Integer> adyacentesDelOrigen = this.listaDeAdyacencias.get(posVerticeOrigen);
        adyacentesDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacentesDelOrigen);        
    }

    @Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("Método no soportado en grafos dirigidos");
    }
    
    public int gradoDeEntradaDelVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradasDeVertice = 0;
        /*for (List<Integer> adyacentesDeUnVertice : super.listaDeAdyacencias) {
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente == posDeVertice) {
                    entradasDeVertice++;
                }
            }
        }*/
        for (int i = 0; i < super.listaDeAdyacencias.size(); i++) {
            Iterable<Integer> adyacentesDeUnVertice = super.adyacentesDeVertice(i);
            for (Integer posDeAdyacente : adyacentesDeUnVertice) {
                if (posDeAdyacente == posDeVertice) {
                    entradasDeVertice++;
                }
            }
        }
        
        return entradasDeVertice;
    }
    
    public int gradoDeSalidaDelVertice(int posDeVertice) {
        return super.gradoDeVertice(posDeVertice);
    }
    
    //super esta llamando de la clase principal
    //this esta llamando de la clase hijo
    
    /** Si un Digrafo es débilmente conexo
     * 
     */
    
    /** Si un Digrafo es fuertemente conexo
     * 
     */
    
    /** Hay Ciclo en un DiGrafo
     * Hacer el recorrido DFS desde el primer vertice disponible
     * Preguntar si hay aristas atrás
     * Preguntar si el último vértice visitado tiene como adyacente al primer
     * vértice
     */
}
