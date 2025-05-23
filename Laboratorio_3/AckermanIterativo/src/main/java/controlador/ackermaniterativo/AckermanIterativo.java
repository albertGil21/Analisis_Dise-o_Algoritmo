/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controlador.ackermaniterativo;

/**
 *
 * @author ALGS
 */

import java.util.Stack;

public class AckermanIterativo {

    public static int ackermann(int m, int n, Counter counter) {
        Stack<Integer> stack = new Stack<>();

        stack.push(m);
        while (!stack.isEmpty()) {
            counter.increment(); // Contamos cada paso de la simulación

            m = stack.pop();

            if (m == 0) {
                n = n + 1;
            } else if (n == 0) {
                stack.push(m - 1);
                n = 1;
            } else {
                stack.push(m - 1);
                stack.push(m);
                n = n - 1;
            }
        }

        return n;
    }

    public static void main(String[] args) {
        int m = 3;
        int n = 2;

        Counter counter = new Counter();

        long startTime = System.currentTimeMillis();
        int result = ackermann(m, n, counter);
        long endTime = System.currentTimeMillis();

        System.out.println("Ackermann(" + m + ", " + n + ") = " + result);
        System.out.println("T(" + m + ", " + n + ") = " + counter.getCount() + " operaciones");
        System.out.println("Tiempo real: " + (endTime - startTime) + " ms");
    }

    // Clase auxiliar para contar operaciones
    static class Counter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}
