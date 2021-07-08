/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import ed2202101.excepciones.ExcepcionAristaNoExiste;
import ed2202101.excepciones.ExcepcionAristaYaExiste;
import ed2202101.excepciones.ExcepcionNroVerticesInvalido;
import grafoNoPesados.DiGrafo;
import grafoNoPesados.Grafo;

/**
 *
 * @author Nicole
 */
public class TestGrafos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ExcepcionAristaYaExiste, ExcepcionNroVerticesInvalido, ExcepcionAristaNoExiste {
        //--------GRAFOS-------------------
        Grafo grafo = new Grafo(7);
        grafo.insertarVertice();
        grafo.insertarArista(0, 0);
        grafo.insertarArista(0, 1);      
        grafo.insertarArista(2, 1);
        grafo.insertarArista(1, 3);
        grafo.insertarArista(2, 0);        
        grafo.insertarArista(2, 4);
        grafo.insertarArista(2, 7);       
        grafo.insertarArista(3, 4);       
        grafo.insertarArista(4, 5);       
        grafo.insertarArista(5, 6);     
        System.out.println(grafo);
        //grafo.eliminarArista(4, 2);
        //grafo.eliminarVertice(4);
        //System.out.println(grafo);
        //System.out.println("Es grafo conexo?: " + grafo.esConexo());
        System.out.println("Cantidad de islas: " + grafo.cantidadDeIslas());
        System.out.println("Hay ciclo en el grafo?: " + grafo.hayCiclo());
        
        //----------DIGRAFOS---------------
        DiGrafo dgrafo = new DiGrafo(7);
        dgrafo.insertarVertice();
        dgrafo.insertarArista(0, 0);
        dgrafo.insertarArista(0, 1);      
        dgrafo.insertarArista(2, 1);
        dgrafo.insertarArista(1, 3);
        dgrafo.insertarArista(2, 0);        
        dgrafo.insertarArista(2, 4);
        dgrafo.insertarArista(2, 7);       
        dgrafo.insertarArista(3, 4);       
        dgrafo.insertarArista(4, 5);       
        dgrafo.insertarArista(5, 6);     
        System.out.println(dgrafo);
        System.out.println("Cantidad de Aristas DiGrafo: " + dgrafo.cantidadDeAristas());
    }
    
}
