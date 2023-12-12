#include <stdio.h>
#include "asm.h"
#include <stdint.h>
uint64_t inc = 0;
uint64_t state = 0;

int main()
{
  int res = readrand();
  if (res == 0)
  {
    int i;
    for (i = 0; i < 63; i++)
      printf("%8x\n", pcg32_random_r());
  }

  char ult_temp = 20;
  char ult_velc_vento = 20;
  short ult_dir_vento = 20;
  char ult_hmd_atm = 20;
  char ult_pluvio = 20;
  char comp_rand = pcg32_random_r();
  short comp_rand2 = pcg32_random_r();

  printf("%c\n", sens_temp(ult_temp, comp_rand));
  printf("%c\n", sens_velc_vento(ult_velc_vento, comp_rand));
  printf("%c\n", sens_dir_vento(ult_dir_vento, comp_rand2));
  printf("%c\n", sens_humd_atm(ult_hmd_atm, ult_pluvio, comp_rand));
  printf("%c\n", sens_humd_solo(ult_pluvio, ult_pluvio, comp_rand));
  printf("%c\n", sens_pluvio(ult_pluvio, ult_temp, comp_rand));
  return 0;
  
  // i need to merge all the sensors into one array 
  // outputSensorsByType(sensors, numSensors)
  // outputMatrixToCSV(matrix, rows, cols)
}

int readrand()
{
  uint32_t buffer[64];
  FILE *f;
  int result;
  int i;
  f = fopen("/dev/urandom", "r");
  if (f == NULL)
  {
    printf("Error: open() failed to open /dev/random for reading\n");
    return 1;
  }
  result = fread(buffer, sizeof(uint32_t), 64, f);
  if (result < 1)
  {
    printf("error , failed to read and words\n");
    return 1;
  }
  printf("Read %d words from /dev/urandom\n", result);
  for (i = 0; i < result - 1; i++)
    inc = buffer[i];
  state = buffer[i + 1];
  return 0;
}
