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
        System.out.println(buscarMelhorCaminho(arvoreLida,coordenadasRoot[0],coordenadasRoot[1],0, " ")+ "aaaaaaa");

    }

    public static int buscarMelhorCaminho(char[][] matriz, int linha, int coluna, int somaAtual,String percorrer) {
        // Verificar se estamos fora dos limites da matriz
        if (linha < 0 || linha >= matriz.length || coluna < 0 || coluna >= matriz[0].length) {
            System.out.println("Saindo dos limites: [" + linha + ", " + coluna + "] - Soma atual: " + somaAtual);
            return somaAtual;  // Retorna a soma atual se sair dos limites da matriz
        }

        char atual = matriz[linha][coluna];
        System.out.println("Posição atual [" + linha + ", " + coluna + "] = " + atual);

        // Se for '#', é o final de um ramo, retornar a soma acumulada
        if (atual == '#') {
            System.out.println("Chegou ao final de um ramo: [" + linha + ", " + coluna + "] - Soma final: " + somaAtual);
            return somaAtual;
        }

        // Se for um número, acumular o valor na soma atual
        if (Character.isDigit(atual)) {

            somaAtual += Character.getNumericValue(atual);
            if(percorrer.equals("esquerda")) {
                int somaEsquerda = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual,"esquerda");  // Caminho à esquerda
            } else if(percorrer.equals("direita")){
                int direita = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual,"direita");  // Caminho à esquerda
            } else if(percorrer.equals("meio")){
                int meio = buscarMelhorCaminho(matriz, linha - 1, coluna , somaAtual,"meio");  // Caminho à esquerda
            }
            System.out.println("Número encontrado: " + atual + " - Soma acumulada: " + somaAtual);
        }

        int melhorSoma = Integer.MIN_VALUE;  // Inicializa com um valor baixo para maximização

        // Verificar o tipo de bifurcação ou caminho
        switch (atual) {
            case 'V':
                System.out.println("Bifurcação (V) encontrada na posição [" + linha + ", " + coluna + "]");
                // Bifurcação (V) encontrada
                // Explora ambos os caminhos: à esquerda e à direita
                int somaEsquerda = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual,"esquerda");  // Caminho à esquerda
                int somaDireita = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual,"direita");   // Caminho à direita
                melhorSoma = Math.max(somaEsquerda, somaDireita);
                //System.out.println("Melhor soma após bifurcação (V): " + melhorSoma);
                break;

            case 'W':
                System.out.println("Trifurcação (W) encontrada na posição [" + linha + ", " + coluna + "]");
                // Trifurcação (W) encontrada
                // Explora os três caminhos: à esquerda, à direita e ao meio
                int somaEsquerdaW = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual,"esquerda");  // Caminho à esquerda
                int somaDireitaW = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual,"direita");   // Caminho à direita
                int somaMeioW = buscarMelhorCaminho(matriz, linha - 1, coluna, somaAtual,"meio");          // Caminho ao meio
                melhorSoma = Math.max(somaEsquerdaW, Math.max(somaDireitaW, somaMeioW));
                System.out.println("Melhor soma após trifurcação (W): " + melhorSoma);
                break;

            case '\\':
                System.out.println("Caminho (\\) encontrado na posição [" + linha + ", " + coluna + "]");
                // Caminho (\) encontrado
                // Explora o caminho para baixo e à esquerda
                melhorSoma = buscarMelhorCaminho(matriz, linha - 1, coluna - 1, somaAtual,"esquerda");//PRECISO VERIFICAR ISSO DEPOIS
                System.out.println("Melhor soma após caminho (\\): " + melhorSoma);
                break;

            case '/':
                System.out.println("Caminho (/) encontrado na posição [" + linha + ", " + coluna + "]");
                // Caminho (/) encontrado
                // Explora o caminho para baixo e à direita
                melhorSoma = buscarMelhorCaminho(matriz, linha - 1, coluna + 1, somaAtual,"direita");//Não sei oque fazer ainda
                System.out.println("Melhor soma após caminho (/): " + melhorSoma);
                break;

            case '|':
                System.out.println("Caminho (|) encontrado na posição [" + linha + ", " + coluna + "]");
                // Caminho (|) encontrado
                // Explora o caminho apenas para baixo
                melhorSoma = buscarMelhorCaminho(matriz, linha - 1, coluna, somaAtual,"meio");
                System.out.println("Melhor soma após caminho (|): " + melhorSoma);
                break;

            default:
                // Se não for um caractere de bifurcação ou caminho, continuar na mesma direção (baixo)
                System.out.println("Caminho padrão encontrado na posição [" + linha + ", " + coluna + "]");
                System.out.println("gay");
                //melhorSoma = buscarMelhorCaminho(matriz, linha - 1, coluna, somaAtual,"esquerda");
                System.out.println("Melhor soma após caminho padrão: " + melhorSoma);
                break;
        }

        return melhorSoma;
    }

//meu default ta fudendo o negocio, mas tamo evoluindo



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