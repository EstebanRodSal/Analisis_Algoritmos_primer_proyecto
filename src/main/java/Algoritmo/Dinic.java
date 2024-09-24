package Algoritmo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase que implementa el algoritmo de Dinic para encontrar el flujo máximo en una red de flujo.
 * Este algoritmo utiliza búsqueda en anchura (BFS) y búsqueda en profundidad (DFS).
 */
public class Dinic {
    private int numVertices;
    private int[][] capacidad;
    private ArrayList<Integer>[] grafo;
    private int[] nivel;
    private int[] siguiente;
    private int asignaciones = 0;
    private int comparaciones = 0;

    /**
     * Constructor de la clase Dinic.
     * Inicializa el grafo con la capacidad y el número de vértices.
     *
     * @param grafo Grafo con la matriz de capacidades.
     */
    public Dinic(Grafo grafo) {
        this.numVertices = grafo.getCapacidad().length;

        this.capacidad = grafo.getCapacidad();

        this.grafo = new ArrayList[numVertices];

        for (int i = 0; i < numVertices; i++) {
            this.grafo[i] = new ArrayList<>();
        }

        // Construir lista de adyacencia a partir de la matriz de capacidades
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {

                if (capacidad[i][j] > 0) {
                    this.grafo[i].add(j);
                    this.grafo[j].add(i);  // Se agrega el arco inverso también
                }
            }
        }

        nivel = new int[numVertices];
        siguiente = new int[numVertices];

    }

    /**
     * Calcula el flujo máximo entre dos nodos en la red de flujo.
     *
     * @param fuente   El nodo fuente.
     * @param sumidero El nodo sumidero.
     * @return El flujo máximo posible desde la fuente hasta el sumidero.
     */
    public int flujoMaximo(int fuente, int sumidero) {
        int flujoMaximo = 0;
        asignaciones++;

        while (bfs(fuente, sumidero)) {
            comparaciones++;

            for (int i = 0; i < numVertices; i++) {
                siguiente[i] = 0;
                asignaciones++;
            }

            int flujo;
            while ((flujo = dfs(fuente, sumidero, Integer.MAX_VALUE)) > 0) {
                flujoMaximo += flujo;
                asignaciones++;
            }
        }

        return flujoMaximo;
    }

    /**
     * Realiza una búsqueda en anchura (BFS) para calcular los niveles de los vértices.
     *
     * @param fuente   Nodo fuente del flujo.
     * @param sumidero Nodo sumidero del flujo.
     * @return true si se puede aumentar el flujo, false de lo contrario.
     */
    private boolean bfs(int fuente, int sumidero) {
        Queue<Integer> cola = new LinkedList<>();
        asignaciones++;

        for (int i = 0; i < numVertices; i++) {
            nivel[i] = -1;
            asignaciones++;
        }

        nivel[fuente] = 0;
        asignaciones++;

        cola.add(fuente);
        asignaciones++;

        while (!cola.isEmpty()) {
            comparaciones++;
            int u = cola.poll();
            asignaciones++;

            for (int v : grafo[u]) {
                comparaciones++;
                if (nivel[v] == -1 && capacidad[u][v] > 0) {
                    comparaciones += 2;

                    nivel[v] = nivel[u] + 1;
                    cola.add(v);
                    asignaciones += 3;
                }
            }
        }

        return nivel[sumidero] != -1;
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) para encontrar el camino de aumento.
     *
     * @param u        Vértice actual.
     * @param sumidero Nodo sumidero del flujo.
     * @param flujo    Flujo disponible.
     * @return El flujo máximo posible para el camino actual.
     */
    private int dfs(int u, int sumidero, int flujo) {
        comparaciones++;

        if (u == sumidero) {
            return flujo;
        }

        for (int i = siguiente[u]; i < grafo[u].size(); i++, siguiente[u]++) {
            comparaciones++;
            int v = grafo[u].get(i);
            comparaciones++;

            if (nivel[v] == nivel[u] + 1 && capacidad[u][v] > 0) {
                comparaciones += 2;

                int flujoDisponible = Math.min(flujo, capacidad[u][v]);
                asignaciones++;

                int flujoAumentado = dfs(v, sumidero, flujoDisponible);
                asignaciones++;

                if (flujoAumentado > 0) {
                    capacidad[u][v] -= flujoAumentado;
                    capacidad[v][u] += flujoAumentado;
                    asignaciones += 2;

                    System.out.println("\033[31m Ruta: " + u + " -> " + v + " | Flujo: \033[0m" + flujoAumentado);
                    return flujoAumentado;
                }
            }
        }

        return 0;
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
