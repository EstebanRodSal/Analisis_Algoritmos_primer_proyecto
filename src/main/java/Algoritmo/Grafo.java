package Algoritmo;

import java.util.Random;

/**
 * Clase que representa un grafo dirigido con una matriz de capacidad.
 * Permite agregar aristas con capacidades específicas y generar grafos aleatorios.
 */
public class Grafo {
    private int numVertices;
    private int[][] capacidad; // Matriz de capacidad del grafo

    /**
     * Constructor de la clase Grafo.
     * Inicializa el grafo con un número dado de vértices y crea la matriz de capacidad.
     *
     * @param numVertices El número de vértices del grafo.
     */
    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.capacidad = new int[numVertices][numVertices];
    }

    /**
     * Agrega una arista al grafo con una capacidad específica.
     *
     * @param fuente   El vértice de origen de la arista.
     * @param destino  El vértice de destino de la arista.
     * @param capacidad La capacidad de la arista.
     */
    public void agregarArista(int fuente, int destino, int capacidad) {
        this.capacidad[fuente][destino] = capacidad;
    }

    /**
     * Devuelve la matriz de capacidades del grafo.
     *
     * @return La matriz de capacidad del grafo.
     */
    public int[][] getCapacidad() {
        return capacidad;
    }

    /**
     * Genera un grafo aleatorio con un número específico de vértices y aristas.
     * Las capacidades de las aristas se generan de forma aleatoria entre 20 y 700.
     *
     * @param vertices El número de vértices del grafo.
     * @param arcos    El número de aristas del grafo.
     * @return Un objeto Grafo generado aleatoriamente.
     */
    public static Grafo generarGrafoAleatorio(int vertices, int arcos) {
        Grafo grafo = new Grafo(vertices);
        Random rand = new Random();

        for (int i = 0; i < arcos; i++) {
            int fuente = rand.nextInt(vertices);
            int destino = rand.nextInt(vertices);
            int capacidad = 20 + rand.nextInt(681); // Capacidad entre 20 y 700 para cada arco

            while (fuente == destino) {
                destino = rand.nextInt(vertices);
            }
            grafo.agregarArista(fuente, destino, capacidad);
        }

        return grafo;
    }

    /**
     * Devuelve un grafo quemado para el caso {10, 12}.
     *
     * @return Un objeto Grafo con los valores predefinidos.
     */
    public static Grafo generarGrafoQuemado() {
        Grafo grafo = new Grafo(10);
        int[][] matrizFija = {
                {0, 0, 544, 0, 0, 0, 0, 0, 610, 173},
                {0, 0, 160, 0, 397, 0, 0, 0, 440, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 503},
                {0, 0, 0, 0, 0, 0, 0, 632, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 43, 0, 0, 0, 0},
                {0, 186, 182, 0, 0, 0, 0, 0, 0, 0},
                {0, 322, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        for (int i = 0; i < matrizFija.length; i++) {
            for (int j = 0; j < matrizFija[i].length; j++) {
                if (matrizFija[i][j] > 0) {
                    grafo.agregarArista(i, j, matrizFija[i][j]);
                }
            }
        }

        return grafo;
    }
}
