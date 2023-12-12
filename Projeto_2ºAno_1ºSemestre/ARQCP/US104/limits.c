#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define limite_min -5
#define limite_max 35
#define leituras_erradas 5

/*int ler_sensor() {
  return valor_lido;
}*/

void reiniciar_sensor(int *valores_corretos, int tamanho) {
  printf("Numero de erros consecutivos atingido!\n");
  printf("Valores incorretos apagados! ");
  printf("\n");
  printf ("Valores corretos:");
  for (int i = 0; i < tamanho; i++) {
    printf("%d ", valores_corretos[i]);
  }
  printf("\n");
  srand(time(NULL));
}

int verificar_limites(int valor, int min, int max) {
  if (valor < min || valor > max) {
    return 0;
  } else {
    return 1;
  }
}

int *monitorar_sensor(int *temperaturas, int tamanho) {
  int contador_erros = 0;
  int i = 0;
  static int valores_corretos[15];
  while (i<tamanho) {
    //int valor_lido = ler_sensor();
    int valor_lido = temperaturas[i];
    if (valor_lido < limite_min || valor_lido > limite_max) {
      contador_erros++;

      if (contador_erros >= leituras_erradas) {
        reiniciar_sensor(valores_corretos,i);
        contador_erros = 0;
        break;
      }
    } else {
      valores_corretos[i] = valor_lido;
      contador_erros = 0;
    }
    
    i++;
  }
  return valores_corretos;
}

int main() {
  srand(time(NULL));
  int temperaturas1[15];
  for (int i = 0; i < 15; i++) {
    temperaturas1[i] = rand() % 81 - 25;
  
  }
    int *valores1 = temperaturas1;
  for (int i = 0; i < 15; i++) {
    printf("%d ", valores1[i]);
  }
  printf("\n");
  int *valores1processados = monitorar_sensor(temperaturas1, 15);
  for (int i = 0; i < 15; i++) {
    printf("%d ", valores1processados[i]);
  }
  printf("\n");


  return 0;
}