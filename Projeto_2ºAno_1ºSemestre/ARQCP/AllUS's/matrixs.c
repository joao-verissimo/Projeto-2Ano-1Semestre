#include <stdio.h>
unsigned int NUM_TYPE_SENSORS = 7;
unsigned int ROWS = NUM_TYPE_SENSORS , COLS = 3;
unsigned int DATA = {10, 11, 12, 13, 14, 15, 16, 20, 21, 22, 23, 24, 25, 26, 30, 31, 32, 33, 34, 35, 36, 40, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 55, 56, 60, 61, 62, 63, 64, 65, 66, 70, 71, 72, 73, 74, 75, 76}


int **createSummaryMatrix(int rows, int cols, int *data){

    int i = 0, j = 0;
    int min = *(data);
    int max = *(data);
    int average = 0;
    int total = rows * cols;

    //Creates a new rows*cols matrix of integers
    int **matrix = malloc(rows * sizeof(int *));

    int mem_cols = cols * sizeof(int);
    for (i = 0; i < rows; i++)
	{
		matrix[i] = malloc(mem_cols);
	}


    for (i = 0; i < rows; i++)
    {
        /** 
         * POS = sizeof(int) * (i * cols + j)
        */
        /*
        min = getMin(i, cols, data);
        max = getMax(i, cols, data);
        average = getAverage(i, cols, data);
        */
        
        int sum = 0;
        for (j = 1; j < cols; j++)
        {
            if ((matrix+i)+j < min) min = (matrix+i)+j;
            if ((matrix+i)+j > max) max = (matrix+i)+j;
            sum += (matrix+i)+j; 
        }
        average = sum / cols;

        ((matrix+i) + 1) = min;
        ((matrix+i) + 2) = average;
        ((matrix+i) + 3) = max;   
    }
    
    return matrix;
}

