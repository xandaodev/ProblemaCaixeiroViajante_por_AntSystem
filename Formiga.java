package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Arrays;

public class Formiga {
    private boolean[] visitados; //quais cidades já foram visitadas
    private double distanciaViagem; //custo total da viagem completa
    private int trilha[]; //sequência de cidades visitadas
    private int numCidades;
    private int cidadeAtual;     
    private int indiceTrilha; 

    //construtor
    public Formiga(int numCidades){
        this.visitados = new boolean[numCidades];
        this.trilha = new int[numCidades];
        this.distanciaViagem = 0.0; 
        this.numCidades = numCidades;
        this.indiceTrilha =0;
    }

    public void resetar(int cidadeInicial){
        Arrays.fill(visitados, false); //limpa o vetor de visitados
        this.distanciaViagem =0.0;
        this.indiceTrilha=0;
        //coloca na cidade inicial:
        visitarCidade(cidadeInicial);
    }

    public void visitarCidade(int cidade){
        visitados[cidade] = true;//marca a cidade como visitada
        trilha[indiceTrilha] = cidade;
        cidadeAtual = cidade;//atualiza a cidade atual
        indiceTrilha++;//prepara pra próxima visita
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

    public double calcularDistanciaTotal(Pcv pcv){
        double dist=0;
        for(int i=0;i<numCidades -1 ;i++){
            dist+= pcv.getDistancia(trilha[i], trilha[i+1]);
        }
        dist += pcv.getDistancia(trilha[numCidades-1], trilha[0]); 
        //salva o resultado final
        this.distanciaViagem =dist;
        //retorna o valor calculado
        return dist;
    }

    
}
