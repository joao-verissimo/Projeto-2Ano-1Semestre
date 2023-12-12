#include <stdio.h>
#include "cfunctions.h"

void outputSensorsByType(Sensor *sensors, int numSensors)
{
    // Open a file for each sensor type
    FILE *files[NUM_TYPE_SENSORS];
    for (int i = 0; i < NUM_TYPE_SENSORS; i++)
    {
        char filename[32];
        sprintf(filename, "sensors_type_%d.csv", i);
        files[i] = fopen(filename, "w");

        // Write the header line
        fprintf(files[i], "id,type,max_limit,min_limit,frequency,readings_size\n");
    }

    // Write the data for each sensor
    for (int i = 0; i < numSensors; i++)
    {
        outputSensorToCSV(files[sensors[i].sensor_type], &sensors[i]);
    }

    // Close the files
    for (int i = 0; i < NUM_TYPE_SENSORS; i++)
    {
        fclose(files[i]);
    }
}
/*
How to call and explanation: 
    outputSensorsByType(sensors, numSensors);
This function will create a CSV file for each sensor type, with the filename "sensors_type_X.csv", where X is the sensor type.
The file will contain the data for all sensors of that type, with each line representing a single sensor.
*/

// This function outputs a matrix of integers to a CSV file
void outputMatrixToCSV(int **matrix, int rows, int cols)
{
    FILE *file = fopen("matrix.csv", "w+");
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            fprintf(file, "%d", matrix[i][j]);
            if (j < cols - 1)
            {
                fprintf(file, ",");
            }
        }
        fprintf(file, "\n");
    }
    fclose(file);
}
/*
How to call and use it :

Open a file for writing
    FILE *file = fopen("matrix.csv", "w");

Write the matrix to the file
    outputMatrixToCSV(file, matrix, rows, cols);

Close the file
    fclose(file);
*/
void outputSensorToCSV(FILE *file, Sensor *sensor)
{
    fprintf(file, "%d,%d,%d,%d,%d,%d\n", sensor->id, sensor->sensor_type, sensor->max_limit, sensor->min_limit, sensor->frequency, sensor->readings_size);
}
