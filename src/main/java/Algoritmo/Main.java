package Algoritmo;

public class Main {

    public static void main(String[] args) {
        // Parámetros para los grafos a medir {Vertices, arcos}
        int[][] casos = {
                {20, 24}, {40, 48}, {80, 56},
                {10, 100}, {20, 400}, {40, 1600}, {80, 6400}
        };

        System.out.println("------------Grafo quemado-------------------------------------");
        Grafo grafoQuemado = Grafo.generarGrafoQuemado();

        // Ejecutar y medir Edmonds-Karp
        EdmondsKarp ekQuemado = new EdmondsKarp(grafoQuemado);
        medirAlgoritmoEdmondsKarp("Edmonds-Karp", ekQuemado, 0, 10 - 1, 10, 12);


        // Ejecutar y medir Ford-Fulkerson
        FordFulkerson ffQuemado = new FordFulkerson(grafoQuemado);
        medirAlgoritmoFordFulkerson("Ford-Fulkerson", ffQuemado, 0, 10 - 1, 10, 12);


        // Ejecutar y medir Dinic
        Dinic dinicQuemado = new Dinic(grafoQuemado);
        medirAlgoritmoDinic("Dinic", dinicQuemado, 0, 10 - 1, 10, 12);

        System.out.println("------------Grafos Aleatorios-------------------------------------");
        for (int[] caso : casos) {
            int vertices = caso[0];
            int arcos = caso[1];

            Grafo grafo = Grafo.generarGrafoAleatorio(vertices, arcos);

            // Ejecutar y medir Edmonds-Karp
            EdmondsKarp ek = new EdmondsKarp(grafo);
            medirAlgoritmoEdmondsKarp("Edmonds-Karp", ek, 0, vertices - 1, vertices, arcos);


            // Ejecutar y medir Ford-Fulkerson
            FordFulkerson ff = new FordFulkerson(grafo);
            medirAlgoritmoFordFulkerson("Ford-Fulkerson", ff, 0, vertices - 1, vertices, arcos);


            // Ejecutar y medir Dinic
            Dinic dinic = new Dinic(grafo);
            medirAlgoritmoDinic("Dinic", dinic, 0, vertices - 1, vertices, arcos);
        }
    }

    /**
     * Método para medir el tiempo de ejecución, el flujo máximo calculado,
     * las métricas (asignaciones y comparaciones) del algoritmo Edmonds-Karp,
     * y determinar si el grafo es denso o ligero.
     *
     * @param nombre    Nombre del algoritmo a medir.
     * @param algoritmo Instancia del algoritmo a medir.
     * @param fuente    Nodo fuente para el cálculo del flujo máximo.
     * @param sumidero  Nodo sumidero para el cálculo del flujo máximo.
     * @param vertices  Número de vértices en el grafo.
     * @param arcos     Número de aristas en el grafo.
     */
    private static void medirAlgoritmoEdmondsKarp(String nombre, EdmondsKarp algoritmo, int fuente, int sumidero, int vertices, int arcos) {
        long inicio = System.nanoTime();  // Iniciar el conteo de tiempo en nanosegundos
        int flujoMaximo = algoritmo.flujoMaximo(fuente, sumidero);
        long fin = System.nanoTime();  // Finalizar el conteo de tiempo en nanosegundos

        // Convertir el tiempo de ejecución a milisegundos con precisión de 3 decimales
        double tiempoEjecucion = (fin - inicio) / 1_000_000.0;

        // Determinar si el grafo es denso o ligero
        int maxArcosPosibles = vertices * (vertices - 1); // Máximo número de aristas posible en un grafo dirigido
        String tipoGrafo = (arcos > maxArcosPosibles / 2) ? "Denso" : "Ligero";

        // Formatear la salida para mostrar 3 decimales
        System.out.println(nombre + " - Flujo máximo: " + flujoMaximo);
        System.out.printf("Tiempo de ejecución: %.3f ms%n", tiempoEjecucion); // Aplicando la precisión de 3 decimales
        System.out.println("Asignaciones: " + algoritmo.getAsignaciones());
        System.out.println("Comparaciones: " + algoritmo.getComparaciones());
        System.out.println("Tipo de grafo: " + tipoGrafo);
        System.out.println("-------------------------------------------");
    }

    /**
     * Método para medir el tiempo de ejecución, el flujo máximo calculado,
     * las métricas (asignaciones y comparaciones) del algoritmo Dinic,
     * y determinar si el grafo es denso o ligero.
     *
     * @param nombre    Nombre del algoritmo a medir.
     * @param algoritmo Instancia del algoritmo a medir.
     * @param fuente    Nodo fuente para el cálculo del flujo máximo.
     * @param sumidero  Nodo sumidero para el cálculo del flujo máximo.
     * @param vertices  Número de vértices en el grafo.
     * @param arcos     Número de aristas en el grafo.
     */
    private static void medirAlgoritmoDinic(String nombre, Dinic algoritmo, int fuente, int sumidero, int vertices, int arcos) {
        long inicio = System.nanoTime();  // Iniciar el conteo de tiempo en nanosegundos
        int flujoMaximo = algoritmo.flujoMaximo(fuente, sumidero);
        long fin = System.nanoTime();  // Finalizar el conteo de tiempo en nanosegundos

        // Convertir el tiempo de ejecución a milisegundos con precisión de 3 decimales
        double tiempoEjecucion = (fin - inicio) / 1_000_000.0;

        // Determinar si el grafo es denso o ligero
        int maxArcosPosibles = vertices * (vertices - 1); // Máximo número de aristas posible en un grafo dirigido
        String tipoGrafo = (arcos > maxArcosPosibles / 2) ? "Denso" : "Ligero";

        // Formatear la salida para mostrar 3 decimales
        System.out.println(nombre + " - Flujo máximo: " + flujoMaximo);
        System.out.printf("Tiempo de ejecución: %.3f ms%n", tiempoEjecucion); // Aplicando la precisión de 3 decimales
        System.out.println("Asignaciones: " + algoritmo.getAsignaciones());
        System.out.println("Comparaciones: " + algoritmo.getComparaciones());
        System.out.println("Tipo de grafo: " + tipoGrafo);
        System.out.println("-------------------------------------------");
    }

    private static void medirAlgoritmoFordFulkerson(String nombre, FordFulkerson algoritmo, int fuente, int sumidero, int vertices, int arcos) {
        long inicio = System.nanoTime();
        int flujoMaximo = algoritmo.flujoMaximo(fuente, sumidero);
        long fin = System.nanoTime();
        double tiempoEjecucion = (fin - inicio) / 1_000_000.0;

        int maxArcosPosibles = vertices * (vertices - 1);
        String tipoGrafo = (arcos > maxArcosPosibles / 2) ? "Denso" : "Ligero";

        System.out.println(nombre + " - Flujo máximo: " + flujoMaximo);
        System.out.printf("Tiempo de ejecución: %.3f ms%n", tiempoEjecucion);
        System.out.println("Asignaciones: " + algoritmo.getAsignaciones());
        System.out.println("Comparaciones: " + algoritmo.getComparaciones());
        System.out.println("Tipo de grafo: " + tipoGrafo);
        System.out.println("-------------------------------------------");

    }
}