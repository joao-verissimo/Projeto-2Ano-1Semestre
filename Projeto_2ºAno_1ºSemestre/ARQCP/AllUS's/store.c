#include <stdio.h>
#include <stdlib.h>
#include "cfunctions.h"

void store()
{
    // Determine the number of sensors and the frequency of readings for each sensor
    int num_sensors = 3;

    // Allocate memory for the array of sensors
    Sensor *sensortemp = malloc(num_sensors * sizeof(Sensor));
    Sensor *sensorpluvio = malloc(num_sensors * sizeof(Sensor));
    Sensor *sensorhumd_atm = malloc(num_sensors * sizeof(Sensor));
    Sensor *sensorhumd_solo = malloc(num_sensors * sizeof(Sensor));
    Sensor *sensorvelcvento = malloc(num_sensors * sizeof(Sensor));
    Sensor *sensordirvento = malloc(num_sensors * sizeof(Sensor));

    for (size_t i = 0; i < 3; i++)
    {
        sensortemp[i].id = i;
        sensortemp[i].sensor_type = "T";
        sensortemp[i].max_limit = 35;
        sensortemp[i].min_limit = -5;
        printf("Insert the frequency of readings for sensor %d: ", sensortemp[i].id);
        scanf("%d", &sensortemp[i].frequency);
        sensortemp[i].readings_size = 0;
        sensortemp[i].readings = malloc(sensortemp[i].readings_size * sizeof(int));

        sensorpluvio[i].id = i;
        sensorpluvio[i].sensor_type = "P";
        sensorpluvio[i].max_limit = 100;
        sensorpluvio[i].min_limit = 0;
        printf("Insert the frequency of readings for sensor %d: ", sensorpluvio[i].id);
        scanf("%d", &sensorpluvio[i].frequency);
        sensorpluvio[i].readings_size = 0;
        sensorpluvio[i].readings = malloc(sensorpluvio[i].readings_size * sizeof(int));

        sensorhumd_atm[i].id = i;
        sensorhumd_atm[i].sensor_type = "H";
        sensorhumd_atm[i].max_limit = 100;
        sensorhumd_atm[i].min_limit = 0;
        printf("Insert the frequency of readings for sensor %d: ", sensorhumd_atm[i].id);
        scanf("%d", &sensorhumd_atm[i].frequency);
        sensorhumd_atm[i].readings_size = 0;
        sensorhumd_atm[i].readings = malloc(sensorhumd_atm[i].readings_size * sizeof(int));

        sensorvelcvento[i].id = i;
        sensorvelcvento[i].sensor_type = "V";
        sensorvelcvento[i].max_limit = 80;
        sensorvelcvento[i].min_limit = 0;
        printf("Insert the frequency of readings for sensor %d: ", sensorvelcvento[i].id);
        scanf("%d", &sensorvelcvento[i].frequency);
        sensorvelcvento[i].readings_size = 0;
        sensorvelcvento[i].readings = malloc(sensorvelcvento[i].readings_size * sizeof(int));

        sensorhumd_solo[i].id = i;
        sensorhumd_solo[i].sensor_type = "H";
        sensorhumd_solo[i].max_limit = 100;
        sensorhumd_solo[i].min_limit = 0;
        printf("Insert the frequency of readings for sensor %d: ", sensorhumd_solo[i].id);
        scanf("%d", &sensorhumd_solo[i].frequency);
        sensorhumd_solo[i].readings_size = 0;
        sensorhumd_solo[i].readings = malloc(sensorhumd_solo[i].readings_size * sizeof(int));

        sensordirvento[i].id = i;
        sensordirvento[i].sensor_type = "V";
        sensordirvento[i].max_limit = 80;
        sensordirvento[i].min_limit = 0;
        printf("Insert the frequency of readings for sensor %d: ", sensordirvento[i].id);
        scanf("%d", &sensordirvento[i].frequency);
        sensordirvento[i].readings_size = 0;
        sensordirvento[i].readings = malloc(sensordirvento[i].readings_size * sizeof(int));
    }

    // Free the memory allocated for the sensors and their readings
    for (int i = 0; i < num_sensors; i++)
    {
        free(sensortemp[i].readings);
        free(sensorpluvio[i].readings);
        free(sensorhumd_solo[i].readings);
        free(sensorhumd_atm[i].readings);
        free(sensordirvento[i].readings);
        free(sensorvelcvento[i].readings);
    }

    free(sensortemp);
    free(sensorpluvio);
    free(sensorhumd_solo);
    free(sensorhumd_atm);
    free(sensorvelcvento);
}
