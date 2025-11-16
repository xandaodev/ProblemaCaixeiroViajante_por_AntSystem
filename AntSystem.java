package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Random;

public class AntSystem {
    private Pcv pcv;
    private Formiga[] formigas;
    private double[][] feromonios;
    //vetor da melhor trilha universal

    //vetor que gaurda a melhor solucao encontrada ate agr:
    private int[] melhorTrilhaGlobal;
    private double melhorDistanciaGlobal = Double.MAX_VALUE;//começa com infinito


    //parametros que ele deu no slide:
    
    private double alpha = 1.0; //influencia do feromonio
    private double beta = 5.0; //influencia da distancia
    private double rho = 0.5; //taxa de evaporaçao
    private double Q = 100.0; //contsante do deposito de feromonio
    private double tau0 = 1e-6;// 10 elevado a -6 - feromonio inicial

    private Random aleatorio = new Random();

    //contrutor:
    public AntSystem(Pcv pcv, int numFormigas){
        this.pcv= pcv;
        this.formigas =new Formiga[numFormigas];
        this.feromonios =new double[pcv.getNumCidades()][pcv.getNumCidades()];
        this.melhorTrilhaGlobal=new int[pcv.getNumCidades()];

        // inicia as formiguinha
        for(int k = 0;k< numFormigas;k++){
            formigas[k]=new Formiga(pcv.getNumCidades());
        }
        
        inicializarFeromonios();
    }
    
    //metodo que inicializa os feromonios
    public void inicializarFeromonios(){
        for(int i=0;i <pcv.getNumCidades();i++){
            for (int j = 0; j < pcv.getNumCidades(); j++) {
                feromonios[i][j] = tau0; [cite: 140]
            }
        }
    }

    //metodo que executa o algoritmo:
    public void executar(int maxIteracoes){
        for(int t=0; t<maxIteracoes; t++){
            //consturir a viagem de cada formiga:
            construirSolucoes();
            //atualiza os feromonios:
            atualizarFeromonios();
            System.out.println("Iteração " + t + " - Melhor Distância: " + melhorDistanciaGlobal);
        }
    }

    //metodo que controi a viagem de cada formiguinha, cada uma faz sua propria viagem:
    private void constuirSolucoes(){
        for(Formiga a : formigas){
            //aqui cada formigas vai pra uma cidade aleatoria
            a.resetar(aleatorio.nextInt(pcv.getNumCidades()));
        }
        //movendo as formgas:
        int numPassos = pcv.getNumCidades()-1;
        for(int passo=0;passo <numPassos; passo++){
            for(Formiga a: formigas){
                selecionarProximaCidade(a);
            }
        }
        //depois do calculo de todas as formigas, calcular as distancias e atualizar com a melhor
        for(Formiga a: formigas){
            double dist = a.calcularDistanciaTotal(pcv);
            if(dist<melhorDistanciaGlobal){
                melhorDistanciaGlobal = dist;
                melhorTrilhaGlobal =a.getTrilha().clone();
            }
        }
    }

    //metodo da roleta que o vinicius tinha feito em aula
    public void selecionarProximaCidade(Formiga a){
        int i = a.getCidadeAtual();
        int numCidades= pcv.getNumCidades();
        double[] probabilidades = new double[numCidades];
        double somaProbabilidades = 0.0;

        //calculo do desejo que o vinicius flaou em aula e to no slide
        for(int j=0; j< numCidades;j++){
            if (!a.foiVisitada(j)){
                double tau =feromonios[i][j];                
                double eta = 1.0 / pcv.getDistancia(i, j);   
                probabilidades[j] =Math.pow(tau, alpha) * Math.pow(eta, beta);
                somaProbabilidades += probabilidades[j];
            }else{
                probabilidades[j] = 0.0; // nao pode acessar cidade ja visitada
            }
        }
        double roleta= aleatorio.nextDouble() * somaProbabilidades;
        double acumulado = 0.0;
        for(int j = 0; j < numCidades; j++){
            if(!a.foiVisitada(j)){
                acumulado += probabilidades[j];
                if(acumulado >=roleta){
                    a.visitarCidade(j);
                    return; // escolheu a cidade => sai do metodo
                }
            }
        }
    }
    
}
