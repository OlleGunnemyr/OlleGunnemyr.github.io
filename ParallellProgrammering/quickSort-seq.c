/* sequential quicksort

   features: main function sorts an unsorted array of integers
   		by reqursivly  call itself (using quicksort algorithm),
   		and prints the executed time to the standard output.

   usage under Linux:
     gcc quickSort.c
     a.out size

*/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <sys/time.h>
#define MAXSIZE 50000

double start_time, end_time; /* start and end times */
int list[MAXSIZE];

//global structure
struct data{
	int first;
	int last;
};

void quicksort (void* sublist);
void medianOf3(int lo, int hi);
void swap(int a, int b);
double read_timer();



/* read command line, initialize, and calls quicksort */
int main(int argc, char* argv[]) {
	int size, i;

	size = (argc > 1)? atoi(argv[1]) : MAXSIZE;
	if (size > MAXSIZE) size = MAXSIZE;

	struct data wholeList = {0, size-1};

/* initialize the list */
	for (i = 0; i<size; i++){
		list[i] = rand()%99;
		//printf("%d ", list[i]); //add to print unsorted list
	}
	printf("\n");
/* call quicksort and calculate execution time*/
	start_time = read_timer();
	quicksort((void *) &wholeList);
	end_time = read_timer();

// add to print sorted list
/*	for(i = 0; i < size; i++){
		printf("%d ", list[i]);
	}
	printf("\n");*/

	/* print time executed */
	printf("The execution time is %g sec\n", end_time - start_time);

	return 0;
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

/* Estimates optimal pivot by comparing first, middle and last element, and placing it
in the start of the list*/
void medianOf3(int lo, int hi){
	int mid = (lo+hi)/2;
	int temp;
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

/* quicksort recieves a sublist (unique instance of the structure), sorts/divides
it in two new sublists and calls itself twice.*/
void quicksort(void * sublist){
	int i, j, pivot, first, last;

	/* structure object */
	struct data *my_data;
	my_data = (struct data *) sublist;

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
			struct data sublist1 = {first, j-1};
			struct data sublist2 = {j+1, last};

			/*call function again*/
			quicksort((void *) &sublist1);
			quicksort((void *) &sublist2);
		}
}