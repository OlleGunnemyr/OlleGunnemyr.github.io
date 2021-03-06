/* matrix summation using pthreads

   features: uses a barrier; the Worker[0] computes
             the total sum from partial sums computed by Workers
             and prints the total sum to the standard output

   usage under Linux:
     gcc matrixSum.c -lpthread
     a.out size numWorkers

*/
#ifndef _REENTRANT
#define _REENTRANT
#endif
#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
#include <time.h>
#include <sys/time.h>
#define MAXSIZE 10000  /* maximum matrix size */
#define MAXWORKERS 10   /* maximum number of workers */

pthread_mutex_t barrier;  /* mutex lock for the barrier */
pthread_cond_t go;        /* condition variable for leaving */
int numWorkers;           /* number of workers */
int numArrived = 0;       /* number who have arrived */

/* a reusable counter barrier */
void Barrier() {
  pthread_mutex_lock(&barrier);
  numArrived++;
  if (numArrived == numWorkers) {
    numArrived = 0;
    pthread_cond_broadcast(&go);
  } else
    pthread_cond_wait(&go, &barrier);
  pthread_mutex_unlock(&barrier);
}

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
int size, stripSize;  /* assume size is multiple of numWorkers */
int sums[MAXWORKERS]; /* partial sums */
int matrix[MAXSIZE][MAXSIZE]; /* matrix */

int maxsPos[MAXWORKERS][2]; /*task1A*/
int minsPos[MAXWORKERS][2];


void *Worker(void *);

/* read command line, initialize, and create threads */
int main(int argc, char *argv[]) {
  int i, j;
  long l; /* use long in case of a 64-bit system */
  pthread_attr_t attr;
  pthread_t workerid[MAXWORKERS];

  /* set global thread attributes */
  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  /* initialize mutex and condition variable */
  pthread_mutex_init(&barrier, NULL);
  pthread_cond_init(&go, NULL);

  /* read command line args if any */
  size = (argc > 1)? atoi(argv[1]) : MAXSIZE;
  numWorkers = (argc > 2)? atoi(argv[2]) : MAXWORKERS;
  if (size > MAXSIZE) size = MAXSIZE;
  if (numWorkers > MAXWORKERS) numWorkers = MAXWORKERS;
  stripSize = size/numWorkers;

  /* initialize the matrix */
  for (i = 0; i < size; i++) {
	  for (j = 0; j < size; j++) {
          matrix[i][j] = rand()%99; /*task1A*/
	  }
  }

  /* print the matrix */
#ifdef DEBUG
  for (i = 0; i < size; i++) {
	  printf("[ ");
	  for (j = 0; j < size; j++) {
	    printf(" %d", matrix[i][j]);
	  }
	  printf(" ]\n");
  }
#endif

  /* do the parallel work: create the workers */
  start_time = read_timer();
  for (l = 0; l < numWorkers; l++)
    pthread_create(&workerid[l], &attr, Worker, (void *) l);
  pthread_exit(NULL);
}

/* Each worker sums the values in one strip of the matrix.
   After a barrier, worker(0) computes and prints the total */
void *Worker(void *arg) {
  long myid = (long) arg;
  int total, i, j, first, last;

  int max, min, maxRad, maxCol, minRad, minCol; /*task1A*/

#ifdef DEBUG
  printf("worker %d (pthread id %d) has started\n", myid, pthread_self());
#endif

  /* determine first and last rows of my strip */
  first = myid*stripSize;
  last = (myid == numWorkers - 1) ? (size - 1) : (first + stripSize - 1);

  /* sum values in my strip */
  total = 0;

  max = 0; /*task1A*/
  min = MAXSIZE;


  for (i = first; i <= last; i++){
    for (j = 0; j < size; j++){
      total += matrix[i][j];

      if (max < matrix[i][j]){ /*task1A*/
		  max = matrix[i][j];
		  maxsPos[myid][0] = i;
		  maxsPos[myid][1] = j;
	  }

      if (min > matrix[i][j]){ /*task1A*/
		  min = matrix[i][j];
		  minsPos[myid][0] = i;
		  minsPos[myid][1] = j;
	  }
  	}
  }
  sums[myid] = total;



  Barrier();
  if (myid == 0) {
    total = 0;

    max = maxRad = minRad = maxCol = minCol = 0; /*task1A*/
    min = MAXSIZE;


    for (i = 0; i < numWorkers; i++){
      total += sums[i];

      if (max < matrix[maxsPos[i][0]][maxsPos[i][1]]){ /*task1A*/
		  max = matrix[maxsPos[i][0]][maxsPos[i][1]];
		  maxRad = maxsPos[i][0];
		  maxCol = maxsPos[i][1];
	  }

	  if (min > matrix[minsPos[i][0]][minsPos[i][1]]){ /*task1A*/
		  min = matrix[minsPos[i][0]][minsPos[i][1]];
		  minRad = minsPos[i][0];
		  minCol = minsPos[i][1];
	  }
  	}
    /* get end time */
    end_time = read_timer();
    /* print results */
    printf("The total is %d\n", total);
    printf("The execution time is %g sec\n", end_time - start_time);

    printf("Max value and pos row,column: %d %d,%d\n", max, maxRad, maxCol); /*task1A*/
    printf("Min value and pos row,column: %d %d,%d\n", min, minRad, minCol);
  }
}
