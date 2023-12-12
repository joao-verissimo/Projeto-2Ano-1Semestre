#include "stdio.h"

void LimitsDir_vento(int N){
    const int MAX=45;
    const int MIN=-15;
    int count1=0;
    while (count1!=N)
    {
        val=sens_dir_vento();
        if (val<MIN || val>MAX)
            count1++;
    }
        if (count1>N) LimitsDir_vento(int N);
    count1=0;
}