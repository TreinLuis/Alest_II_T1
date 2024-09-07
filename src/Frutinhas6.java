import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Frutinhas6 {
    public static void main(String[] args) {
        // Lê a árvore do arquivo, converte para matriz de strings e imprime
        String[][] arvoreLida = convertLinesToMatrix(leArvore());
        imprimeArvore(arvoreLida);
        buscarMelhorCaminho(arvoreLida,30,14,0);
    }

    // Função para converter as linhas lidas em uma matriz de strings
    private static String[][] convertLinesToMatrix(List<String> lines) {
        int numeroLinhas = lines.size();
        int numeroColunas = lines.stream().mapToInt(String::length).max().orElse(0); // Calcula o maior comprimento de linha

        String[][] treeMatrix = new String[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++) {
            String line = lines.get(i);
            for (int j = 0; j < numeroColunas; j++) {
                // Preenche a matriz com o caractere da linha como string ou um espaço se a linha for menor que o máximo
                treeMatrix[i][j] = j < line.length() ? String.valueOf(line.charAt(j)) : " ";
            }
        }

        return treeMatrix;
    }

    // Função para imprimir a matriz da árvore
    public static void imprimeArvore(String[][] arvoreMatriz) {
        System.out.println("Matriz da árvore:");
        for (String[] row : arvoreMatriz) {
            for (String s : row) {
                System.out.print(s);  // Imprimir sem vírgulas ou colchetes
            }
            System.out.println();  // Quebra de linha após cada linha da matriz
        }
    }

    // Função para ler a árvore do arquivo
    public static List<String> leArvore() {
        String fileName = "casoc30.txt";  // Nome do arquivo com a árvore
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado: " + fileName, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public static int buscarMelhorCaminho(String[][] matriz, int linha, int coluna, int somaAtual) {//devemos passar nossa matrix e a coordenada do nosso root
        // Verificar se estamos fora dos limites da matriz
//        if (linha < 0 || linha >= matriz.length || coluna < 0 || coluna >= matriz[0].length) {
//            System.out.println("gay");
//            return 0;
//        }
        //System.out.println(linha);
        String atual = matriz[linha][coluna];

        // Se for '#', é o final de um ramo, retornar a soma acumulada
        if (atual.equalsIgnoreCase("#")) {
            return somaAtual;
        }

        // Se for um número, acumular o valor na soma atual
        if (atual.matches("\\d")) {  // Verifica se a string contém um único dígito
            somaAtual += Integer.parseInt(atual);  // Converte a string para um número inteiro
            System.out.println(somaAtual);
        }


        int melhorSoma = 0;

        // Se for uma bifurcação (V), explorar dois caminhos
        if (atual.equalsIgnoreCase("V")) {
            int somaEsquerda= 0;
            int somaDireita = 0;
            // Explorar os dois caminhos possíveis (esquerda e direita)
            if(atual.equalsIgnoreCase("\\")){
                somaEsquerda = buscarMelhorCaminho(matriz,linha++,coluna--,somaAtual); //Aqui estamos movimentando e percorrendo ela para a esquerda
            } else if(atual.equalsIgnoreCase("/")){
                somaDireita = buscarMelhorCaminho(matriz, linha++, coluna + 1, somaAtual);
            }
            melhorSoma = Math.max(somaEsquerda, somaDireita);

            // Se for uma trifurcação (W), explorar três caminhos
        } else if (atual.equalsIgnoreCase("W")) {
            // Explorar três caminhos possíveis (baixo, direita e diagonal)
            int somaEsquerda= 0;
            int somaDireita = 0;
            int somaMeio = 0;
            if(atual.equalsIgnoreCase("\\")){
                somaEsquerda = buscarMelhorCaminho(matriz,linha++,coluna--,somaAtual); //Aqui estamos movimentando e percorrendo ela para a esquerda
            } else if(atual.equalsIgnoreCase("/")){
                somaDireita = buscarMelhorCaminho(matriz, linha++, coluna + 1, somaAtual);
            } else if(atual.equalsIgnoreCase("|")){
                somaMeio = buscarMelhorCaminho(matriz,linha++,coluna,somaAtual);
            }
            melhorSoma = Math.max(somaEsquerda, Math.max(somaDireita, somaMeio));

        } else {
            // Se não for bifurcação nem trifurcação, continuar na mesma direção (baixo)
            melhorSoma = buscarMelhorCaminho(matriz, linha + 1, coluna, somaAtual);
        }

        return melhorSoma;
    }
}
