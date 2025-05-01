/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.maxsubarray;

/**
 *
 * @author ALGS
 */


/**
 * Clase que contiene el algoritmo para encontrar la subsecuencia contigua de suma máxima.
 */
public class SubsecuenciaMaxima {

    public static Resultado buscar(int[] datos) {
        int sumaActual = 0;
        int sumaMaxima = 0;
        int inicioTemp = 0;
        int inicioFinal = 0;
        int finFinal = 0;

        for (int i = 0; i < datos.length; i++) {
            sumaActual += datos[i];

            if (sumaActual < 0) {
                sumaActual = 0;
                inicioTemp = i + 1;
            }

            if (sumaActual > sumaMaxima) {
                sumaMaxima = sumaActual;
                inicioFinal = inicioTemp;
                finFinal = i;
            }
        }

        return new Resultado(sumaMaxima, inicioFinal, finFinal);
    }

    /**
     * Clase interna que representa el resultado de la búsqueda.
     */
    public static class Resultado {
        private final int suma;
        private final int inicio;
        private final int fin;

        public Resultado(int suma, int inicio, int fin) {
            this.suma = suma;
            this.inicio = inicio;
            this.fin = fin;
        }

        public int getSuma() {
            return suma;
        }

        public int getInicio() {
            return inicio;
        }

        public int getFin() {
            return fin;
        }
    }
}
