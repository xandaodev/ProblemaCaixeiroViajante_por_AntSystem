package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // exemplo simples de 5 cidades 
        double[][] distanciasExemplo = {
            // Cidades: 0   1   2   3   4   5   6
            {0, 10, 15, 20, 30, 25, 12}, // Cidade 0
            {10, 0, 18, 5, 22, 30, 20}, // Cidade 1
            {15, 18, 0, 14, 10, 8, 28}, // Cidade 2
            {20, 5, 14, 0, 16, 26, 17}, // Cidade 3
            {30, 22, 10, 16, 0, 12, 19}, // Cidade 4
            {25, 30, 8, 26, 12, 0, 14}, // Cidade 5
            {12, 20, 28, 17, 19, 14, 0}  // Cidade 6
        };

        int numCidades = distanciasExemplo.length;
        
        Pcv pcv = new Pcv(numCidades);
        pcv.setMatrizDistancias(distanciasExemplo); 
        
        int numeroFormigas = numCidades; 
        int maxIteracoes = 100; 

        System.out.println("iniciado ant system com " + numeroFormigas + " formigas...");

        AntSystem as = new AntSystem(pcv, numeroFormigas);
        as.executar(maxIteracoes);

        System.out.println("melhor distancia encontrada: " + as.getMelhorDistancia());
        System.out.println("melhor caminho: " + Arrays.toString(as.getMelhorTrilha()));
    }
}