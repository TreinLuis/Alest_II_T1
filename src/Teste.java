import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Teste {
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

    public static int buscarMelhorCaminho(char[][] matriz, int linha, int coluna, int somaAtual) {
        // Verificar se estamos fora dos limites da matriz
        if (linha < 0 || linha >= matriz.length || coluna < 0 || coluna >= matriz[0].length) {
            return somaAtual;  // Retorna a soma atual se sair dos limites da matriz
        }

        char atual = matriz[linha][coluna];
        System.out.println("Posição [" + linha + ", " + coluna + "] = " + atual);

        // Se for '#', é o final de um ramo, retornar a soma acumulada
        if (atual == '#') {
            return somaAtual;
        }

        // Se for um número, acumular o valor na soma atual
        if (Character.isDigit(atual)) {
            somaAtual += Character.getNumericValue(atual);
            System.out.println("Soma acumulada: " + somaAtual);
        }

        int melhorSoma = somaAtual;

        // Verificar o tipo de bifurcação ou caminho
        switch (atual) {
            case 'V':
                System.out.println("Bifurcação (V) encontrada");
                int somaEsquerda = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual);  // Caminho à esquerda
                int somaDireita = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual);   // Caminho à direita
                melhorSoma = Math.max(somaEsquerda, somaDireita);
                break;

            case 'W':
                System.out.println("Trifurcação (W) encontrada");
                int somaEsquerdaW = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual);  // Caminho à esquerda
                int somaDireitaW = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual);   // Caminho à direita
                int somaMeioW = buscarMelhorCaminho(matriz, linha - 1, coluna, somaAtual);          // Caminho ao meio
                melhorSoma = Math.max(somaEsquerdaW, Math.max(somaDireitaW, somaMeioW));
                break;

            case '\\':
                System.out.println("Caminho (\\) encontrado");
                int somaBaixoEsquerda = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual);  // Caminho para baixo e esquerda
                melhorSoma = somaBaixoEsquerda;
                break;

            case '/':
                System.out.println("Caminho (/) encontrado");
                int somaBaixoDireita = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual);   // Caminho para baixo e direita
                melhorSoma = somaBaixoDireita;
                break;

            case '|':
                System.out.println("Caminho (|) encontrado");
                int somaAbaixo = buscarMelhorCaminho(matriz, linha - 1, coluna, somaAtual);          // Caminho apenas para baixo
                melhorSoma = somaAbaixo;
                break;

            default:
                // Se não for um caractere de bifurcação ou caminho, continuar na mesma direção (baixo)
                melhorSoma = buscarMelhorCaminho(matriz, linha - 1, coluna, somaAtual);
                break;
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
                    return valorRetorno;
                }
            }
        }
        return null;
    }
}
//ANOTACOES
//QUANDO EU ACHO UM VALOR ELE SE PERDE E PARA DE PERCORRER DA MANEIRA CORRETA
//AGORA PRECISAMOS IMPLEMENTAR UMA LÓGICA QUE ELE LEIA NUMERO E CONTINUE NO CAMINHAMENTO CORRETO