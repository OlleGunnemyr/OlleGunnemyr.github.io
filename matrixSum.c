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

int matrix[MAXSIZE][MAXSIZE]; /* matrix */


/*task1B*/
typedef struct {
int sum; /* partial sums */
int maxPosRad; /*task1A*/
int minPosRad;
int maxPosCol;
int minPosCol;
}MATRIXDATA;

MATRIXDATA mstr;

pthread_mutex_t mutexsum;
pthread_mutex_t mutexmin;
pthread_mutex_t mutexmax;

/*task1C*/
int nextRow = 0;
pthread_mutex_t mutexrow;



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


  /*task1B*/
  mstr.sum = 0;
  mstr.minPosRad = 0;
  mstr.maxPosRad = 0;
  mstr.minPosCol = 0;
  mstr.maxPosCol = 0;
  pthread_mutex_init(&mutexsum, NULL);
  pthread_mutex_init(&mutexmin, NULL);
  pthread_mutex_init(&mutexmax, NULL);

  /*task1C*/
  pthread_mutex_init(&mutexrow, NULL);


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
//#ifdef DEBUG
  for (i = 0; i < size; i++) {
	  printf("[ ");
	  for (j = 0; j < size; j++) {
	    printf(" %d", matrix[i][j]);
	  }
	  printf(" ]\n");
  }
//#endif

  /* do the parallel work: create the workers */
  start_time = read_timer();
  for (l = 0; l < numWorkers; l++)
    pthread_create(&workerid[l], &attr, Worker, (void *) l);


  /*task1B*/
  for(l = 0; l<numWorkers; l++){
	  pthread_join(workerid[l], NULL);
  }

  /* get end time */
  end_time = read_timer();
  /* print results */
  printf("The total is %d\n", mstr.sum);
  printf("The execution time is %g sec\n", end_time - start_time);

  printf("Max value and pos row,column: %d %d,%d\n", matrix[mstr.maxPosRad][mstr.maxPosCol], mstr.maxPosRad, mstr.maxPosCol); /*task1A*/
  printf("Min value and pos row,column: %d %d,%d", matrix[mstr.minPosRad][mstr.minPosCol], mstr.minPosRad, mstr.minPosCol);

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

  int row;
  while (true){
	  pthread_mutex_lock (&mutexrow);
	  row = nextRow;
	  nextRow++;
	  pthread_mutex_unlock (&mutexrow);

if ( row>=size){
  	  break;
  }

  first = row;
  last = row + 1;



  /* sum values in my strip */
  total = 0;

  max = maxCol = minCol = maxRad = minRad = 0; /*task1A*/
  min = 100;


  for (i = first; i < last; i++){
    for (j = 0; j < size; j++){
      total += matrix[i][j];

      if (max < matrix[i][j]){ /*task1A*/
		  max = matrix[i][j];
		  maxCol = j;
		  maxRad = i;
	  }

      if (min > matrix[i][j]){
		  min = matrix[i][j];
		  minCol = j;
		  minRad = i;
	  }
  	}
  }

  /*task1B*/
  pthread_mutex_lock (&mutexsum);
  mstr.sum += total;
  pthread_mutex_unlock (&mutexsum);

  pthread_mutex_lock (&mutexmin);
  if(matrix[mstr.minPosRad][mstr.minPosCol] > matrix[minRad][minCol]){
	  mstr.minPosRad = minRad;
	  mstr.minPosCol = minCol;
  }
  pthread_mutex_unlock (&mutexmin);


  pthread_mutex_lock (&mutexmax);
    if(matrix[mstr.maxPosRad][mstr.maxPosCol] < matrix[maxRad][maxCol]){
  	  mstr.maxPosRad = maxRad;
  	  mstr.maxPosCol = maxCol;
    }
  pthread_mutex_unlock (&mutexmax);



}


  return NULL;



  }

