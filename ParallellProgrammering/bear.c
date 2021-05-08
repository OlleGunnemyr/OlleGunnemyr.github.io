/* a multiple producer - consumer using semaphores and threads

   usage on Solaris:
     gcc bear.c -lpthread -lposix4
     a.out numBees numHoney

*/
#ifndef _REENTRANT
#define _REENTRANT
#endif
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdbool.h>

#define SHARED 1
#define MAXBEES 10
#define MAXHONEY 10

void *Bear(void *);  /* the two threads */
void *Bees(void *);

sem_t empty, full;    /* the global semaphores */
int pot;             /* shared buffer         */
int numHoney;

/* main() -- read command line and create threads, then
             print result when the threads have quit */

int main(int argc, char *argv[]) {
  /* thread ids and attributes */
  long l;
  pthread_t bear;
  pthread_t bees[MAXBEES];

  pthread_attr_t attr;
  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  int numBees =(argc > 1)? atoi(argv[1]) : MAXBEES;
  numHoney =(argc > 2)? atoi(argv[2]) : MAXHONEY;
  if (numBees > MAXBEES) numBees = MAXBEES;
  if (numHoney > MAXHONEY) numHoney = MAXHONEY;

  sem_init(&empty, SHARED, 1);  /* sem empty = 1 */
  sem_init(&full, SHARED, 0);   /* sem full = 0  */

  printf("main started\n");
  pthread_create(&bear, &attr, Bear, NULL);
  for (l = 0; l < numBees; l++){
	  pthread_create(&bees[l], &attr, Bees, (void *) l);
  }

  pthread_join(bear, NULL);
  for (l = 0; l < numBees; l++){
  	  pthread_join(bees[l], NULL);
  }
}

/* bear/consumer waits until pot is full, sets to zero, unlocks for producer/bees*/
void *Bear(void *arg) {
  while(true) {
    sem_wait(&full);
    printf("bear eats entire pot\n");
    pot = 0;
    sem_post(&empty);
  }

}

/* bees/producers waits until pot is empty, add one to pot until pot is full, then unlock
for consumer/bear*/
void *Bees(void *arg) {
   long myid = (long) arg;
  while(true) {
	  sem_wait(&empty);
	  if (pot == numHoney){
		  printf("pot full, bee %d calls bear\n", myid);
		  sem_post(&full);
	  }

	  else{
		  printf("bee %d fills pot with one port honey\n", myid);
		  pot++;
		  sem_post(&empty);
	  }
  }
}
