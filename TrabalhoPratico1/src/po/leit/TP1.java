package po.leit;

import po.leit.ui.Le;
import po.leit.ui.MyCommand;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TP1 {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos=0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair=false;

        interC=new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if  ( cmd.get(0).equalsIgnoreCase("carregar") ) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if ( alunosLidos != notA ) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }
                        
                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar") ) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar") ) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    int numeroU = Integer.parseInt(input);
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp") ) {
                    mostrarPauta();

                } else if (cmd.get(0).equalsIgnoreCase("mostrarr") ) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top") ) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome") ) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota") ) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help") ) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar") ) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array e um ficheiro
     * Lẽ cada linha do ficheiro e guarda no array. Retorna o número
     * de linhas que forma lidas do ficheiro.
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        //PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ( (nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty() ) ) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros entre
     * 0 e 20 que representam a nota de cada aluno.
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont = 0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados"
     * @param
     * @return
     */
    public static void mostrarAlunos() {
        int codigoAluno = 1;

        if(nomeAlunos[0] != null) {
            System.out.println("Codigo Nome de Estudante");
            for (String nomeAluno : nomeAlunos) {
                if(nomeAluno != null){
                final DecimalFormat decimal= new DecimalFormat("000000");
                System.out.print(decimal.format(codigoAluno++));
                System.out.println(" " + nomeAluno);
                }
            }
        }else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }


    /**
     * Método a ser implementando no TP1
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados".
     * Neste método os dados não são mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10 em 10. Esse número
     * que indica quantos nomes enchem a tela é um parâmetro do método.
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */
    public static void mostrarAlunos(int tela) {
        int codigoAluno = 1;
        int i = 0;
        int n = tela;
        int totalAlunos = 0;

        for (String nomeAluno : nomeAlunos) {
            if(nomeAluno != null)
                totalAlunos++;
        }

        if (nomeAlunos[0] != null) {
            loop:
            do {
                System.out.println("Codigo Nome de Estudante");
                do {
                    if (i != totalAlunos || nomeAlunos[i] != null){
                            final DecimalFormat decimal = new DecimalFormat("000000");
                            System.out.print(decimal.format(codigoAluno++));
                            System.out.println(" " + nomeAlunos[i]);
                            i++;
                    }else
                        break loop;
                } while (i < tela);

                    interC.showMsg("\nEnter para continuar...");
                    tela = tela + n;

            } while(i < totalAlunos);
        }else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá percorrer o array de notas, calcular o valor da média aritmética de notas, a nota máximo e
     * a nota mínima.
     * Os valores calculados devem ser guaraddos na variáveis notaAVG (média),
     * notaMax (nota máxima) e notaMin(nota mínima)
     * Devem validar se o array de notas tem elementos. Se estiver vazio devem somente apresentar
     * a mensagem "Não há dados"
     */

    private static void calcularMaxMinAvg() {
        int totalAlunos = 0;

        for (String nomeAluno : nomeAlunos) {
            if(nomeAluno != null)
                totalAlunos++;
        }

        notaMin=notasAlunos[0];

        for (int i=0; i<=totalAlunos-1; i++){
            if(nomeAlunos[0] != null){
                notaAvg += notasAlunos[i];}

            if(notasAlunos[i] > notaMax){
                notaMax = notasAlunos[i];
            }

            if(notasAlunos[i] < notaMin ){
                notaMin = notasAlunos[i];
           }
        }

        notaAvg = notaAvg / totalAlunos;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar um resumo da avaliação;
     * Nota máxima, Nota mínima, Nota média. Número de alunos com nota superior a média e número de alunos com
     * nota inferior a média.
     * a mensagem "Não há dados"
     */
    public static void mostraResumo() {
        int notaAcima = 0;
        int totalAlunos = 0;

        for (String nomeAluno : nomeAlunos) {
            if(nomeAluno != null)
                totalAlunos++;
        }

        if(nomeAlunos[0] != null){
            calcularMaxMinAvg();

            System.out.println("Total de alunos: " + totalAlunos);
            System.out.println("Nota media: " + notaAvg);
            System.out.println("Nota máx: " + notaMax);
            System.out.println("Nota min: " + notaMin);

            for (int notasAluno : notasAlunos) {
                if (notasAluno > notaAvg) {
                   notaAcima++;
                }
            }
            System.out.println("Notas acima da média: " + notaAcima );
            System.out.println("Notas abaixo da média: " + (totalAlunos-notaAcima) );

        } else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar o nome dos três alunos que têm as melhores notas.
     */

    public static void mostrarTop() {
        int top = 0;
        int totalAlunos=0;

        for (String nomeAluno : nomeAlunos) {
            if(nomeAluno != null)
                totalAlunos++;
        }

        if (nomeAlunos[0] != null) {
            calcularMaxMinAvg();

            System.out.println("Nome Estudante       Nota");

            for (int n=0;n<3;n++){
                for (int i = 0; i < totalAlunos; i++) {
                    if (notasAlunos[i] == notaMax-n && top < 3) {
                        System.out.printf("%-20s %d\n",nomeAlunos[i],notasAlunos[i]);
                        top++;
                    }
                }
            }
        }else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }
    /**
     * Método a ser implementando no TP1.
     * Apresentar a pauta com nomes dos alunos e á frente cada nome a respectiva nota obtida.
     */
    public static void mostrarPauta() {
        int codigoAluno = 1;
        int totalAlunos = 0;
        int pagina = 10;
        int i = 0;

        for (String nomeAluno : nomeAlunos) {
            if(nomeAluno != null)
                totalAlunos++;
        }

        if (nomeAlunos[0] != null) {
            loop:
            do {
                System.out.println("Codigo Nome de Estudante   Nota");
                do {
                    if (i == totalAlunos || nomeAlunos[i] == null){
                            break loop;
                        }
                            final DecimalFormat decimal = new DecimalFormat("000000");
                            System.out.print(decimal.format(codigoAluno++) + " ");
                            System.out.printf("%-20s",nomeAlunos[i]);
                            System.out.println(notasAlunos[i]);
                            i++;
                } while (i < pagina);

                interC.showMsg("\nEnter para continuar...");
                pagina = pagina + 10;

            } while(i<totalAlunos);
        }else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }
    /**
     * Método a ser implementando no TP1
     * Apresentar \para um aluno específico em que o nome é dado como parâmetro a nota de avaliação
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a sua nota
     * @return
     */
    public static void mostrarDetalhesAluno(String nome) {
        interC.showMsg("A ser implementado ...");

    }
    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(String nome){
        int i=0;

        if(nomeAlunos[0] != null){
            System.out.println("--> Resultado de pesquisa de " + '"' + nome + '"');
            System.out.println("Nome Estudante      Nota");

            for (String nomeAluno : nomeAlunos){
                if (nomeAluno != null){

                    if ((nomeAluno.toLowerCase()).contains(nome.toLowerCase())){
                        System.out.printf("%-20s", nomeAluno);
                        System.out.println(notasAlunos[i]);
                    }
                }
                i++;
            }
        } else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(int nota) {
        int totalAlunos =0;

        for (String nomeAluno : nomeAlunos) {
            if(nomeAluno != null)
                totalAlunos++;
        }

        if(nomeAlunos[0] != null){
            System.out.println("Nome Estudante       Nota");

            for (int i = 0; i < totalAlunos; i++) {
                if (nomeAlunos[i] != null) {
                    if (nota == notasAlunos[i]) {
                        System.out.printf("%-20s %d\n", nomeAlunos[i], notasAlunos[i]);
                    }
                }
            }
        }else
            System.out.println("Não há dados");

        interC.showMsg("\nEnter para continuar...");
    }

    private String[] searchByName(String nome) {
        return null;
    }

}
