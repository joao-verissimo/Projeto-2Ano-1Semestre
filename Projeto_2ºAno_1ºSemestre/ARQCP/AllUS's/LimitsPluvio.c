#include "stdio.h"

void LimitsPluvio(int N){
    const int MAX=45; 
    const int MIN=-15;
    int count4=0;
    while (count4!=N)
    {
        val=sens_pluvio();
        if (val<MIN || val>MAX)
            count4++;
    }
        if (count4>N) LimitsPluvio(int N);
    count4=0;
}