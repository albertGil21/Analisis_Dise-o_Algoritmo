/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controlador.maxsubarray;

/**
 *
 * @author ALGS
 */
public class MaxSubArray {
    public static void main(String[] args) {
        int[] arreglo = {-2, 11, -4, 13, -5, 9, -3, 2, -8, 4};

        SubsecuenciaMaxima.Resultado resultado = SubsecuenciaMaxima.buscar(arreglo);

        System.out.println("La suma máxima encontrada es: " + resultado.getSuma());
        System.out.println("Desde la posición " + (resultado.getInicio() + 1) + " hasta la posición " + (resultado.getFin() + 1));
    }
}
