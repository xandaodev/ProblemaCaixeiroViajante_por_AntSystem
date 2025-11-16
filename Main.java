package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // exemplo simples de 5 cidades 
        double[][] distanciasExemplo = {
            {0, 10, 12, 25, 15},
            {10, 0, 20, 18, 8},
            {12, 20, 0, 16, 12},
            {25, 18, 16, 0, 22},
            {15, 8, 12, 22, 0}
        };

        int numCidades = distanciasExemplo.length;
        
        Pcv pcv = new Pcv(numCidades);
        pcv.setMatrizDistancias(distanciasExemplo); 
        
        int numeroFormigas = numCidades; 
        int maxIteracoes = 100; 

        System.out.println("A iniciar Ant System com " + numeroFormigas + " formigas...");

        AntSystem as = new AntSystem(pcv, numeroFormigas);
        as.executar(maxIteracoes);

        System.out.println("-------------------------------");
        System.out.println("Melhor Distância Encontrada: " + as.getMelhorDistancia());
        System.out.println("Melhor Caminho: " + Arrays.toString(as.getMelhorTrilha()));
    }
}