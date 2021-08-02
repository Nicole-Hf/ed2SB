/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2202101;

import ed2202101.excepciones.ExcepcionClaveNoExiste;

/**
 *
 * @author alejandra
 * @param <K>
 * @param <V>
 */
public class AVL<K extends Comparable<K>, V> extends ArbolBinarioBusqueda<K,V> {
    private static final byte DIFERENCIA_PERMITIDA = 1;
    
    @Override
    public void insertar(K claveAInsertar, V valorAInsertar) {
        if (valorAInsertar == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }
        
        this.raiz = insertar(this.raiz, claveAInsertar, valorAInsertar);
    }
    
    private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar,valorAInsertar);
            return nuevoNodo;
        }
        
        K claveActual = nodoActual.getClave();
        
        if (claveAInsertar.compareTo(claveActual) < 0) {
            NodoBinario<K,V> nuevoSupuestoHijo = insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAInsertar);
            nodoActual.setHijoIzquierdo(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveActual) > 0) {
            NodoBinario<K,V> nuevoSupuestoHijo = insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAInsertar);
            nodoActual.setHijoDerecho(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        
        //encontré en el arbol la clave que queria insertar, actualizo el valor
        nodoActual.setValor(valorAInsertar);
        return nodoActual;
    }
    
    @Override
    public V eliminar(K claveAEliminar) throws ExcepcionClaveNoExiste {
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar == null) {
            throw new ExcepcionClaveNoExiste();
        }
        this.raiz = eliminar(this.raiz,claveAEliminar);
        
        return valorAEliminar;
    }
    
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual, K claveAEliminar) {
        K claveActual = nodoActual.getClave();
        
        if (claveAEliminar.compareTo(claveActual) < 0) {
            NodoBinario<K,V> supuestoNuevoHijoIzq = eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return balancear(nodoActual);
        }
        
        if (claveAEliminar.compareTo(claveActual) > 0) {
            NodoBinario<K,V> supuestoNuevoHijoDer = eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return balancear(nodoActual);
        }
        
        //si llego hasta aqui, ya encontré el nodo con la clave a eliminar
        //revisar los tres casos
        //CASO 1 : ES HOJA
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        
        //CASO 2
        //Caso 2.1. Tiene hijo izquierdo
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return balancear(nodoActual.getHijoIzquierdo());
        }
        //Caso 2.2. Tiene hijo derecho
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()) {
            return balancear(nodoActual.getHijoDerecho());
        }
        
        //CASO 3: Tiene ambos hijos
        NodoBinario<K,V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoNuevoHijo = eliminar(nodoActual.getHijoDerecho(),nodoDelSucesor.getClave());
        
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        
        return balancear(nodoActual);  
    }
    
    private NodoBinario<K,V> balancear(NodoBinario<K,V> nodoActual) {
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        int diferenciaDeAltura = alturaPorIzquierda - alturaPorDerecha;
        
        if (diferenciaDeAltura > DIFERENCIA_PERMITIDA) {
            //ROTACION A DERECHA
            NodoBinario<K,V> hijoIzquierdoDelActual = nodoActual.getHijoIzquierdo();
            alturaPorIzquierda = altura(hijoIzquierdoDelActual.getHijoIzquierdo());
            alturaPorDerecha = altura(hijoIzquierdoDelActual.getHijoDerecho());
            if (alturaPorDerecha > alturaPorIzquierda) {
                return rotacionDobleADerecha(nodoActual);
            }
            return rotacionSimpleADerecha(nodoActual);
            
        } else if (diferenciaDeAltura < -DIFERENCIA_PERMITIDA) {
            //ROTACION A IZQUIERDA
            NodoBinario<K,V> hijoDerechoDelActual = nodoActual.getHijoDerecho();
            alturaPorIzquierda = altura(hijoDerechoDelActual.getHijoIzquierdo());
            alturaPorDerecha = altura(hijoDerechoDelActual.getHijoDerecho());
            if (alturaPorIzquierda > alturaPorDerecha) {
                return rotacionDobleAIzquierda(nodoActual);
            }
            return rotacionSimpleAIzquierda(nodoActual);
        }
        
        //NO HAY QUE HACER NADA      
        return nodoActual;
    }
    
    private NodoBinario<K,V> rotacionSimpleADerecha(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleADerecha(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoDePrimeraRotacion = rotacionSimpleAIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoDePrimeraRotacion);
        return rotacionSimpleADerecha(nodoActual);
    }
    
    private NodoBinario<K,V> rotacionSimpleAIzquierda(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    
    private NodoBinario<K,V> rotacionDobleAIzquierda(NodoBinario<K,V> nodoActual) {
        NodoBinario<K,V> nodoDePrimerRotacion = rotacionSimpleADerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoDePrimerRotacion);
        return rotacionSimpleADerecha(nodoActual);
    }
    
}
