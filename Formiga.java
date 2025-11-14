package ProblemaCaixeiroViajante_por_AntSystem;

public class Formiga {
    private boolean[] visitados;
    private double distanciaViagem;
    private int trilha[];
    private int numCidades;
    private int cidadeAtual;

    public Formiga(int numCidades){
        this.visitados = new boolean[numCidades];
        this.distanciaViagem = 0.0;
        this.trilha = new int[numCidades];
        this.numCidades = numCidades;
    }

    public boolean foiVisitada(int cidade){
        return visitados[cidade];
    }

    public int getCidadeAtual(){
        return cidadeAtual;
    }

    public int[] getTrilha(){
        return trilha;
    }

    public double getDistanciaViagem(){
        return distanciaViagem;
    }

    
}
