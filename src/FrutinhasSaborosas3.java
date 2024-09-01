import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FrutinhasSaborosas3 {

    static class Node {
        int value;
        List<Node> children;

        Node(int value) {
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

        char[][] treeMatrix = convertLinesToMatrix(lines);

        System.out.println("Estrutura da árvore:");

        System.out.println("\nCaminhos e somas:");
    }

    private static char[][] convertLinesToMatrix(List<String> lines) {
        int numRows = lines.size();
        int numCols = lines.stream().mapToInt(String::length).max().orElse(0);

        char[][] treeMatrix = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                treeMatrix[i][j] = line.charAt(j);
                System.out.println(treeMatrix[i][j]);
            }
        }

        System.out.println("Matriz da árvore:");
        for (char[] row : treeMatrix) {
            System.out.println(Arrays.toString(row));
        }

        return treeMatrix;
    }

    private static Node processarArvore(char[][] treeMatrix) {
        int numRows = treeMatrix.length;
        int numCols = treeMatrix[0].length;
        Node[][] nodes = new Node[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                char current = treeMatrix[i][j];
                if (Character.isDigit(current)) {
                    nodes[i][j] = new Node(Character.getNumericValue(current));
                }
            }
        }

        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = 0; j < numCols; j++) {
                Node currentNode = nodes[i][j];
                if (currentNode != null) {
                    if (i + 1 < numRows) {
                        if (j < numCols && nodes[i + 1][j] != null) {
                            currentNode.children.add(nodes[i + 1][j]);
                        }
                        if (j + 1 < numCols && treeMatrix[i + 1][j + 1] == '/') {
                            if (nodes[i + 1][j + 1] != null) {
                                currentNode.children.add(nodes[i + 1][j + 1]);
                            }
                        }
                        if (j - 1 >= 0 && treeMatrix[i + 1][j - 1] == '\\') {
                            if (nodes[i + 1][j - 1] != null) {
                                currentNode.children.add(nodes[i + 1][j - 1]);
                            }
                        }
                    }
                }
            }
        }

        Node root = null;
        for (int j = 0; j < numCols; j++) {
            if (nodes[0][j] != null) {
                root = nodes[0][j];
                break;
            }
        }
        return root;
    }
}
