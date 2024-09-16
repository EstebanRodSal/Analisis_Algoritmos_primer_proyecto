package Algoritmo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase que implementa el algoritmo de Edmonds-Karp para encontrar el flujo máximo
 * en una red de flujo.
 * Este algoritmo utiliza la búsqueda en anchura (BFS) para encontrar el camino de aumento.
 */
public class EdmondsKarp {
    private int numVertices;
    private int[][] capacidad;
    private int asignaciones = 0;
    private int comparaciones = 0;

    /**
     * Constructor de la clase EdmondsKarp.
     * Inicializa la capacidad del grafo y el número de vértices.
     *
     * @param grafo Grafo con la matriz de capacidades.
     */
    public EdmondsKarp(Grafo grafo) {
        this.numVertices = grafo.getCapacidad().length;
        this.capacidad = grafo.getCapacidad();
    }

    /**
     * Calcula el flujo máximo entre dos nodos en la red de flujo.
     *
     * @param fuente   El nodo fuente.
     * @param sumidero El nodo sumidero.
     * @return El flujo máximo posible desde la fuente hasta el sumidero.
     */
    public int flujoMaximo(int fuente, int sumidero) {
        int[][] flujoResidual = new int[numVertices][numVertices];
        for (int u = 0; u < numVertices; u++) {
            for (int v = 0; v < numVertices; v++) {
                flujoResidual[u][v] = capacidad[u][v];
                asignaciones++;
            }
        }

        int[] padres = new int[numVertices];
        int flujoMaximo = 0;

        while (bfs(flujoResidual, fuente, sumidero, padres)) {
            int flujoCamino = Integer.MAX_VALUE;

            for (int v = sumidero; v != fuente; v = padres[v]) {
                int u = padres[v];
                flujoCamino = Math.min(flujoCamino, flujoResidual[u][v]);
                comparaciones++;
            }

            for (int v = sumidero; v != fuente; v = padres[v]) {
                int u = padres[v];
                flujoResidual[u][v] -= flujoCamino;
                flujoResidual[v][u] += flujoCamino;
                asignaciones += 2;
            }

            flujoMaximo += flujoCamino;
            asignaciones++;
        }

        return flujoMaximo;
    }

    /**
     * Realiza una búsqueda en anchura (BFS) para encontrar un camino de aumento
     * en la red de flujo.
     *
     * @param flujoResidual La matriz de flujo residual.
     * @param fuente        El nodo fuente.
     * @param sumidero      El nodo sumidero.
     * @param padres        Array para almacenar el camino encontrado.
     * @return true si se encontró un camino de aumento, false en caso contrario.
     */
    private boolean bfs(int[][] flujoResidual, int fuente, int sumidero, int[] padres) {
        boolean[] visitado = new boolean[numVertices];
        Queue<Integer> cola = new LinkedList<>();
        cola.add(fuente);
        visitado[fuente] = true;
        padres[fuente] = -1;

        while (!cola.isEmpty()) {
            int u = cola.poll();

            for (int v = 0; v < numVertices; v++) {
                comparaciones++;
                if (!visitado[v] && flujoResidual[u][v] > 0) {
                    cola.add(v);
                    padres[v] = u;
                    visitado[v] = true;
                    asignaciones += 3;
                    if (v == sumidero) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Devuelve el número de asignaciones realizadas durante la ejecución del algoritmo.
     *
     * @return El número de asignaciones.
     */
    public int getAsignaciones() {
        return asignaciones;
    }

    /**
     * Devuelve el número de comparaciones realizadas durante la ejecución del algoritmo.
     *
     * @return El número de comparaciones.
     */
    public int getComparaciones() {
        return comparaciones;
    }
}
