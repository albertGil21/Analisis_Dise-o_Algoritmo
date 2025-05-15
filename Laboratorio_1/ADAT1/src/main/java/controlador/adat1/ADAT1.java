/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controlador.adat1;

/**
 *
 * @author ALGS
 */
// Main.java
public class ADAT1 {
    public static void main(String[] args) {
        char[][] tablero = {
            {'E','S','T','O'},
            {'S','T','T','M'},
            {'E','A','S','A'},
            {'P','R','N','E'}
        };
        String[] palabras = {"esto", "ese", "pato", "este"};

        SopaDeLetras solver = new SopaDeLetras(tablero);
        for (String w : palabras) {
            // Pasamos a mayúsculas para comparar con el grid
            Posicion[] res = solver.buscar(w.toUpperCase());
            if (res != null) {
                // Sumamos +1 para pasar de índice 0‑based a 1‑based
                System.out.printf(
                  "Palabra '%s' = inicio (%d,%d) - fin (%d,%d)%n",
                  w,
                  res[0].fila + 1, res[0].col + 1,
                  res[1].fila + 1, res[1].col + 1
                );
            } else {
                System.out.printf("Palabra '%s' NO encontrada%n", w);
            }
        }
    }
}
