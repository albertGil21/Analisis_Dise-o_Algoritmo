/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.adat1;

/**
 *
 * @author ALGS
 */
// SopaDeLetras.java
public class SopaDeLetras {
    private final char[][] grid;
    // Direcciones: N‑W, N, N‑E, W, E, S‑W, S, S‑E
    private static final int[] dF = {-1, -1, -1,  0, 0, +1, +1, +1};
    private static final int[] dC = {-1,  0, +1, -1, +1, -1,  0, +1};

    public SopaDeLetras(char[][] grid) {
        this.grid = grid;
    }

    /**
     * Busca 'palabra' en el tablero. 
     * @param palabra en mayúsculas
     * @return un array de dos Posicion {inicio, fin}, o null si no se encontró.
     */
    public Posicion[] buscar(String palabra) {
        int n = grid.length, m = grid[0].length;
        int L = palabra.length();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] != palabra.charAt(0)) continue;
                // Prueba las ocho direcciones
                for (int d = 0; d < 8; d++) {
                    int k;
                    for (k = 0; k < L; k++) {
                        int ni = i + dF[d] * k;
                        int nj = j + dC[d] * k;
                        if (ni < 0 || nj < 0 || ni >= n || nj >= m
                                || grid[ni][nj] != palabra.charAt(k)) {
                            break;
                        }
                    }
                    if (k == L) {
                        // encontré la palabra completa
                        Posicion inicio = new Posicion(i, j);
                        Posicion fin = new Posicion(i + dF[d] * (L - 1),
                                                    j + dC[d] * (L - 1));
                        return new Posicion[]{inicio, fin};
                    }
                }
            }
        }
        return null;
    }
}
