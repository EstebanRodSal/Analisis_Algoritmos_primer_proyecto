package Algoritmo;

import java.util.LinkedList;

/**
 * Clase que implementa el algoritmo de Ford-Fulkerson para calcular el flujo máximo en un grafo.
 */
public class FordFulkerson {
    private Grafo grafo;
    private int numVertices;
    private int[][] grafoResidual;
    private int asignaciones;
    private int comparaciones;

    /**
     * Constructor de la clase FordFulkerson.
     *
     * @param grafo El objeto Grafo sobre el cual se aplicará el algoritmo.
     */
    public FordFulkerson(Grafo grafo) {
        this.grafo = grafo;
        this.numVertices = grafo.getCapacidad().length;
        this.grafoResidual = new int[numVertices][numVertices];
        int[][] capacidad = grafo.getCapacidad();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                grafoResidual[i][j] = capacidad[i][j];
            }
        }
    }

    /**
     * Método para realizar una búsqueda en anchura (BFS) en el grafo residual.
     *
     * @param fuente     El nodo fuente del grafo.
     * @param sumidero   El nodo sumidero del grafo.
     * @param padres     Array que almacena el camino encontrado durante la BFS.
     * @return Verdadero si existe un camino desde la fuente al sumidero, falso de lo contrario.
     */
    private boolean bfs(int fuente, int sumidero, int[] padres) {
        boolean[] visitado = new boolean[numVertices];
        LinkedList<Integer> cola = new LinkedList<>();
        cola.add(fuente);
        visitado[fuente] = true;
        padres[fuente] = -1;

        while (!cola.isEmpty()) {
            int u = cola.poll();

            for (int v = 0; v < numVertices; v++) {
                comparaciones++;
                if (!visitado[v] && grafoResidual[u][v] > 0) {
                    asignaciones++;
                    if (v == sumidero) {
                        padres[v] = u;
                        return true;
                    }
                    cola.add(v);
                    padres[v] = u;
                    visitado[v] = true;
                }
            }
        }
        return false;
    }

    /**
     * Método para calcular el flujo máximo utilizando el algoritmo de Ford-Fulkerson.
     *
     * @param fuente   El nodo fuente.
     * @param sumidero El nodo sumidero.
     * @return El flujo máximo calculado.
     */
    public int flujoMaximo(int fuente, int sumidero) {
        int[] padres = new int[numVertices];
        int flujoMaximo = 0;

        while (bfs(fuente, sumidero, padres)) {
            int flujoCamino = Integer.MAX_VALUE;
            for (int v = sumidero; v != fuente; v = padres[v]) {
                int u = padres[v];
                flujoCamino = Math.min(flujoCamino, grafoResidual[u][v]);
            }

            for (int v = sumidero; v != fuente; v = padres[v]) {
                int u = padres[v];
                grafoResidual[u][v] -= flujoCamino;
                grafoResidual[v][u] += flujoCamino;
            }

            flujoMaximo += flujoCamino;
        }

        return flujoMaximo;
    }

    /**
     * Método para obtener el número de asignaciones realizadas durante la ejecución.
     *
     * @return El número de asignaciones.
     */
    public int getAsignaciones() {
        return asignaciones;
    }

    /**
     * Método para obtener el número de comparaciones realizadas durante la ejecución.
     *
     * @return El número de comparaciones.
     */
    public int getComparaciones() {
        return comparaciones;
    }
}
