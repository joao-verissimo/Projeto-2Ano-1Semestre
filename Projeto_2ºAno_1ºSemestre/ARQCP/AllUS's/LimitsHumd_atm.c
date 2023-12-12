#include "stdio.h"

void LimitsHumd_atm(int N){
    const int MAX=45;
    const int MIN=-15;
    int count2=0;
    while (count2!=N)
    {
        val=sens_humd_atm();
        if (val<MIN || val>MAX)
            count2++;
    }
        if (count2>N) LimitsHumd_atm.c(int N);
    count2=0;
}