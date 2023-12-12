#ifndef CFUNCTIONS_H
#define CFUNCTIONS_H

typedef struct
{
    int id;
    int sensor_type;
    int max_limit;
    int min_limit;
    int frequency;
    int readings_size;
    int *readings;
} Sensor;
int NUM_TYPE_SENSORS = 4;
void outputSensorsByType(Sensor *sensors, int numSensors);
void outputMatrixToCSV(FILE *file, int **matrix, int rows, int cols);
void LimitsDir_vento(int N);
void LimitsHumd_atm(int N);
void LimitsPluvio(int N);
void LimitsTemp(int N);
int **createSummaryMatrix(int rows, int cols, int *data);
void store();

#endif
