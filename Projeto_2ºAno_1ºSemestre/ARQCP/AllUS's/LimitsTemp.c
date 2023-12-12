#include "stdio.h"

void LimitsTemp(int N){
    const int MAX=45; // 45ºC é a temperatura máxima
    const int MIN=-15; // -15ºC é a temperatura mínima
    int count5=0; // contador
    while (count5!=N) // enquanto o contador for de N
    {
        val=sens_temp(); // val recebe o valor da temperatura
        if (val<MIN || val>MAX) // se o valor da temperatura for menor que o mínimo ou maior que o máximo
            count5++; // incrementa o contador
    }
        if (count5>N) // se o contador for maior que N LimitsTemp(int N); // chama a função LimitsTemp
    count5=0;
}