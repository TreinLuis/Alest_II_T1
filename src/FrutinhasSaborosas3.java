import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FrutinhasSaborosas3 {

    static class Node {
        String value;

        List<Node> children;

//        Node(int value) {
//            this.value = value;
//            this.children = new ArrayList<>();
//        }
        Node(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
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
        //Uma funcao para ler | Pegar e fazer outra funcao que imprime a matrix | aí depois uma funcao gera matriz pra leitura
        char[][] arvoreLida = convertLinesToMatrix(lines);
        processarArvore(arvoreLida);

//        System.out.println("Estrutura da árvore:");
//
//        System.out.println("\nCaminhos e somas:");
        //System.out.println(treeMatrix);
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
        System.out.println("Matriz da árvore:");
        for (char[] row : treeMatrix) {
            System.out.println(Arrays.toString(row));
        }
        return treeMatrix;
    }

//    private static Node processarArvore(char[][] treeMatrix) {
//        int numRows = treeMatrix.length;
//        int numCols = treeMatrix[0].length;
//        Node[][] nodes = new Node[numRows][numCols];
//
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                char current = treeMatrix[i][j];
//                if (Character.isDigit(current)) {
//                    nodes[i][j] = new Node(Character.getNumericValue(current));
//                }
//            }
//        }
//        for (int i = numRows - 1; i >= 0; i--) {
//            for (int j = 0; j < numCols; j++) {
//                Node currentNode = nodes[i][j];
//                if (currentNode != null) {
//                    if (i + 1 < numRows) {
//                        if (j < numCols && nodes[i + 1][j] != null) {
//                            currentNode.children.add(nodes[i + 1][j]);
//                        }
//                        if (j + 1 < numCols && treeMatrix[i + 1][j + 1] == '/') {
//                            if (nodes[i + 1][j + 1] != null) {
//                                currentNode.children.add(nodes[i + 1][j + 1]);
//                            }
//                        }
//                        if (j - 1 >= 0 && treeMatrix[i + 1][j - 1] == '\\') {
//                            if (nodes[i + 1][j - 1] != null) {
//                                currentNode.children.add(nodes[i + 1][j - 1]);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        Node root = null;
//        for (int j = 0; j < numCols; j++) {
//            if (nodes[0][j] != null) {
//                root = nodes[0][j];
//                break;
//            }
//        }
//        return root;
//    }

    public static void processarArvore(char[][] arvore){
        int numRows = arvore.length;
        int numCols = arvore[0].length;
        Node[][] nodes = new Node[numRows][numCols];
        //System.out.println(numRows);

        for(int i=numRows-1; i > 0 ;i--){
            //este for começa a processar a arvore de baixo para cima
            for(int j = 0; j < numCols; j++){//aqui fica normal pois é da esquerda pra direita
                String valor = String.valueOf(arvore[i][j]);
                    nodes[i][j] = new Node(valor);
            }
        }
        if(!nodes[30][14].value.equalsIgnoreCase(" ")){//Consegui fazer meu programa ignorar os espaços em branco
            System.out.println("gay");
        }
        System.out.println(nodes[30][14].value);
    }
}
