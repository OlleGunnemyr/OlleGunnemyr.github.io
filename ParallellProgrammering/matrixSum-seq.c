/* matrix summation using pthreads

   features: sequential search program for maximum, minimum elements,
   their positions and the total sum of a matrix with given size

   usage under Linux:
     gcc matrixSum-seq.c
     a.out size

*/
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <sys/time.h>
#define MAXSIZE 10000  /* maximum matrix size */

/* timer */
double read_timer() {
    static bool initialized = false;
    static struct timeval start;
    struct timeval end;
    if( !initialized )
    {
        gettimeofday( &start, NULL );
        initialized = true;
    }
    gettimeofday( &end, NULL );
    return (end.tv_sec - start.tv_sec) + 1.0e-6 * (end.tv_usec - start.tv_usec);
}

double start_time, end_time; /* start and end times */
int size;
int matrix[MAXSIZE][MAXSIZE]; /* matrix */
int sum;
int maxPos[2];
int minPos[2];


void Worker();

/* read command line, initialize and call Worker function*/
int main(int argc, char *argv[]) {
  int i, j;
  sum = maxPos[0] = maxPos[1] = minPos[0] = minPos[1] = 0;

  /* read command line args if any */
  size = (argc > 1)? atoi(argv[1]) : MAXSIZE;
  if (size > MAXSIZE) size = MAXSIZE;

  /* initialize the matrix */
  for (i = 0; i < size; i++) {
	  for (j = 0; j < size; j++) {
          matrix[i][j] = rand()%99; /*task1A*/
	  }
  }

  /* add to print the matrix */
  /*for (i = 0; i < size; i++) {
	  printf("[ ");
	  for (j = 0; j < size; j++) {
	    printf(" %d", matrix[i][j]);
	  }
	  printf(" ]\n");
  }*/

  start_time = read_timer();
  Worker(size);
  end_time = read_timer();
  /* print results */
  printf("The total is %d\n", sum);
  printf("The execution time is %g sec\n", end_time - start_time);

  printf("Max value and pos row,column: %d %d,%d\n", matrix[maxPos[0]][maxPos[1]], maxPos[0], maxPos[1]); /*task1A*/
  printf("Min value and pos row,column: %d %d,%d\n", matrix[minPos[0]][minPos[1]], minPos[0], minPos[1]);



}

/* sequential search function for max, min and sum */
void Worker() {
  int i, j, max, min;

  max = 0;
  min = MAXSIZE;


  for (i = 0; i < size; i++){
    for (j = 0; j < size; j++){
      sum += matrix[i][j];

      if (max < matrix[i][j]){
		  max = matrix[i][j];
		  maxPos[0] = i;
		  maxPos[1] = j;
	  }

      if (min > matrix[i][j]){
		  min = matrix[i][j];
		  minPos[0] = i;
    	  minPos[1] = j;
	  }
  	}
  }
}
