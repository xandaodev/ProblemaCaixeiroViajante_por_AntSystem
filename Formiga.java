package ProblemaCaixeiroViajante_por_AntSystem;

public class Formiga {
    private boolean[] visitados;
    private double distanciaViagem;
    private int trilha[];//aqui fica a sequencia de cidades visitadas
    private int numCidades;
    private int cidadeAtual;
    private int indiceTrilha; //isso aqui é pra saber qual posicao da trilha vamos preencehr

    public Formiga(int numCidades){
        this.visitados = new boolean[numCidades];
        this.trilha = new int[numCidades];
        this.distanciaViagem = 0.0;
        this.numCidades = numCidades;
        this.indiceTrilha =0;
    }

    public void resetar(int cidadeInicial){
        Arrays.fill(visitados, false); //funcao pra limpar o vetor de visitados
        this.distanciaViagem =0.0;
        this.indiceTrilha=0;
        //coloca na cidade inicial:
        visitarCidade(cidadeInicial);

    }

    public void visitarCidade(int cidade){
        visitados[cidade] = true;
        trilha[indiceTrilha] = cidade;
        cidadeAtual = cidade;
        indiceTrilha++;
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

    //metodo que calcular a distancia total da viagem
    public double calcularDistanciaTotal(Pcv pcv){
        double dist=0;
        for(int i=0;i<numCidades -1 ;i++){
            dist =+ pcv.getDistancia(trilha[i], trilha[i+1]);
        }
        dist += pcv.getDistancia(trilha[numCidades-1], trilha[0]); [cite: 97]

        this.distanciaViagem =dist;
        return dist;
    }

    
}
