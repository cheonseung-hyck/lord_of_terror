#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int rear;
int front;
int* queue;
int size;

void put(int data){
    
    if(rear%size==front&&rear>front){
        printf("the queue is full!!\n");
    }
    else{
        queue[rear%size]=data;
        rear=rear+size+1;
    }
}
int get(){

    int result;
    if(rear==front){
        printf("the queue is empty\n");
        result=-1;
    }
    else{
        result=queue[front];
        front=(front+1)%size;
        rear=rear%size;
    }
    return result;
}
int main(int argc, char** argv){

    char command[50];

    queue = (int*)malloc(sizeof(int)*(size=atoi(argv[1])));
    rear=front=0;

    while(true){
        gets(command);
        char* operaddress;
        char* instruction = strncpy((char*)malloc(sizeof(char)*4),command,3);
        int operand = strlen(command)<4?0:atoi(operaddress=strncpy((char*)malloc(sizeof(char)*(strlen(command)-4)),command+4,strlen(command)-4));

        if(strcmp(instruction,"put")==0)
            put(operand);
        else if(strcmp(instruction,"get")==0){
            printf("you got : %d\n",get());
        }
        else
            printf("\nunknown command!! : !%s!\n",command);
        
        free(instruction);
        free(operaddress);
    }    

    return 0;
}