import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FrutinhasSaborosas {

    public static void main(String[] args) {
        String fileName = "casoc30.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                doTheExercise(line);
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doTheExercise(String treeData) {

//        String[] rows = treeData.split("");
//        int numRows = rows.length;
//        int numCols = rows[0].length();
//
//        int[][] tree = new int[numRows][numCols];
//
//        for (int i = 0; i < numRows; i++) {
//            for (int j = 0; j < numCols; j++) {
//                tree[i][j] = Character.getNumericValue(rows[i].charAt(j));
//            }
//        }
//
//        System.out.println("Matriz da árvore:");
//        for (int[] row : tree) {
//            for (int value : row) {
//                System.out.print(value + " ");
//            }
//            System.out.println();
//        }
    }
}
