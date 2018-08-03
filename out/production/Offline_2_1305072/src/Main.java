import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException{
        File file = new File("1305072_testcase.txt");
        Scanner input = new Scanner(file);
        int T = input.nextInt();

        for(int t=0;t<T;t++){
            int N,M;
            N = input.nextInt();
            M = input.nextInt();

            int[] subset_size = new int[M];
            double[] weight = new double[M];
            ArrayList<int[]> subsets = new ArrayList<>();

            for (int m=0;m<M;m++) {
                weight[m] = input.nextDouble();
                subset_size[m] = input.nextInt();
                int [] subset = new int[subset_size[m]];
                for (int k=0;k<subset_size[m];k++) {
                    subset[k] = input.nextInt();
                }
                subsets.add(subset);
            }
            System.out.println("------  Test Case: "+ (t+1) + "  -------");
            double time = System.nanoTime();
            LinearProgramming lp = new LinearProgramming(N, M, subsets, weight, subset_size);
            lp.printResult();
            System.out.println("runtime of LP : " + (System.nanoTime()-time)/1000000);

            System.out.println();

            time = System.nanoTime();
            BitmaskDP bit = new BitmaskDP(N, M, subsets, weight, subset_size);
            bit.printResult();
            System.out.println("runtime of DP : " + (System.nanoTime()-time)/1000000);

            System.out.println();
        }

        input.close();
    }
}
