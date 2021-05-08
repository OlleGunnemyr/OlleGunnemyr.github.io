/* matrix summation (and max/min elements) using OpenMP

   usage with gcc (version 4.2 or higher required):
     gcc -O -fopenmp -o matrixSumABC-openmp matrixSumABC-openmp.c
     ./matrixSumABC-openmp size numWorkers

*/

#include <omp.h>

double start_time, end_time;

#include <stdlib.h> //Task1A library needed for atoi and rand

#include <stdio.h>
#define MAXSIZE 10000  /* maximum matrix size */
#define MAXWORKERS 4   /* maximum number of workers */

int numWorkers;
int size;
int matrix[MAXSIZE][MAXSIZE];
void *Worker(void *);

/* read command line, initialize, and create threads */
int main(int argc, char *argv[]) {
  int i, j, total=0;

 int maxPos[] = {0,0};//Task1A
  int minPos[] = {0,0};
  int max = 0;
  int maxCol = 0;
  int minRad = 0;
  int maxRad = 0;
  int minCol=0;
  int min = 99;

  /* read command line args if any */
  size = (argc > 1)? atoi(argv[1]) : MAXSIZE;
  numWorkers = (argc > 2)? atoi(argv[2]) : MAXWORKERS;
  if (size > MAXSIZE) size = MAXSIZE;
  if (numWorkers > MAXWORKERS) numWorkers = MAXWORKERS;

  omp_set_num_threads(numWorkers);



  /* initialize the matrix */
  for (i = 0; i < size; i++) {
     // printf("[ ");
	  for (j = 0; j < size; j++) {
      matrix[i][j] = rand()%99;
     // 	  printf(" %d", matrix[i][j]);
	  }
	  //	  printf(" ]\n");
  }

  start_time = omp_get_wtime();
#pragma omp parallel for reduction (+:total) private(j) firstprivate(min, max, maxRad, maxCol, minRad, minCol)
  for (i = 0; i < size; i++){
    for (j = 0; j < size; j++){
      total += matrix[i][j];

      //Task1A
       if (max < matrix[i][j]){
	  		  max = matrix[i][j];
	  		  maxRad = i;
	  		  maxCol = j;
	  	  }

	   if (min > matrix[i][j]){
	  	  min = matrix[i][j];
	  	  minRad = i;
	  	  minCol = j;
	  }

	  }


      //Task1A
      #pragma omp critical(maximum)
      {
      if (matrix[maxPos[0]][maxPos[1]] < max){

		  maxPos[0] = maxRad;
  	  maxPos[1] = maxCol;
	  }
  }

 	 //Task1A
 	 #pragma omp critical(minimum)
 	 {
	 if (matrix[minPos[0]][minPos[1]] > min){
			  minPos[0] = minRad;
  	  minPos[1] = minCol;
	 }
 }

  }

// implicit barrier

  end_time = omp_get_wtime();

  printf("the total is %d\n", total);
  //Task1A
  printf("max and pos: %d %d,%d\n", matrix[maxPos[0]][maxPos[1]], maxPos[0], maxPos[1]);
  printf("min and pos: %d %d,%d\n", matrix[minPos[0]][minPos[1]], minPos[0], minPos[1]);

  printf("it took %g seconds\n", end_time - start_time);

}
