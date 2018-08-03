import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;

public class LinearProgramming {
    int N,M;
    ArrayList<int[]> subsets;
    double[] weight;
    int[] subset_size;
    int resultantSets;
    double minCost;

    public LinearProgramming(int N, int M, ArrayList<int[]> subsets, double[] weight, int[] subset_size){
        this.N = N;
        this.M = M;
        this.subsets = subsets;
        this.weight = weight;
        this.subset_size = subset_size;

        minCost = 0;
        resultantSets = 0;
    }

    public boolean doesElementExist(int [] subset, int subset_size, int element) {
        for (int i=0;i<subset_size;i++) {
            if (subset[i] == element) return true;
        }
        return false;
    }


    public int countMaxNumElement() {
        int max = 0;
        for (int n=0;n<N;n++) {
            int count = 0;
            for (int m=0;m<M;m++) {
                int [] subset = subsets.get(m);
                if (doesElementExist(subset, subset_size[m], n)) count++;
            }
            if (count > max) max = count;
        }
        return max;
    }

    public void setCover(){
        LinearObjectiveFunction lof = new LinearObjectiveFunction(weight, 0);
        ArrayList<LinearConstraint> constraints = new ArrayList<>();
        for (int i=0;i<N;i++) {
            double [] coefficient = new double[M];
            for (int m=0;m<M;m++) {
                int [] subset = subsets.get(m);
                if (doesElementExist(subset, subset_size[m], i)) coefficient[m] = 1;
            }
            constraints.add(new LinearConstraint(coefficient, Relationship.GEQ, 1));
        }

        SimplexSolver solver = new SimplexSolver();
        PointValuePair result = solver.optimize(new MaxIter(50), lof, new LinearConstraintSet(constraints), GoalType.MINIMIZE, new NonNegativeConstraint(true));
        double [] values = result.getPoint();
        for(int i=0;i<M;i++) System.out.print(values[i]+" ");
        System.out.println();

        for (int m=0;m<M;m++) {
            for(int n=0;n<=50*Math.log(N);n++){
                double random = Math.random()*1.0001+0;
                if(values[m] >= random){
                    resultantSets |= (1 << m);
                    minCost += weight[m];
                    break;
                }
            }
            /*if (values[m] >= 1/(double)countMaxNumElement()) {
                resultantSets |= (1 << m);
                minCost += weight[m];
            }*/
        }
    }

    public void printResult() {
        System.out.println("Linear Programming:");
        setCover();
        System.out.print("Subsets: ");
        for (int i=0;i<M;i++) {
            if ((resultantSets & 1) == 1) System.out.print(i + " ");
            resultantSets >>= 1;
        }
        System.out.println();
        System.out.println("Min Cost: " + minCost);
    }
}
