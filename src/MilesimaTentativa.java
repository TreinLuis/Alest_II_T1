import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MilesimaTentativa {
    public static void main(String[] args) {
        char[][] arvoreLida = convertLinesToMatrix(leArvore());
        imprimeArvore(arvoreLida);
        int[] coordenadasRoot = leRoot(arvoreLida);
        System.out.println(buscarMelhorCaminhoIterativo(arvoreLida, coordenadasRoot[0], coordenadasRoot[1]) + "aaaaaaa");
    }

    public static int buscarMelhorCaminhoIterativo(char[][] matriz, int linhaInicial, int colunaInicial) {
        Stack<Estado> pilha = new Stack<>();
        pilha.push(new Estado(linhaInicial, colunaInicial, 0));

        int melhorSoma = Integer.MIN_VALUE;

        while (!pilha.isEmpty()) {
            Estado estadoAtual = pilha.pop();
            int linha = estadoAtual.linha;
            int coluna = estadoAtual.coluna;
            int somaAtual = estadoAtual.somaAtual;

            // Verificar se estamos fora dos limites da matriz
            if (linha < 0 || linha >= matriz.length || coluna < 0 || coluna >= matriz[0].length) {
                System.out.println("Saindo dos limites: [" + linha + ", " + coluna + "] - Soma atual: " + somaAtual);
                continue;  // Continua para o próximo estado na pilha
            }

            char atual = matriz[linha][coluna];
            System.out.println("Posição atual [" + linha + ", " + coluna + "] = " + atual);

            // Se for '#', é o final de um ramo, atualizar a melhor soma
            if (atual == '#') {
                System.out.println("Chegou ao final de um ramo: [" + linha + ", " + coluna + "] - Soma final: " + somaAtual);
                melhorSoma = Math.max(melhorSoma, somaAtual);
                continue;  // Continua para o próximo estado na pilha
            }

            // Se for um número, acumular o valor na soma atual
            if (Character.isDigit(atual)) {
                somaAtual += Character.getNumericValue(atual);
                System.out.println("Número encontrado: " + atual + " - Soma acumulada: " + somaAtual);
            }

            // Verificar o tipo de bifurcação ou caminho
            switch (atual) {
                case 'V':
                    System.out.println("Bifurcação (V) encontrada na posição [" + linha + ", " + coluna + "]");
                    // Bifurcação (V) encontrada
                    // Adiciona ambos os caminhos à pilha: à esquerda e à direita
                    pilha.push(new Estado(linha - 1, coluna - 1, somaAtual));  // Caminho à esquerda
                    pilha.push(new Estado(linha - 1, coluna + 1, somaAtual));  // Caminho à direita
                    break;

                case 'W':
                    System.out.println("Trifurcação (W) encontrada na posição [" + linha + ", " + coluna + "]");
                    // Trifurcação (W) encontrada
                    // Adiciona os três caminhos à pilha: à esquerda, à direita e ao meio
                    pilha.push(new Estado(linha - 1, coluna - 1, somaAtual));  // Caminho à esquerda
                    pilha.push(new Estado(linha - 1, coluna + 1, somaAtual));  // Caminho à direita
                    pilha.push(new Estado(linha - 1, coluna, somaAtual));      // Caminho ao meio
                    break;

                case '\\':
                    System.out.println("Caminho (\\) encontrado na posição [" + linha + ", " + coluna + "]");
                    // Caminho (\) encontrado
                    // Adiciona o caminho para baixo e à esquerda
                    pilha.push(new Estado(linha - 1, coluna - 1, somaAtual));
                    break;

                case '/':
                    System.out.println("Caminho (/) encontrado na posição [" + linha + ", " + coluna + "]");
                    // Caminho (/) encontrado
                    // Adiciona o caminho para baixo e à direita
                    pilha.push(new Estado(linha - 1, coluna + 1, somaAtual));
                    break;

                case '|':
                    System.out.println("Caminho (|) encontrado na posição [" + linha + ", " + coluna + "]");
                    // Caminho (|) encontrado
                    // Adiciona o caminho apenas para baixo
                    pilha.push(new Estado(linha - 1, coluna, somaAtual));
                    break;

                default:
                    // Se não for um caractere de bifurcação ou caminho, continuar na mesma direção (baixo)
                    System.out.println("Caminho padrão encontrado na posição [" + linha + ", " + coluna + "]");
                    pilha.push(new Estado(linha - 1, coluna, somaAtual));
                    break;
            }
        }

        return melhorSoma;
    }

    private static class Estado {
        int linha;
        int coluna;
        int somaAtual;

        Estado(int linha, int coluna, int somaAtual) {
            this.linha = linha;
            this.coluna = coluna;
            this.somaAtual = somaAtual;
        }
    }

    private static char[][] convertLinesToMatrix(List<String> lines) {
        int numeroLinhas = lines.size();
        int numeroColunas = lines.stream().mapToInt(String::length).max().orElse(0);

        char[][] treeMatrix = new char[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                treeMatrix[i][j] = line.charAt(j);
            }
        }

        return treeMatrix;
    }

    public static void imprimeArvore(char[][] arvoreMatriz) {
        System.out.println("Matriz da árvore:");
        for (char[] row : arvoreMatriz) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static List<String> leArvore() {
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

    public static int[] leRoot(char[][] arvoreLida) {
        int numRows = arvoreLida.length;
        int numCols = arvoreLida[0].length;
        int[] valorRetorno = new int[2];
        for (int i = numRows - 1; i > 0; i--) {
            for (int j = 0; j < numCols; j++) {
                String valor = String.valueOf(arvoreLida[i][j]);
                if (!valor.isBlank()) {
                    valorRetorno[0] = i;
                    valorRetorno[1] = j;
                    return valorRetorno;
                }
            }
        }
        return null;
    }
}
