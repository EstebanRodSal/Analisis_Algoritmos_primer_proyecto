package Algoritmo;

import java.util.ArrayDeque;
import java.util.Queue;

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


        // System.arraycopy para mejorar la eficiencia del algorimo
        for (int i = 0; i < numVertices; i++) {
            System.arraycopy(capacidad[i], 0, grafoResidual[i], 0, numVertices);

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
        asignaciones++;

        Queue<Integer> cola = new ArrayDeque<>();  // Usar ArrayDeque para mejorar el rendimiento en operaciones de cola
        asignaciones++;

        cola.add(fuente);
        asignaciones++;

        visitado[fuente] = true;
        asignaciones++;

        padres[fuente] = -1;
        asignaciones++;

        while (!cola.isEmpty()) {
            comparaciones++;

            int u = cola.poll();  // Obtener el primer elemento de la cola
            asignaciones++;

            for (int v = 0; v < numVertices; v++) {
                comparaciones++;

                if (!visitado[v] && grafoResidual[u][v] > 0) {
                    comparaciones += 2;
                    padres[v] = u;
                    asignaciones++;

                    if (v == sumidero) {
                        comparaciones++;
                        return true;
                    }

                    cola.add(v);
                    asignaciones++;

                    visitado[v] = true;
                    asignaciones++;
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
        // Verificar que los nodos fuente y sumidero sean válidos
        if (fuente < 0 || sumidero < 0 || fuente >= numVertices || sumidero >= numVertices) {
            comparaciones += 2;
            throw new IllegalArgumentException("Fuente o sumidero fuera de rango.");
        }

        if (fuente == sumidero) {
            comparaciones++;
            throw new IllegalArgumentException("La fuente y el sumidero no pueden ser el mismo nodo.");
        }

        int[] padres = new int[numVertices];
        asignaciones++;

        int flujoMaximo = 0;
        asignaciones++;

        // Mientras haya un camino de aumento, actualizamos el flujo máximo
        while (bfs(fuente, sumidero, padres)) {
            comparaciones++;

            int flujoCamino = Integer.MAX_VALUE;
            asignaciones++;

            // Encontrar el flujo mínimo en el camino encontrado
            for (int v = sumidero; v != fuente; v = padres[v]) {
                comparaciones++;

                int u = padres[v];
                asignaciones++;

                flujoCamino = Math.min(flujoCamino, grafoResidual[u][v]);
                asignaciones++;
            }
            comparaciones++;

            // Actualizar el flujo residual y mostrar la ruta
            System.out.print("\033[31m Ruta encontrada: " + fuente);
            for (int v = sumidero; v != fuente; v = padres[v]) {
                comparaciones++;

                int u = padres[v];
                asignaciones++;

                grafoResidual[u][v] -= flujoCamino;
                asignaciones++;

                grafoResidual[v][u] += flujoCamino;
                asignaciones++;

                System.out.print(" -> " + v);
            }
            System.out.println(" | Flujo del camino: \033[0m" + flujoCamino);


            flujoMaximo += flujoCamino;
            asignaciones++;
        }
        comparaciones++;

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
