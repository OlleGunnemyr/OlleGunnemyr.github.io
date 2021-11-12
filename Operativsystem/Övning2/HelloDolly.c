#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
int main() {
int pid = fork();
if (pid == 0) {
printf("I'm the child %d\n" , getpid());
sleep(1);
} else {
printf("My child is called %d\n" , pid);
}
printf("That's it %d\n" , getpid());
return 0 ;
}
