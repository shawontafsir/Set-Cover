import java.util.ArrayList;

public class BitmaskDP {
    private static final int INF = 99999999;
    int N;
    int M;
    double[] weight;
    int[] subset_size;
    ArrayList<int[]> subsets;

    double[][] dp;
    boolean[][] isIn;
    int[] bitmask_of_subsets;
    int resultantSets;

    public BitmaskDP(int N, int M, ArrayList<int[]> subsets, double[] weight, int[] subset_size){
        this.N = N;
        this.M = M;
        this.subsets = subsets;
        this.weight = weight;
        this.subset_size = subset_size;

        resultantSets = 0;

        dp = new double[M][(int) (Math.pow(2, N))];
        isIn = new boolean[M][(int) (Math.pow(2, N))];
        for (int m=0;m<M;m++) {
            for (int j=0;j<(Math.pow(2, N));j++) {
                dp[m][j] = -1;
                isIn[m][j] = false;
            }
        }

        bitmask_of_subsets = new int[M];
        for (int m=0;m<M;m++) {
            int[] subset = subsets.get(m);
            int temp_mask = 0;
            for (int j=0;j<subset_size[m];j++) {
                temp_mask |= 1<<subset[j];
            }
            bitmask_of_subsets[m] = temp_mask;
        }
    }

    double setCover(int current, int current_mask) {
        if (current == M && current_mask != (1<<N)-1) return INF;
        else if (current == M && current_mask == (1<<N)-1) return 0;
        else {
            double chooseCurrent = setCover(current+1, current_mask | bitmask_of_subsets[current]) + weight[current];
            double notChooseCurrent = setCover(current+1, current_mask);
            double ans;
            if (chooseCurrent < notChooseCurrent) {
                ans = chooseCurrent;
                isIn[current][current_mask] = true;
            }
            else {
                ans = notChooseCurrent ;
                isIn[current][current_mask] = false;
            }
            dp[current][current_mask] = ans;
            return ans;
        }
    }

    void resultant_sets() {
        int current_mask = 0;
        for(int m=0;m<M;m++){
            if (isIn[m][current_mask] == true) {
                resultantSets |= (1<<m);
                current_mask |= bitmask_of_subsets[m];
            }
        }
    }

    void printResult(){
        System.out.println("Bitmask Dynamic Programming:");
        setCover(0,0);
        resultant_sets();
        if (dp[0][0] == INF)
            System.out.println("No solution found");
        else {
            System.out.println("Subsets: ");
            for (int i=0;i<M;i++){
                if ((resultantSets & 1) == 1) System.out.print(i+" ");
                resultantSets >>= 1;
            }
            System.out.println();
            System.out.println("Min Cost: "+dp[0][0]);
        }
    }
}
