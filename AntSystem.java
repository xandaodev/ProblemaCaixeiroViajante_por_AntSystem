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
    
    private double alpha;   //influencia do feromônio
    private double beta;    //influência da distância
    private double rho; //taxa de evaporação
    private double Q;   //contsante do depósito de feromônio
    private double tau0;    // 10 elevado a -16 - feromônio inicial

    //gera numeros aleatórios
    private Random aleatorio = new Random();

    //contrutor(inicializa a colônia com valores padrão e cria as formigas):
    public AntSystem(Pcv pcv, int numFormigas){
        this.pcv= pcv;
        this.formigas =new Formiga[numFormigas];
        this.feromonios =new double[pcv.getNumCidades()][pcv.getNumCidades()];
        this.melhorTrilhaGlobal=new int[pcv.getNumCidades()];

        // inicia as formigas
        for(int k = 0;k< numFormigas;k++){
            formigas[k]=new Formiga(pcv.getNumCidades());
        }
        //preenche a matriz de feromonio
        inicializarFeromonios();
    }

    //construtor secundario para os parâmetros alfa, beta, etc
    public AntSystem(Pcv pcv, int numFormigas, double alpha, double beta, double rho, double Q, double tau0) {
        this.pcv = pcv;
        this.formigas = new Formiga[numFormigas];
        this.feromonios = new double[pcv.getNumCidades()][pcv.getNumCidades()];
        this.melhorTrilhaGlobal = new int[pcv.getNumCidades()];
        for (int k = 0; k < numFormigas; k++) {
            formigas[k] = new Formiga(pcv.getNumCidades());
        }
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
        this.Q = Q;
        this.tau0 = tau0;
        inicializarFeromonios();
    }

    //metodos principais abaixo:

    //inicializa os feromônios
    public void inicializarFeromonios(){
        for(int i=0;i <pcv.getNumCidades();i++){
            for (int j = 0; j < pcv.getNumCidades(); j++) {
                feromonios[i][j] = tau0;
            }
        }
    }

    //método que executa o algoritmo, loop principal:
    public void executar(int maxIteracoes){
        for(int t=0; t<maxIteracoes; t++){
            //consturir a viagem de cada formiga:
            construirSolucoes();
            atualizarFeromonios();
        }
    }

    // constroi e gerencia o ciclo de vida das formigas em uma iteração, reseta, anda e avalia
    private void construirSolucoes(){
        for(Formiga a : formigas){
            //cada formigas vai pra uma cidade aleatoria
            a.resetar(aleatorio.nextInt(pcv.getNumCidades()));
        }

        int numPassos = pcv.getNumCidades()-1;
        //move todas as formigas ate completar o ciclo
        for(int passo=0;passo <numPassos; passo++){
            for(Formiga a: formigas){
                selecionarProximaCidade(a);
            }
        }
        //calcula as distâncias e atualiza com a menor
        for(Formiga a: formigas){
            double dist = a.calcularDistanciaTotal(pcv);
            if(dist<melhorDistanciaGlobal){
                melhorDistanciaGlobal = dist;
                melhorTrilhaGlobal =a.getTrilha().clone();
            }
        }
    }

    //equaçao da roleta
    public void selecionarProximaCidade(Formiga a){
        int i = a.getCidadeAtual();
        int numCidades= pcv.getNumCidades();
        double[] probabilidades = new double[numCidades];
        double somaProbabilidades = 0.0;

        // calcula o desejo de ir para acidade vizinha
        for(int j=0; j< numCidades;j++){
            if (!a.foiVisitada(j)){
                double tau =feromonios[i][j];                
                double eta = 1.0 / pcv.getDistancia(i, j);   
                probabilidades[j] =Math.pow(tau, alpha) * Math.pow(eta, beta);
                somaProbabilidades += probabilidades[j];
            }else{
                probabilidades[j] = 0.0; //não pode ir para cidade já visitada
            }
        }

        // executa a roleta para escolher a próxima cidade baseado nas probabilidades calculadas
        double roleta= aleatorio.nextDouble() * somaProbabilidades;
        double acumulado = 0.0;
        for(int j = 0; j < numCidades; j++){
            if(!a.foiVisitada(j)){
                acumulado += probabilidades[j];
                if(acumulado >=roleta){
                    a.visitarCidade(j);
                    return; //depois que "escolhe" a cidade sai do método
                }
            }
        }
    }

    //atualiza feromonios, realiza a evaporaçao e os novos feromonios nas trilhas usadas
    public void atualizarFeromonios(){
        for(int i=0; i<pcv.getNumCidades(); i++){
            for(int j=0; j< pcv.getNumCidades(); j++){
                feromonios[i][j] *= (1.0-rho);
            }
        }

        //as rotas mais curtas receebem mais feromonios
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
            int ultimo = trilha[trilha.length - 1];
            int primeiro =trilha[0];

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