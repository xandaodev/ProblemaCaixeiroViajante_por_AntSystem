package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Exemplo simples de 5 cidades (Simétrico, diagonal principal = 0)
        // Podem substituir isto pela leitura do ficheiro depois
        double[][] distanciasExemplo = {
            {0, 10, 12, 25, 15},
            {10, 0, 20, 18, 8},
            {12, 20, 0, 16, 12},
            {25, 18, 16, 0, 22},
            {15, 8, 12, 22, 0}
        };

        int numCidades = distanciasExemplo.length;
        
        // 1. Criar o problema (PCV)
        Pcv pcv = new Pcv(numCidades);
        pcv.setMatrizDistancias(distanciasExemplo); // Carregar dados
        
        // 2. Configurar parâmetros do algoritmo
        // O PDF sugere m = número de cidades [cite: 237]
        int numeroFormigas = numCidades; 
        int maxIteracoes = 100; // Teste inicial

        System.out.println("A iniciar Ant System com " + numeroFormigas + " formigas...");

        // 3. Criar e executar o sistema
        AntSystem as = new AntSystem(pcv, numeroFormigas);
        as.executar(maxIteracoes);

        // 4. Resultados Finais
        System.out.println("-------------------------------");
        System.out.println("Melhor Distância Encontrada: " + as.getMelhorDistancia());
        System.out.println("Melhor Caminho: " + Arrays.toString(as.getMelhorTrilha()));
    }
}