package ProblemaCaixeiroViajante_por_AntSystem;

import java.util.Random;

//classe da colonia das formiguinhas
public class AntSystem {
    private Pcv pcv;
    private Formiga[] formigas;//vetor que armazena as formigas da colonia
    private double[][] feromonios;//matriz que guarda a qtd de feromonios em cada aresta

    //vetor da melhor trilha universal
    //vetor que gaurda a melhor solucao encontrada ate agr:
    private int[] melhorTrilhaGlobal;
    //guarda o custo da melhor viagem
    private double melhorDistanciaGlobal = Double.MAX_VALUE;//começa com infinito


    //parametros que ele deu no slide:
    
    private double alpha = 1.0; //influencia do feromonio
    private double beta = 3.0; //influencia da distancia
    private double rho = 0.5; //taxa de evaporaçao
    private double Q = 100.0; //contsante do deposito de feromonio
    private double tau0 = 1e-6;// 10 elevado a -6 - feromonio inicial

    //gera numeros aleatorios
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
        //preenche a matriz de feromonio
        inicializarFeromonios();
    }
    

    //************************************************************************************ 


    //metodos principais abaixo (coisa pra caralho):

    //metodo que inicializa os feromonios
    public void inicializarFeromonios(){
        for(int i=0;i <pcv.getNumCidades();i++){
            for (int j = 0; j < pcv.getNumCidades(); j++) {
                feromonios[i][j] = tau0;
            }
        }
    }

    //metodo que executa o algoritmo, loop principal:
    public void executar(int maxIteracoes){
        for(int t=0; t<maxIteracoes; t++){
            //consturir a viagem de cada formiga:
            construirSolucoes();
            //atualiza os feromonios(hora que o feromonio evapora que o vinicius falou):
            atualizarFeromonios();
            System.out.println("Iteração " + t + " - Melhor Distância: " + melhorDistanciaGlobal);
        }
    }

    //metodo que controi a viagem de cada formiguinha, cada uma faz sua propria viagem:
    private void construirSolucoes(){
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
                melhorTrilhaGlobal =a.getTrilha().clone();//esse.clone é pra criar a copia da trilha, muito util essa paradinha ai 
            }
        }
    }

    //metodo da roleta que o vinicius tinha feito em aula
    public void selecionarProximaCidade(Formiga a){
        int i = a.getCidadeAtual();
        int numCidades= pcv.getNumCidades();
        double[] probabilidades = new double[numCidades];//armazena o "desejo" pra cada cidade
        double somaProbabilidades = 0.0;//soma o desejo

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
        //aqui ele gira a roleta e sorteia um nmr entre 0  e somaProbal=bilidades
        double roleta= aleatorio.nextDouble() * somaProbabilidades;
        double acumulado = 0.0;
        for(int j = 0; j < numCidades; j++){
            //so considera cidade q n foram visitadas
            if(!a.foiVisitada(j)){
                acumulado += probabilidades[j];//acumula o desjeo
                if(acumulado >=roleta){
                    a.visitarCidade(j);
                    return; // escolheu a cidade => sai do metodo
                }
            }
        }
    }

    //metodo de atualizar feromonios
    public void atualizarFeromonios(){
        //evaporacao
        for(int i=0; i<pcv.getNumCidades(); i++){
            for(int j=0; j< pcv.getNumCidades(); j++){
                feromonios[i][j] *= (1.0-rho);
            }
        }
        //deposito
        for(Formiga k : formigas){
            double deltaTau = Q / k.getDistanciaViagem();
            int[] trilha = k.getTrilha();
            for(int p =0;p< trilha.length - 1; p++){
                int i = trilha[p];
                int j = trilha[p + 1];
                //depositando feromonios
                feromonios[i][j] += deltaTau;
                feromonios[j][i] += deltaTau;
            }
            // Não esquecer a aresta de volta (última -> primeira)
            int ultimo = trilha[trilha.length - 1];
            //pega a ultima cidade da  trilha
            int primeiro =trilha[0];
            //deposita feromonio na aresta
            feromonios[ultimo][primeiro] += deltaTau;
            feromonios[primeiro][ultimo] += deltaTau;
        }
    }

    public double getMelhorDistancia(){ 
        return melhorDistanciaGlobal; 
    
    }
    public int[] getMelhorTrilha(){ 
        return melhorTrilhaGlobal; 
    }
    
}
