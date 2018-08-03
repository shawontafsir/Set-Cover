#include<bits/stdc++.h>
#define INF 99999999
using namespace std;

class Set_Cover{
public:
    int N;
    int M;
    vector<int> weight;
    vector<int> subset_size;
    vector<vector<int> > subsets;

    vector<int> bitmask;
    vector<vector<int> > dp;
    vector<vector<bool> > isIn;
    int result_mask;

    Set_Cover(int n, int m, vector<vector<int> > subsets, vector<int> weight, vector<int> subset_size){
        this->N = n;
		this->M = m;
		this->subsets = subsets;
		this->weight = weight;
		this->subset_size = subset_size;

		dp.resize(m,vector<int> ((int)pow(2,n),-1));
		isIn.resize(m,vector<bool> ((int)pow(2,n), false));
		result_mask=0;

		bitmask.resize(m);
		for (int i=0;i<m;i++) {
			vector<int> subset = subsets[i];
			int c = 0;
			for (int j=0;j<subset_size[i];j++) {
				c |= 1<<subset[j];
			}
			bitmask[i] = c;
		}
    }

    int setCover(int current, int current_mask) {
		if (current == M && current_mask != (1<<N)-1) return INF;
		else if (current == M && current_mask == (1<<N)-1) return 0;
		else {
			int chooseCurrent = setCover(current+1, current_mask | bitmask[current]) + weight[current];
			int notChooseCurrent = setCover(current+1, current_mask);
			int ans;
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
				result_mask |= (1<<m);
				current_mask |= bitmask[m];
			}
		}
	}

	void printResult(){
        setCover(0,0);
        resultant_sets();
        if (dp[0][0] == INF)
			cout<<"No solution found\n";
		else {
            cout<<"Subsets: \n";
			for (int i=0;i<M;i++){
				if ((result_mask & 1) == 1) cout<<i<<" ";
				result_mask >>= 1;
			}
			cout<<"\nMin Cost: "<<dp[0][0]<<endl;
		}
	}
};

int main(){
    ifstream in("1305072_input.txt");
    if (in.is_open())
    {
        string line;
        getline(in,line);
        int T = atoi(line.c_str());

        for(int t=0;t<T;t++){
            int N,M;

            getline(in,line);
            N=atoi(line.c_str());
            getline(in,line);
            M=atoi(line.c_str());
            vector<int> weight(M), subset_size(M);
            vector<vector<int> > subsets(M);
            for(int m=0;m<M;m++)
            {
                getline(in,line);
                stringstream ss(line);
                string s;
                ss>>s;
                weight[m] = atoi(s.c_str());
                ss>>s;
                subset_size[m] = atoi(s.c_str());
                for(int i=0;i<subset_size[m];i++) {ss>>s; subsets[m].push_back(atoi(s.c_str()));}

            }

            Set_Cover sc(N, M, subsets, weight, subset_size);
            sc.printResult();
        }
        in.close();
    }

    return 0;
}
