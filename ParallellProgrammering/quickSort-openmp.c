/* quicksort using openmp

   features: a given number of threads sorts an unsorted array of integers
   		by reqursivly  call itself in and outside of tasks
   		(using quicksort algorithm), and prints the executed time to
   		the standard output.

   usage under Linux:
     gcc -O -fopenmp -o quickSort-openmp quickSort-openmp.c
     a.out numWorkers size

*/
#ifndef _REENTRANT
#define _REENTRANT
#endif
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <stdbool.h>
#include <sys/time.h>
#define MAXSIZE 50000
#define MAXWORKERS 4

double start_time, end_time; /* start and end times */
int list[MAXSIZE];

//shared structure
struct thread_data{
	int first;
	int last;
};

void quicksort (void* sublist);
void medianOf3(int lo, int hi);
void swap(int a, int b);


/* read command line, initialize and create parallel region construct */
int main(int argc, char* argv[]) {
	int size, i, numWorkers;

	 /* read command line args if any */
	  size = (argc > 1)? atoi(argv[1]) : MAXSIZE;
	  numWorkers = (argc > 2)? atoi(argv[2]) : MAXWORKERS;
	  if (size > MAXSIZE) size = MAXSIZE;
  if (numWorkers > MAXWORKERS) numWorkers = MAXWORKERS;

	struct thread_data wholeList = {0, size-1};
omp_set_num_threads(numWorkers);
omp_set_dynamic(0);

/* initialize the list */
	for (i = 0; i<size; i++){
		list[i] = rand()%99;
		//printf("%d ", list[i]); //add to print unsorted list
	}
	printf("\n");

	/* do the parallel work: create a single directive and call quicksort*/
	start_time = omp_get_wtime();
	#pragma omp parallel
	{
		#pragma omp single nowait
		{
			quicksort((void *) &wholeList);
		}
	}
	end_time = omp_get_wtime();

// add to print sorted list
/*	for(i = 0; i < size; i++){
		printf("%d ", list[i]);
	}
	printf("\n");
*/
	/* print time executed */
	printf("The execution time is %g sec\n", end_time - start_time);

	return 0;
}

/* Estimates optimal pivot by comparing first, middle and last element, and placing it
in the start of the list*/
void medianOf3(int lo, int hi){
	int mid = (lo+hi)/2;
	if (list[mid] > list[hi]){
		swap(mid, hi);
	}

	if (list[hi] < list[lo]){
			swap(hi, lo);
	}

	if (list[mid] > list[lo]){
		swap(mid, lo);
	}
}

/* swaps two elements in the list*/
void swap(int a, int b){
	int temp = list[a];
	list[a] = list[b];
	list[b] = temp;
}

/* Each thread recieves a sublist (unique instance of the structure), sorts/divides
it in two new sublists and creates recursive task and function call.*/
void quicksort(void * sublist){
	int i, j, pivot, first, last;

	/* structure object */
	struct thread_data *my_data;
	my_data = (struct thread_data *) sublist;

	/* access elements in structure*/
	first = my_data -> first;
	last = my_data -> last;



	/*sorts array by dividing the list into two sublists, one with
	smaller numbers than the other*/
	if(first < last){
			medianOf3(first, last);
			i = pivot = first;
			j = last;

			while(i<j){
				while(list[i] <= list[pivot] && i < last){
					i++;
				}
				while(list[j] > list[pivot]){
					j--;
				}
				if(i < j){
				swap(i, j);
				}
			}

			swap(pivot, j);

			/* initiate elements in the structures*/
			struct thread_data sublist1 = {first, j-1};
			struct thread_data sublist2 = {j+1, last};

			/*create a task directive and call function inside and outside*/

			#pragma omp task
			{
				quicksort((void *) &sublist1);
			}

			#pragma omp task
			{
				quicksort((void *) &sublist2);
			}
		}
}