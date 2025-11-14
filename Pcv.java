package ProblemaCaixeiroViajante_por_AntSystem;
//tem que por umas bibliotecas aqui

public class Pcv {
    private int numCidades;
    private double [][] matrizDistancias;

    public Pcv(int numCidades){
        this.numCidades = numCidades;
        this.matrizDistancias = new double[numCidades][numCidades];

    }

    public void lerArquivo(String caminhoArquivo){
        //tem que fazer aqui a funcao de ler o arquivo
    }

    public void setMatrizDistancias(double[][] matriz){
        this.matrizDistancias = matriz;
        this.numCidades = matriz.length;
    }

    public double getDistancia(int cidadeA, int cidadeB){
        return matrizDistancias[cidadeA][cidadeB];
    }

    public int getNumCidades(){
        return numCidades;
    }
    
}
