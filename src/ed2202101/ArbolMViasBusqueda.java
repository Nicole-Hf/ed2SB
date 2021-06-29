/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2202101;

import ed2202101.excepciones.ExcepcionClaveNoExiste;
import ed2202101.excepciones.ExcepcionOrdenInvalido;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Nicole
 * @param <K>
 * @param <V>
 */
public class ArbolMViasBusqueda<K extends Comparable<K>, V> 
        implements IArbolBusqueda<K,V> {
    
    protected NodoMVias<K,V> raiz;
    protected int orden;
    public static int POSICION_INVALIDA = -1;
    
    public ArbolMViasBusqueda() {
        this.orden = 3;
    }
    
    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < 3) {
            throw new ExcepcionOrdenInvalido();
        }
        
        this.orden = orden;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (valorAInsertar == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }
        
        if (claveAInsertar == null) {
            throw new RuntimeException("No se permite insertar claves nulas");
        }
        
        if (this.esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }
        
        NodoMVias<K,V> nodoActual = this.raiz;
        
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClave = this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if (posicionDeClave != POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClave, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();
            } else {
                if (nodoActual.esHoja()) {
                    if (nodoActual.estanClaveLLenas()) {
                        int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                        NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    } else {
                        this.insertarClaveYValorOrdenadaEnNodo(nodoActual, claveAInsertar, valorAInsertar);                     
                    }      
                    nodoActual = NodoMVias.nodoVacio();
                } else {
                    int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    if (nodoActual.esHijoVacio(posicionPorDondeBajar)) {
                        NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                        nodoActual = NodoMVias.nodoVacio();
                    } else {
                        nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                    }
                }               
            }           
        }
    }
    
    protected int obtenerPosicionDeClave(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveAInsertar.compareTo(claveActual) == 0) {              
                return i;
            }
        } 
        return POSICION_INVALIDA;
    }

    protected int obtenerPosicionPorDondeBajar(NodoMVias<K, V> nodoActual, K claveAInsertar) {
        int posicion = -1;      
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveAInsertar.compareTo(claveActual) == 0) {
                return i;
            }
            if (claveAInsertar.compareTo(claveActual) < 0) {                 
                return i;
            }
        }           
        if (claveAInsertar.compareTo(nodoActual.getClave(nodoActual.cantidadDeClaveNoVacias() - 1)) > 0) {
            posicion = nodoActual.cantidadDeClaveNoVacias();
        }
        return posicion;
    }

    protected void insertarClaveYValorOrdenadaEnNodo(NodoMVias<K, V> nodoActual, K claveAInsertar, V valorAInsertar) {                   
        for (int i = 0; i < this.orden - 1; i++) {
            if (!this.existe(nodoActual, claveAInsertar)) {
                K claveActual = nodoActual.getClave(i);
                if (nodoActual.esClaveVacia(i)) {               
                    nodoActual.setClave(i, claveAInsertar);
                    nodoActual.setValor(i, valorAInsertar);
                } else {
                    if (claveAInsertar.compareTo(claveActual) == 0) {
                        nodoActual.setValor(i, valorAInsertar);
                        return;
                    }               
                    K claveAnterior = nodoActual.getClave(i+1);
                    if (claveAInsertar.compareTo(claveActual) < 0) { 
                    //if (!this.existe(nodoActual,claveAInsertar)) {
                        nodoActual.setClave(i+1, claveActual);
                        nodoActual.setValor(i+1, nodoActual.getValor(i));
                        nodoActual.setClave(i, claveAInsertar);
                        nodoActual.setValor(i, valorAInsertar);
                    //}                                      
                    }
                    if (claveAInsertar.compareTo(claveActual) > 0) {
                        nodoActual.setClave(i+1, claveAInsertar);
                        nodoActual.setValor(i+1, valorAInsertar);                   
                    }
                    if (claveAnterior != null) {
                        claveAInsertar = claveAnterior;
                    }
                }                              
            }    
        }            
    }

    private boolean existe(NodoMVias<K, V> nodoActual, K claveABuscar) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return false;
        }        
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveABuscar.compareTo(claveActual) == 0) {
                return true;
            }
        }       
        return false;
    }
    
    @Override
    public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        
        this.raiz = eliminar(this.raiz, claveAEliminar);
         
        return valorAEliminar; 
    }
    
    private NodoMVias<K,V> eliminar(NodoMVias<K,V> nodoActual, K claveAEliminar) {
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveActual) == 0) {
                if (nodoActual.esHoja()) {
                    this.eliminarClaveYValorDelNodo(nodoActual, i);
                    if (nodoActual.cantidadDeClaveNoVacias() == 0) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //No es hoja el nodoActual
                K claveDeReemplazo;
                if (this.hayHijosMasAdelante(nodoActual, i)) {
                    claveDeReemplazo = this.buscarClaveSucesoraInOrden(nodoActual, claveAEliminar);
                } else {
                    claveDeReemplazo = this.buscarClavePredecesoraInOrden(nodoActual, claveAEliminar);
                }
                V valorDeReemplazo = buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual, claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorDeReemplazo);
                return nodoActual;
            }
            //no esta en la posicion i del nodoActual
            if (claveAEliminar.compareTo(claveActual) < 0) {
                NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }
        //si llego sin retornar, no baje por ningun lado y no la encontré
        NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias()),claveAEliminar);
        nodoActual.setHijo(nodoActual.cantidadDeClaveNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }
    
    protected void eliminarClaveYValorDelNodo(NodoMVias<K, V> nodoActual, int posicion) {
        if (posicion == 0) {
            if (nodoActual.cantidadDeClaveNoVacias() == 1) {
                nodoActual.setClave(posicion, null);
                nodoActual.setValor(posicion, null);
            } else {
                for (int i = posicion; i < nodoActual.cantidadDeClaveNoVacias(); i ++) {
                    if (i + 1 < nodoActual.cantidadDeClaveNoVacias()) {
                        nodoActual.setClave(i, nodoActual.getClave(i+1));
                        nodoActual.setValor(i, nodoActual.getValor(i+1));
                    } else {
                        nodoActual.setClave(i, null);
                        nodoActual.setValor(i, null);
                    }                    
                }
            }
        } else {
            if (posicion == nodoActual.cantidadDeClaveNoVacias()) {
                nodoActual.setClave(posicion, null);
                nodoActual.setValor(posicion, null);
            } else {
                for (int i = posicion; i < nodoActual.cantidadDeClaveNoVacias(); i++) {   
                    if (i + 1 < nodoActual.cantidadDeClaveNoVacias()) {
                        nodoActual.setClave(posicion, nodoActual.getClave(i+1));
                        nodoActual.setValor(posicion, nodoActual.getValor(i+1));
                    } else {
                        nodoActual.setClave(i, null);
                        nodoActual.setValor(i, null);
                    }                              
                }
            }                         
        }                    
    }

    protected boolean hayHijosMasAdelante(NodoMVias<K, V> nodoActual, int posicion) {
        for (int i = posicion; i < nodoActual.cantidadDeClaveNoVacias(); i++) {           
            if (!NodoMVias.esNodoVacio(nodoActual.getHijo(i+1))) {
                return true;
            }         
        }
        return false;
    }

    protected K buscarClaveSucesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        List<K> listaInOrden = new LinkedList<>();
        //listaInOrden = this.recorridoEnInOrden();
        this.recorridoEnInOrden(nodoActual, listaInOrden);
        int posicion = 0;
        K claveSucesora = (K) NodoMVias.datoVacio();
        while (posicion < listaInOrden.size()) {
            K claveActual = listaInOrden.get(posicion);
            if (claveAEliminar.compareTo(claveActual) < 0) {
                claveSucesora = claveActual;
                posicion = listaInOrden.size();
            }
            posicion++;
        }
        return claveSucesora;
    }

    protected K buscarClavePredecesoraInOrden(NodoMVias<K, V> nodoActual, K claveAEliminar) {
        List<K> listaInOrden = new LinkedList<>();
        //listaInOrden = this.recorridoEnInOrden();
        this.recorridoEnInOrden(nodoActual, listaInOrden);
        int posicion = 0;
        K clavePredecesora = (K) NodoMVias.datoVacio();
        while (posicion < listaInOrden.size()) {
            K claveActual = listaInOrden.get(posicion);
            if (claveAEliminar.compareTo(claveActual) < 0) {
                clavePredecesora = claveActual;
                posicion = listaInOrden.size();
            }
            posicion++;
        }
        return clavePredecesora;
    }

    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K,V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            boolean huboCambioDeNodoActual = false;
            for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias() && 
                    !huboCambioDeNodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveABuscar.compareTo(claveActual) == 0) {
                    return nodoActual.getValor(i);
                }
                if (claveABuscar.compareTo(claveActual) < 0) {
                    nodoActual = nodoActual.getHijo(i);
                    huboCambioDeNodoActual = true;
                }
            } //fin del for           
            if (!huboCambioDeNodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias());
            }
        }
        return (V) NodoMVias.datoVacio();
    }

    @Override
    public boolean contiene(K claveABuscar) {
        return this.buscar(claveABuscar) != NodoMVias.datoVacio();
    }

    @Override
    public int size() {
        return size(this.raiz);
    }

    private int size(NodoMVias<K,V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++){
            cantidad = cantidad + size(nodoActual.getHijo(i));
        }
        cantidad = cantidad + size(nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias()));
        return cantidad + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    
    protected int altura(NodoMVias<K,V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        
        int alturaMayor = 0;
        for (int i = 0; i < this.orden; i++) {  
            int alturaActual = altura(nodoActual.getHijo(i));
            if (alturaActual > alturaMayor) {
                alturaMayor = alturaActual;
            }
        }    
        /*
        //Otra Opcion
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {  
            int alturaActual = altura(nodoActual.getHijo(i));
            if (alturaActual > alturaMayor) {
                alturaMayor = alturaActual;
            }
        }
        int alturaActual = altura(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
        if (alturaActual > alturaMayor) {
                alturaMayor = alturaActual;
            }       
        */
        return alturaMayor + 1;
    }

    @Override
    public int nivel() {
        return this.nivel(this.raiz);
    }
    
    private int nivel(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        int nivel = 0;
        for (int i = 0; i < this.orden; i++) {
            int nivelHijo = nivel(nodoActual.getHijo(i));
            if (nivelHijo > nivel) {
                nivel = nivelHijo;
            }
        }
        return nivel + 1;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }
        
        Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K,V> nodoActual = colaDeNodos.poll();
            for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
                recorrido.add(nodoActual.getClave(i));
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClaveNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias()));
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i),recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias()), recorrido);
    }
    
    @Override
    public List<K> recorridoEnInOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
        recorridoEnInOrden(nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;       
    }
    
    private void recorridoEnPostOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i+1),recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }

    //Metodo que retorne la cantidad de Hijos vacios en el arbolMVias
    public int cantidadHijosVaciosRec() {
        return cantidadHijosVaciosRec(this.raiz); 
    }
    
    private int cantidadHijosVaciosRec(NodoMVias<K,V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nodoActual.esHoja()) {
            return orden;
        }
        int contador = 0;
        for (int i = 0; i < orden; i ++) {
            if (nodoActual.esHijoVacio(i)) {
                contador++;
            } else {
                contador = contador + cantidadHijosVaciosRec(nodoActual.getHijo(i));
            }           
        }       
        return contador;
    }
    
    @Override
    public String toString() { 
        String arbol = ""; 
        if (this.esArbolVacio()) {
            return arbol;
        }       
        Queue<NodoMVias<K,V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K,V> nodoActual = colaDeNodos.poll();           
            for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
                System.out.print("| "+nodoActual.getClave(i)+" |");
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));                   
                }
            }
            if (!nodoActual.esHijoVacio(nodoActual.cantidadDeClaveNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClaveNoVacias())); 
                System.out.println();
            }                                           
        }
        System.out.println();
        return arbol;
    }
    
    /** 17. Implemente un método que retorne la mayor llave en un 
     * árbol m vias de búsqueda.
     * */
    @Override
    public K mayorLLave() {
        return mayorLLave(this.raiz);
    }
    
    private K mayorLLave(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return (K)NodoMVias.datoVacio();
        }
        K claveMayor = nodoActual.getClave(nodoActual.cantidadDeClaveNoVacias()-1);
        for (int i = 0; i <= nodoActual.cantidadDeClaveNoVacias(); i++) {
            if (!nodoActual.esHijoVacio(i)) {
                K claveActual = mayorLLave(nodoActual.getHijo(i));
                if (claveMayor.compareTo(claveActual) < 0) {
                    claveMayor = claveActual;
                }
            }                              
        }
        return (K)claveMayor;
    }
   
    /** 18. Implemente un método que retorne verdadero si solo hay
     * hojas en el último nivel de un árbol m-vias de búsqueda. 
     * Falso en caso contrario.
     * */
    @Override
    public boolean hayHojasEnUltimoNivel() {
        return !this.esArbolVacio();
    }
    
    /** 19. Implemente un método que retorne verdadero si un 
     * árbol m vias tiene solo hojas o nodos con todos sus hijos 
     * distinto de vacío. Falso en caso contrario.
     * */
    @Override
    public boolean tieneHojasOTodosHijos() {
        return tieneHojasOTodosHijos(this.raiz);
    }
    
    private boolean tieneHojasOTodosHijos(NodoMVias<K,V> nodoActual) {
        boolean resultado = false;
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return resultado;
        }
        if (nodoActual.esHoja()) {
            resultado = true;
        }      
        for (int i = 0; i < this.orden; i++) {
            if (nodoActual.cantidadDeHijosNoVacios() == this.orden) {
                resultado = this.tieneHojasOTodosHijos(nodoActual.getHijo(i));
            }                
        }     
        return resultado;
    }
    
    /** 20. Para un árbol m vías implementar un método que reciba otro 
     * árbol de parámetro y que retorne verdadero si los arboles son 
     * similares. Falso en caso contrario
     * */
    @Override
    public boolean sonIguales(ArbolMViasBusqueda<K,V> arbolCopia) {
        return sonIguales(this.raiz,arbolCopia.raiz);
    }
    
    private boolean sonIguales(NodoMVias<K,V> nodoActual, NodoMVias<K,V> nodoCopia) {
        if (NodoMVias.esNodoVacio(nodoActual) && NodoMVias.esNodoVacio(nodoCopia)) {
            return true;
        }
        boolean resultado = false;
        if (nodoActual.esHoja() && nodoCopia.esHoja()) {
            if (nodoActual.cantidadDeClaveNoVacias() == nodoCopia.cantidadDeClaveNoVacias()) {
                for (int i = 0; i < nodoActual.cantidadDeClaveNoVacias(); i++) {
                    K claveActual = nodoActual.getClave(i);
                    K claveCopia = nodoCopia.getClave(i);
                    if (claveCopia.compareTo(claveActual) == 0) {
                        resultado = true;
                    }
                }
            }
        }
        for (int i = 0; i < this.orden; i++) {
            if (!nodoActual.esHijoVacio(i) && !nodoCopia.esHijoVacio(i)) {
                resultado = sonIguales(nodoActual.getHijo(i),nodoCopia.getHijo(i));
            }
        }
        return resultado;
    } 

    @Override
    public int cantidadDeNodosConAmbosHijos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadDeNodosConAmbosHijosRec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadNodosConAmbosHijosEnN(int nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadNodosConAmbosHijosEnNRec(int nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadNodosConUnHijoEnN(int nivel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int nroNodosConInOrden() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K menorLlave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean esArboLleno() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
