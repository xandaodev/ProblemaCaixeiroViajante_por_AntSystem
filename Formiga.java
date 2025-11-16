package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Arrays;
//classe formiga, cada objeto formiga vai constuir uma viagem diferente, candidata para solucao
public class Formiga {
    private boolean[] visitados;//quais cidade ja foram visitadas
    private double distanciaViagem;//custo total da viagem completa
    private int trilha[];//aqui fica a sequencia de cidades visitadas
    private int numCidades;
    private int cidadeAtual;//onde a formiguinha ta 
    private int indiceTrilha; //isso aqui é pra saber qual posicao da trilha vamos preencehr

    //construtor
    public Formiga(int numCidades){
        this.visitados = new boolean[numCidades];
        this.trilha = new int[numCidades];
        this.distanciaViagem = 0.0; 
        this.numCidades = numCidades;
        this.indiceTrilha =0;
    }

    //prepara a formiga pra nova iteracao, vamos chamar esse metodo no inicio de todo iteracao do algoritmo
    public void resetar(int cidadeInicial){
        Arrays.fill(visitados, false); //funcao pra limpar o vetor de visitados
        this.distanciaViagem =0.0;
        this.indiceTrilha=0;
        //coloca na cidade inicial:
        visitarCidade(cidadeInicial);
    }

    public void visitarCidade(int cidade){
        visitados[cidade] = true;//marca a cidade como visitada
        trilha[indiceTrilha] = cidade;
        cidadeAtual = cidade;//atualiza a cidade atual
        indiceTrilha++;//prepara pra proixma visita
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
        //loop quq vai da primiera cidade ate a penultima
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
