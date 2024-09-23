package Algoritmo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase que implementa el algoritmo de Edmonds-Karp para encontrar el flujo máximo
 * en una red de flujo. Este algoritmo utiliza la búsqueda en anchura (BFS) para encontrar
 * los caminos de aumento en el grafo residual.
 */
public class EdmondsKarp {
    private final int numVertices; // Número de vértices en el grafo
    private final int[][] capacidad; // Matriz de capacidad de la red de flujo
    private int asignaciones = 0; // Contador de asignaciones realizadas durante el algoritmo
    private int comparaciones = 0; // Contador de comparaciones realizadas durante el algoritmo

    /**
     * Constructor de la clase EdmondsKarp.
     * Inicializa el número de vértices y la matriz de capacidades del grafo.
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
        int[][] flujoResidual = new int[numVertices][]; // Matriz de flujo residual
        asignaciones++;

        for (int u = 0; u < numVertices; u++) { // Inicializa la matriz de flujo residual
            comparaciones++;
            flujoResidual[u] = capacidad[u].clone(); // Clona cada fila de la matriz de capacidades
            asignaciones++;
        }
        comparaciones++;

        int[] padres = new int[numVertices]; // Array para almacenar el camino encontrado por BFS
        asignaciones++;

        int flujoMaximo = 0; // Inicializa el flujo máximo a 0
        asignaciones++;

        // Realiza búsqueda en anchura (BFS) hasta que no haya más caminos de aumento
        while (bfs(flujoResidual, fuente, sumidero, padres)) {
            comparaciones++;
            int flujoCamino = Integer.MAX_VALUE; // Inicializa el flujo del camino a un valor muy grande
            asignaciones++;

            // Encuentra la capacidad mínima en el camino encontrado
            for (int v = sumidero; v != fuente; v = padres[v]) {
                comparaciones++;
                int u = padres[v];
                flujoCamino = Math.min(flujoCamino, flujoResidual[u][v]);
                asignaciones += 2;
            }
            comparaciones++;

            // Actualiza el flujo residual en el camino encontrado
            for (int v = sumidero; v != fuente; v = padres[v]) {
                comparaciones++;
                int u = padres[v];
                flujoResidual[u][v] -= flujoCamino;
                flujoResidual[v][u] += flujoCamino;
                asignaciones += 3;
            }
            comparaciones++;

            flujoMaximo += flujoCamino;
            asignaciones++;
        }
        comparaciones++;

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
        boolean[] visitado = new boolean[numVertices];  // Array para marcar los nodos visitados
        asignaciones++;
        Queue<Integer> cola = new LinkedList<>(); // Cola para almacenar nodos a explorar
        asignaciones++;

        cola.add(fuente);
        asignaciones++;
        visitado[fuente] = true;
        asignaciones++;
        padres[fuente] = -1; // Inicializar padre del nodo fuente
        asignaciones++;

        while (!cola.isEmpty()) {
            comparaciones++;
            int u = cola.poll(); // Obtener y eliminar el nodo del frente de la cola
            asignaciones++;

            // Recorre todos los vértices adyacentes al nodo u
            for (int v = 0; v < numVertices; v++) {
                comparaciones++;
                // Si el nodo no ha sido visitado y hay capacidad residual
                if (!visitado[v] && flujoResidual[u][v] > 0) {
                    comparaciones += 2;
                    cola.add(v); // Agregar el nodo a la cola
                    asignaciones++;
                    padres[v] = u;  // Establecer el padre del nodo
                    asignaciones++;
                    visitado[v] = true;  // Marcar el nodo como visitado
                    asignaciones++;
                    if (v == sumidero) { // Si llegamos al nodo sumidero
                        comparaciones++;
                        return true; // Camino encontrado
                    }
                }
            }
        }
        return false; // No se encontró un camino
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
