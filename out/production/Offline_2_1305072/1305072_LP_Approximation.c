#include<bits/stdc++.h>
#define MAX 107
#define INF 1000000007
#define EPS (1e-12)
using namespace std;

void Pivot( long m,long n,double A[MAX+7][MAX+7],long *B,long *N,long r,long c )
{
    long i,j;
    swap( N[c],B[r] );
    A[r][c] = 1/A[r][c];
    for( j=0;j<=n;j++ ) if( j!=c ) A[r][j] *= A[r][c];
    for( i=0;i<=m;i++ ){
        if( i!=r ){
            for( j=0;j<=n;j++ ) if( j!=c ) A[i][j] -= A[i][c]*A[r][j];
            A[i][c] = -A[i][c]*A[r][c];
        }
    }
}

long Feasible( long m,long n,double A[MAX+7][MAX+7],long *B,long *N )
{
    long r,c,i;
    double p,v;
    while( 1 ){
        for( p=INF,i=0;i<m;i++ ) if( A[i][n]<p ) p = A[r=i][n];
        if( p > -EPS ) return 1;
        for( p=0,i=0;i<n;i++ ) if( A[r][i]<p ) p = A[r][c=i];
        if( p > -EPS ) return 0;
        p = A[r][n]/A[r][c];
        for( i=r+1;i<m;i++ ){
            if( A[i][c] > EPS ){
                v = A[i][n]/A[i][c];
                if( v<p ) r=i,p=v;
            }
        }
        Pivot( m,n,A,B,N,r,c );
    }
}

long Simplex( long m,long n,double A[MAX+7][MAX+7],double *b,double &Ret )
{
    long B[MAX+7],N[MAX+7],r,c,i;
    double p,v;
    for( i=0;i<n;i++ ) N[i] = i;
    for( i=0;i<m;i++ ) B[i] = n+i;
    if( !Feasible( m,n,A,B,N ) ) return 0;
    while( 1 ){
        for( p=0,i=0;i<n;i++ ) if( A[m][i] > p ) p = A[m][c=i];
        if( p<EPS ){
            for( i=0;i<n;i++ ) if( N[i]<n ) b[N[i]] = 0;
            for( i=0;i<m;i++ ) if( B[i]<n ) b[B[i]] = A[i][n];
            Ret = -A[m][n];
            return 1;
        }
        for( p=INF,i=0;i<m;i++ ){
            if( A[i][c] > EPS ){
                v = A[i][n]/A[i][c];
                if( v<p ) p = v,r = i;
            }
        }
        if( p==INF ) return -1;
        Pivot( m,n,A,B,N,r,c );
    }
}

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


        }
        in.close();
    }

    return 0;
}

