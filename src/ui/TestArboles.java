/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import ed2202101.AVL;
import ed2202101.ArbolBinarioBusqueda;
import ed2202101.ArbolMViasBusqueda;
import ed2202101.IArbolBusqueda;
import ed2202101.excepciones.ExcepcionClaveNoExiste;
import ed2202101.excepciones.ExcepcionOrdenInvalido;
import java.util.Scanner;

/**
 *
 * @author alejandra
 */
public class TestArboles {

    public static void main(String argumentos[]) throws ExcepcionOrdenInvalido, ExcepcionClaveNoExiste {
        IArbolBusqueda<Integer, String> arbolBusqueda;
        Scanner entrada = new Scanner(System.in);
        System.out.print("Elija un tipo de árbol(ABB, AVL, AMV, AB): ");
        String tipoArbol = entrada.next();
        tipoArbol = tipoArbol.toUpperCase();
        
        switch (tipoArbol) {
            case "ABB":
                arbolBusqueda = new ArbolBinarioBusqueda<>();
                break;
            case "AVL":
                arbolBusqueda = new AVL<>();
                break;
            case "AMV":
                arbolBusqueda = new ArbolMViasBusqueda<>(4);
                break;
            default:
                return;
        } 
        
        arbolBusqueda.insertar(50, "Franco");
        arbolBusqueda.insertar(60, "Abner");
        arbolBusqueda.insertar(55, "ASIR");            
        arbolBusqueda.insertar(70, "Yuliana");
        arbolBusqueda.insertar(90, "Jorge");
        arbolBusqueda.insertar(40, "Marco");
        arbolBusqueda.insertar(45, "jUAN");
        arbolBusqueda.insertar(20, "Ruth");
        arbolBusqueda.insertar(65, "Ricky");
        arbolBusqueda.insertar(68, "Harold");
        arbolBusqueda.insertar(110, "Andrea");              
        System.out.println(arbolBusqueda);
        
        //arbolBusqueda.eliminar(45);
        //System.out.println(arbolBusqueda);
        /*
        System.out.println("Cantidad Hijos Vacios: "+arbolBusqueda.cantidadHijosVacios());
        System.out.println("Cantidad Hijos Vacios Recursivo: "+arbolBusqueda.cantidadHijosVaciosRec());
        System.out.println("Existe nodo vacio antes del nivel N: "+arbolBusqueda.hijoVacioN(4));
        System.out.println("Existe nodo vacio antes del nivel N Recursivo: "+arbolBusqueda.hijoVacioNRec(4));
        */
        
        System.out.println("InOrden: "+arbolBusqueda.recorridoEnInOrden());
        System.out.println("PreOrden: "+arbolBusqueda.recorridoEnPreOrden());
        System.out.println("PostOrden: "+arbolBusqueda.recorridoEnPostOrden());
        
        System.out.println("--------------PRACTICO 1---------------------");
        System.out.println("3. Implemente un método iterativo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario: "
                + arbolBusqueda.cantidadDeNodosConAmbosHijos());
        
        System.out.println("4. Implemente un método recursivo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario: "
                + arbolBusqueda.cantidadDeNodosConAmbosHijosRec());
        
        System.out.println("5. Implemente un método iterativo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario, \n "
                + "pero solo en el nivel N: "
                + arbolBusqueda.cantidadNodosConAmbosHijosEnN(2));
        
        System.out.println("6. Implemente un método recursivo que retorne la cantidad de nodos \n "
                + "que tienen ambos hijos distintos de vacío en un árbol binario, \n "
                + "pero solo en el nivel N: "
                + arbolBusqueda.cantidadNodosConAmbosHijosEnNRec(2));
        
        System.out.println("7. Implemente un método iterativo que retorne la cantidad nodos \n "
                + "que tienen un solo hijo diferente de vacío en un árbol binario, \n "
                + "pero solo antes del nivel N: "
                + arbolBusqueda.cantidadNodosConUnHijoEnN(3));

        System.out.println("12. Implemente un método iterativo con la lógica de un recorrido \n "
                + "en inOrden que retorne el número de nodos que tiene un árbol binario: "
                + arbolBusqueda.nroNodosConInOrden());
        
        System.out.println("16. Implemente un método que retorne la menor llave en un \n "
                + "árbol binario de búsqueda: "
                + arbolBusqueda.menorLlave());
        
        System.out.println("21. Implemente un método que retorne verdadero si un árbol \n "
                + "binario esta lleno. Falso en caso contrario: "
                + arbolBusqueda.esArboLleno());
        
        System.out.println("17. Implemente un método que retorne la mayor llave en un \n "
                + "árbol m vias de búsqueda: "
                + arbolBusqueda.mayorLLave());
        
        System.out.println("18. Implemente un método que retorne verdadero si solo hay\n"
                + "hojas en el último nivel de un árbol m-vias de búsqueda. \n"
                + "Falso en caso contrario: "
                + arbolBusqueda.hayHojasEnUltimoNivel());
        
        System.out.println("19. Implemente un método que retorne verdadero si un \n "
                + "árbol m vias tiene solo hojas o nodos con todos sus hijos \n "
                + "distinto de vacío. Falso en caso contrario: "
                + arbolBusqueda.tieneHojasOTodosHijos());
        
        ArbolMViasBusqueda<Integer,String> arbolCopia = new ArbolMViasBusqueda<>(4);
        arbolCopia.insertar(50, "Franco");
        arbolCopia.insertar(55, "Franco");
        arbolCopia.insertar(60, "Abner");        
        arbolCopia.insertar(70, "Yuliana");
        arbolCopia.insertar(90, "Jorge");
        arbolCopia.insertar(40, "Marco");
        arbolCopia.insertar(45, "jUAN");
        arbolCopia.insertar(20, "Ruth");
        arbolCopia.insertar(65, "Ricky");   
        arbolCopia.insertar(68, "Ricky"); 
        arbolCopia.insertar(110, "Ricky");
        System.out.println(arbolCopia);
        System.out.println("20. Para un árbol m vías implementar un método que reciba otro \n"
                + "árbol de parámetro y que retorne verdadero si los arboles son \n"
                + "similares. Falso en caso contrario: "
                + arbolBusqueda.sonIguales(arbolCopia));
              
        System.out.println("Cantidad de Nodo No Vacios Desde N nivel: " + arbolCopia.nroNodosNoVaciosDesdeN(0));
    }   
}
