#include <stdio.h>
#include <stdlib.h>

typedef struct
{
    unsigned short id;
    unsigned char sensor_type;
    unsigned short max_limit;
    unsigned short min_limit;
    unsigned long frequency;
    unsigned long readings_size;
    unsigned short *readings;
} Sensor;

// Function to read sensor configuration from a file or user input
void readSensorConfiguration(Sensor *sensor)
{
    printf("Enter sensor id: ");
    scanf("%hu", &sensor->id);
    printf("Enter sensor type: ");
    scanf("%hhu", &sensor->sensor_type);
    printf("Enter max limit: ");
    scanf("%hu", &sensor->max_limit);
    printf("Enter min limit: ");
    scanf("%hu", &sensor->min_limit);
    printf("Enter frequency: ");
    scanf("%lu", &sensor->frequency);
    printf("Enter readings size: ");
    scanf("%lu", &sensor->readings_size);
    sensor->readings = (unsigned short *)malloc(sensor->readings_size * sizeof(unsigned short));
}

int main()
{
    // Determine number of sensors of each type
    int numSensors;
    printf("Enter number of sensors: ");
    scanf("%d", &numSensors);

    // Create a dynamic array of sensors
    Sensor *sensors = (Sensor *)malloc(numSensors * sizeof(Sensor));

    // Read configuration for each sensor
    for (int i = 0; i < numSensors; i++)
    {
        readSensorConfiguration(&sensors[i]);
    }

    // Use sensors
    for (int i = 0; i < numSensors; i++)
    {
        printf("Sensor %hu: type = %hhu, max limit = %hu, min limit = %hu, frequency = %lu, readings size = %lu\n",
               sensors[i].id, sensors[i].sensor_type, sensors[i].max_limit, sensors[i].min_limit, sensors[i].frequency, sensors[i].readings_size);
    }

    // Free memory
    for (int i = 0; i < numSensors; i++)
    {
        free(sensors[i].readings);
    }
    free(sensors);

    return 0;
}