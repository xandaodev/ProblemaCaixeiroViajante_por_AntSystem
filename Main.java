package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        String arquivo = "LAU15_dist.txt"; 

        Pcv pcv = new Pcv(0);
        pcv.lerArquivo(arquivo);

        int numCidades = pcv.getNumCidades();
        int maxIteracoes = 100;
        double rho = 0.5;
        double Q = 100.0;
        //no slide sobre o trabalho tem uma diferença em relaçao aos valores dessa variavel, no slide 16 fala 10^-16, e no 29 fala 10^-6
        //na duvida optamos por deixar o ultimo valor que apareceu, no ultimo slide, que é 10^-6
        //double tau0 = 0.0000000000000001; // 10 elevado a -16
        double tau0 = 1.0e-6; // 10 elevado a -6

        // valores do experimento fatorial
        int[] valoresM = { numCidades, 2 * numCidades };
        double[] valoresAlpha = { 1.0, 2.0 };
        double[] valoresBeta  = { 3.0, 5.0 };

        // quantas vezes repetir cada combinação 
        int numRepeticoes = 5;

        System.out.println("=== Experimento fatorial no Ant System ===");
        System.out.println("Instância: " + arquivo + " (" + numCidades + " cidades)\n");

        for (int m : valoresM) {
            for (double alpha : valoresAlpha) {
                for (double beta : valoresBeta) {

                    double somaMelhores = 0.0;
                    double melhorDaComb = Double.MAX_VALUE;
                    int[] melhorTrilhaDaComb = null;

                    System.out.println("-----------------------------------------");
                    System.out.println("m = " + m + ", alpha = " + alpha + ", beta = " + beta);

                    for (int rep = 1; rep <= numRepeticoes; rep++) {

                        AntSystem as = new AntSystem(pcv, m, alpha, beta, rho, Q, tau0);
                        as.executar(maxIteracoes);

                        double dist = as.getMelhorDistancia();
                        somaMelhores += dist;

                        if (dist < melhorDaComb) {
                            melhorDaComb = dist;
                            melhorTrilhaDaComb = as.getMelhorTrilha().clone();
                        }

                        System.out.println("  Execução " + rep + " -> melhor distância = " + dist);
                    }

                    double media = somaMelhores / numRepeticoes;

                    System.out.println(">> RESULTADO COMBINAÇÃO");
                    System.out.println("   Média das melhores distâncias = " + media);
                    System.out.println("   Melhor distância obtida      = " + melhorDaComb);
                    System.out.println("   Melhor trilha (da combinação)= " 
                            + Arrays.toString(melhorTrilhaDaComb));
                    System.out.println();
                }
            }
        }

    }
}
