#include <stdio.h>
#include <stdlib.h>

int** new_array(int n, int m){
    int **M;
    M = (int **) malloc(n * sizeof(int *));
    for (int i=0; i<n; i++){
        M[i] = (int *) malloc(m * sizeof(int));
    }
    return M;
}

int main(void) {
    freopen("../../data.txt", "r", stdin);
    int n = 0, m = 0;
    scanf("%d %d\n", &n, &m);

    int** Ma = new_array(n, m);

    for(int i=0;i<n;i++){
        for(int j=0;j<m;j++){
            scanf("%d", &Ma[i][j]);
        }
    }

    int m2 = 0, t = 0;
    scanf("%d %d\n", &m2, &t);

    int** Mb = new_array(m2, t);

    for(int i=0;i<m2;i++){
        for(int j=0;j<t;j++){
            scanf("%d", &Mb[i][j]);
        }
    }

    int** Mc = new_array(n, t);
    for(int i=0;i<n;i++){
        for(int j=0;j<t;j++){
            Mc[i][j] = 0;
            for(int k=0;k<m;k++){
                Mc[i][j] += Ma[i][k] * Mb[k][j];
            }
        }
    }

    return 0;
}
