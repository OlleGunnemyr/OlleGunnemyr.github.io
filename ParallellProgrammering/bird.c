/* a one producer - multiple consumer using semaphores and threads

   usage on Solaris:
     gcc bird.c -lpthread -lposix4
     a.out numBabyBirds numWorms

*/
#ifndef _REENTRANT
#define _REENTRANT
#endif
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <stdbool.h>
#include <unistd.h>//needed for sleep()

#define SHARED 1
#define MAXBABYBIRDS 10
#define MAXWORMS 10


void *ParentBird(void *);  /* the two threads */
void *BabyBird(void *);

sem_t empty, full;    /* the global semaphores */
int dish;             /* shared buffer         */
int numWorms;

/* main() -- read command line and create threads, then
             print result when the threads have quit */

int main(int argc, char *argv[]) {
  /* thread ids and attributes */
  long l;
  pthread_t pb;
  pthread_t bb[MAXBABYBIRDS];

  pthread_attr_t attr;
  pthread_attr_init(&attr);
  pthread_attr_setscope(&attr, PTHREAD_SCOPE_SYSTEM);

  int numBabyBirds =(argc > 1)? atoi(argv[1]) : MAXBABYBIRDS;
  numWorms =(argc > 2)? atoi(argv[2]) : MAXWORMS;
  if (numBabyBirds > MAXBABYBIRDS) numBabyBirds = MAXBABYBIRDS;
  if (numWorms > MAXWORMS) numWorms = MAXWORMS;

  sem_init(&empty, SHARED, 1);  /* sem empty = 1 */
  sem_init(&full, SHARED, 0);   /* sem full = 0  */

  printf("main started\n");
  pthread_create(&pb, &attr, ParentBird, NULL);
  for (l = 0; l < numBabyBirds; l++){
	  pthread_create(&bb[l], &attr, BabyBird, (void *) l);
  }

    pthread_join(pb, NULL);
    for (l = 0; l < numBabyBirds; l++){
    	  pthread_join(bb[l], NULL);
  }
}

/* parentBird/producer waits until dish is empty, fills it, unlocks for consumers/babyBirds*/
void *ParentBird(void *arg) {
  while(true) {
    sem_wait(&empty);
    printf("parent fills up dish\n");
    dish = numWorms;
    sem_post(&full);
  }

}

/* babyBirds/consumers waits until dish is full, remove one from dish until dish is empty, then unlock
for producer/parentBird*/
void *BabyBird(void *arg) {
   long myid = (long) arg;
  while(true) {
	  sem_wait(&full);
	  if (dish == 0){
		  printf("dish empty, baby %d calls parent\n", myid);
		  sem_post(&empty);
	  }

	  else{
		  printf("baby %d eats a worm and sleeps\n", myid);
		  dish--;
		  sem_post(&full);
		  sleep(1);
		  printf("baby %d is awake\n", myid);
	  }
  }
}
