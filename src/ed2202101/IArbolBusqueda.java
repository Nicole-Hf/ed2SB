/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2202101;

import ed2202101.excepciones.ExcepcionClaveNoExiste;
import java.util.List;

/**
 *
 * @author alejandra
 * @param <K>
 * @param <V>
 */
public interface IArbolBusqueda<K extends Comparable<K>, V> {
    void insertar(K claveAInsertar, V valorAInsertar);
    V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste;
    V buscar(K claveABuscar);
    boolean contiene(K claveABuscar);
    int size();
    int altura();
    int nivel();
    void vaciar();
    boolean esArbolVacio();
    List<K> recorridoPorNiveles();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPostOrden();  
    int cantidadDeNodosConAmbosHijos();
    int cantidadDeNodosConAmbosHijosRec();
    int cantidadNodosConAmbosHijosEnN(int nivel);
    int cantidadNodosConAmbosHijosEnNRec(int nivel);
    int cantidadNodosConUnHijoEnN(int nivel);
    int nroNodosConInOrden();
    K menorLlave();
    boolean esArboLleno();
    K mayorLLave();
    boolean hayHojasEnUltimoNivel();
    boolean tieneHojasOTodosHijos();
    boolean sonIguales(ArbolMViasBusqueda<K,V> arbolCopia);
}

