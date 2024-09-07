import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Frutinhas5 {
    public static void main(String[] args) {

        //Uma funcao para ler | Pegar e fazer outra funcao que imprime a matrix | aí depois uma funcao gera matriz pra leitura
        char[][] arvoreLida = convertLinesToMatrix(leArvore());
        imprimeArvore(arvoreLida);
        //buscarMelhorCaminho(arvoreLida,0,0,0);
        int[] coordenadasRoot = new int[2];
        coordenadasRoot = leRoot(arvoreLida);
        //System.out.println(buscarMelhorCaminho(arvoreLida,0,0,0));
        System.out.println(buscarMelhorCaminho(arvoreLida,coordenadasRoot[0],coordenadasRoot[1],0)+ "aaaaaaa");

    }

    public static int buscarMelhorCaminho(char[][] matriz,int linha,int coluna, int somaAtual) {//devemos passar nossa matrix e a coordenada do nosso root
        // Verificar se estamos fora dos limites da matriz
//        if (linha < 0 || linha >= matriz.length || coluna < 0 || coluna >= matriz[0].length) {
//            System.out.println("gay");
//            return 0;
//        }
        //System.out.println(linha);
        char atual = matriz[linha][coluna];
        System.out.println(atual);

        // Se for '#', é o final de um ramo, retornar a soma acumulada
        if (atual == '#') {
            return somaAtual;
        }

        // Se for um número, acumular o valor na soma atual
        if (Character.isDigit(atual)) {
            somaAtual += Character.getNumericValue(atual);
           // System.out.println(somaAtual);
        }

        int melhorSoma = 0;

        // Se for uma bifurcação (V), explorar dois caminhos
        if (atual == 'V') {
            int somaEsquerda= 0;
            int somaDireita = 0;
            // Explorar os dois caminhos possíveis (esquerda e direita)
            if(atual == '\\'){
                somaEsquerda = buscarMelhorCaminho(matriz,linha++,coluna--,somaAtual); //Aqui estamos movimentando e percorrendo ela para a esquerda
            } else if(atual == '/'){
                somaDireita = buscarMelhorCaminho(matriz, linha++, coluna + 1, somaAtual);
            }
            melhorSoma = Math.max(somaEsquerda, somaDireita);

            // Se for uma trifurcação (W), explorar três caminhos
        } else if (atual == 'W') {
            // Explorar três caminhos possíveis (baixo, direita e diagonal)
            int somaEsquerda= 0;
            int somaDireita = 0;
            int somaMeio = 0;
            if(atual == '\\'){
                somaEsquerda = buscarMelhorCaminho(matriz,linha++,coluna--,somaAtual); //Aqui estamos movimentando e percorrendo ela para a esquerda
            } else if(atual == '/'){
                somaDireita = buscarMelhorCaminho(matriz, linha++, coluna + 1, somaAtual);
            } else if(atual == '|'){
                somaMeio = buscarMelhorCaminho(matriz,linha++,coluna,somaAtual);
            }
            melhorSoma = Math.max(somaEsquerda, Math.max(somaDireita, somaMeio));

        } else {
            // Se não for bifurcação nem trifurcação, continuar na mesma direção (baixo)
            melhorSoma = buscarMelhorCaminho(matriz, linha + 1, coluna, somaAtual);
        }

        return melhorSoma;
    }

    private static char[][] convertLinesToMatrix(List<String> lines) {
        int numeroLinhas = lines.size();
        int numeroColunas = lines.stream().mapToInt(String::length).max().orElse(0);

        char[][] treeMatrix = new char[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                treeMatrix[i][j] = line.charAt(j);
                //System.out.println(treeMatrix[i][j]);
            }
        }

        return treeMatrix;
    }
    public static void imprimeArvore(char[][] arvoreMatriz){
        System.out.println("Matriz da árvore:");
        for (char[] row : arvoreMatriz) {
            System.out.println(Arrays.toString(row));
        }
    }
    public static List<String> leArvore(){
        String fileName = "casoc30.txt";
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {

                lines.add(line);
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado: " + fileName, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public static int[] leRoot(char[][] arvoreLida){//ACHAR O ROOT INVERTENDO FAZENDO UM FOR DO CONTRARIO OK
        //Fiz o metodo que retorna um vetor para podermos separar em linha e coluna e usar no nosso metodo de buscar melhor caminho
        int numRows = arvoreLida.length;
        int numCols = arvoreLida[0].length;
        int[] valorRetorno = new int[2];
        for(int i=numRows-1; i > 0 ;i--){
            //este for começa a processar a arvore de baixo para cima
            for(int j = 0; j < numCols; j++){
                //aqui fica normal pois é da esquerda pra direita
                String valor = String.valueOf(arvoreLida[i][j]);
                if(!valor.isBlank()){
                    valorRetorno[0] = i;//LINHA
                    valorRetorno[1] = j;//COLUNA
                    System.out.println(valor);
                }
            }
        }
        return valorRetorno;
    }
}
