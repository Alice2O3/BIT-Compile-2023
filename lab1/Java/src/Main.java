import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Integer> parse_line(String str){
        List<Integer> output = new ArrayList<>();
        String[] elements = str.split(" ");
        for (String element : elements) {
            output.add(Integer.parseInt(element));
        }
        return output;
    }

    public static int[][] initializeArray(int n, int m) {
        int[][] array = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                array[i][j] = 0;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        String fileName = "../../data.txt";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<Integer> line = parse_line(bufferedReader.readLine());
            int n = line.get(0), m = line.get(1);

            int[][] Ma = initializeArray(n, m);

            for(int i = 0; i < n; i++){
                line = parse_line(bufferedReader.readLine());
                for(int j = 0; j < m; j++){
                    Ma[i][j] = line.get(j);
                }
            }

            line = parse_line(bufferedReader.readLine());
            int m2 = line.get(0), t = line.get(1);

            int[][] Mb = initializeArray(m2, t);

            for(int i = 0; i < m2; i++){
                line = parse_line(bufferedReader.readLine());
                for(int j = 0; j < t; j++){
                    Mb[i][j] = line.get(j);
                }
            }

            int[][] Mc = initializeArray(n, t);
            for(int i = 0; i < n; i++) {
                for (int j = 0; j < t; j++) {
                    for (int k = 0; k < m; k++) {
                        Mc[i][j] += Ma[i][k] * Mb[k][j];
                    }
                }
            }

            bufferedReader.close();
        }
        catch(Exception e) {
            System.out.println("Error reading file!");
            e.printStackTrace();
        }
    }
}
