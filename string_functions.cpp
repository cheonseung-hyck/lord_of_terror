#include<stdio.h>
#include<string.h>
#include<stdlib.h>

int mystrlen(const char* s){

    int length=0;
    const char* p=s;
    while(*s!='\0'){
        length++;
        s++;
    }
    return length;
}
int mystrcmp(const char* c1, const char* c2){
    
    int index=0;
    while(c1[index]!='\0'&&c2[index]!='\0'){
        if(c1[index]!=c2[index])
            return c1[index]-c2[index];
        else
            index++;
    }
    if(c1[index]==c2[index])
        return 0;
    else{
        if(c1[index]=='\0')
            return -1;
        else
            return 1;
    }
        
}
char* mystrcpy(const char* c1,const char* c2){

    c1 = (char*)malloc(sizeof(char)*mystrlen(c2)+1);
    c1=c2;
    return (char*)c1;
}
char* mystrcat(const char* c1, const char* c2){

    int length = mystrlen(c1)+mystrlen(c2)+1;
    char* c = (char*)malloc(sizeof(char)*length);
    int index=0;

    for(;index<mystrlen(c1);index++){
        c[index]=c1[index];
    }
    for(int i=0;i<mystrlen(c2);index++,i++){
        c[index]=c2[i];
    }
    c[index]='\0';

    return c;
}
char* mystrchr(const char* s, char c){

    const char* str = s;
    char* result = NULL;

    while(*str!='\0'){
        if(*str==c){
            result = (char*)str;
            break;
        }
        str++;
    }
    return result;
}
int main(int argc, char** args){
 
    printf("strlen : %d\n",strlen("hello world!"));
    printf("my strlen : %d\n",mystrlen("hello world!"));
    printf("\n");

    printf("strcmp : %d\n",strcmp("how","however"));
    printf("my strcmp : %d\n",mystrcmp("how","however"));
    printf("\n");

    char however[8];
    char however2[8];
    printf("strcpy : %s\n",strcpy(however,"however"));
    printf("my strcpy : %s\n",mystrcpy(however2,"however"));
    printf("\n");

    char myBoy[23] = "I was a boy";
    char myBoy2[23] = "I was a boy";
    printf("strcat : %s\n",strcat(myBoy," not a girl"));
    printf("my strcat : %s\n",mystrcat(myBoy2," not a girl"));
    printf("\n");

    printf("strchr : %s\n",strchr("I was a man",'w'));
    printf("my strchr : %s\n",mystrchr("I was a man",'w'));
    printf("\n");

    getchar();
    return 0;
}