import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
public class Frutinhas4 {
    public static void main(String[] args){
        int linha = 0;
        int coluna = 0;
        int total = 0;
        int num_colunas, num_linhas;
        char atual, direcao = 'L';
        String soma = "0";

        Scanner entrada = null;

        try {
            BufferedReader streamEntrada = new BufferedReader(new FileReader("casoc30.txt"));
            entrada = new Scanner(streamEntrada);
        } catch (Exception e) {
            System.out.println(e);
        }

        String[] a = entrada.nextLine().split(" ");
        num_linhas = Integer.parseInt(a[0]);
        num_colunas = Integer.parseInt(a[1]);

        char[][] matriz = new char[num_linhas][num_colunas];

        for(int i=0; i<num_linhas; i++) {
            matriz[i] = entrada.nextLine().toCharArray();
            System.out.println(matriz[i]);
        }

        atual = 'a';

        while(atual != '#') {
            linha++;
            atual = matriz[linha-1][0];//Aqui ele ta na raiz dele o começo dele/ mas o nosso é variavel
            System.out.println(atual);
        }


        while (atual != '#') {
            switch(atual) {
                case 'v':
                    if (direcao == '|') {direcao = 'L';}
                    else if (direcao == 'S') {direcao = 'O';}
                    else if (direcao == 'L') {direcao = 'N';}
                    else if (direcao == 'O') {direcao = 'S';}
                    break;
                case 'w':
                    if (direcao == 'N') {direcao = 'O';}
                    else if (direcao == 'S') {direcao = 'L';}
                    else if (direcao == 'L') {direcao = 'S';}
                    else if (direcao == 'O') {direcao = 'N';}
                    break;


                default:
            }

            int num = Character.getNumericValue(atual);
            if (num > -1) {
                soma = soma + atual;
            } else if (!(soma.equals("0"))) {
                total += Integer.parseInt(soma);
                soma = "0";
            }

            switch(direcao) {
                case 'N':
                    linha--;
                    break;
                case 'S':
                    linha++;
                    break;
                case 'L':
                    coluna++;
                    break;
                case 'O':
                    coluna--;
                    break;
            }

            atual = matriz[linha][coluna];
            System.out.println(matriz[linha][coluna]);
        }

        System.out.println(total);
    }
}