/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed2202101;

/**
 *
 * @author alejandra
 */
public class NodoBinario <K,V> {
    private K clave;
    private V valor;
    private NodoBinario <K,V> hijoIzquierdo;
    private NodoBinario <K,V> hijoDerecho;

    public NodoBinario() {
    }

    public NodoBinario(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public NodoBinario<K, V> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<K, V> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoBinario<K, V> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<K, V> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }
    
    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == null;
    }
    
    public static NodoBinario nodoVacio() {
        return null;
    }
    
    public boolean esVacioHijoIzquierdo() {
        return NodoBinario.esNodoVacio(this.hijoIzquierdo);
    }
    
    public boolean esVacioHijoDerecho() {
        return NodoBinario.esNodoVacio(this.hijoDerecho);
    }
    
    public boolean esHoja() {
        return this.esVacioHijoIzquierdo() &&
                this.esVacioHijoDerecho();
    }
    
    public int cantidadHijos() {
        int contador = 0;
        if (!NodoBinario.esNodoVacio(this.hijoIzquierdo) && NodoBinario.esNodoVacio(this.hijoDerecho)) {
            return 1;
        }
        if (!NodoBinario.esNodoVacio(this.hijoDerecho) && NodoBinario.esNodoVacio(this.hijoIzquierdo)) {
            return 1;
        }
        if (!NodoBinario.esNodoVacio(this.hijoIzquierdo) && !NodoBinario.esNodoVacio(this.hijoDerecho)) {
            return 2;
        }
        return contador;
    }
    
    public boolean esHijoIzquierdo(NodoBinario nodo) {
        return nodo == this.hijoIzquierdo;
    }
    
    public boolean esHijoDerecho(NodoBinario nodo) {
        return nodo == this.hijoDerecho;
    }
    
    @Override
    public String toString() {
        return "" + clave; 
    }
}
