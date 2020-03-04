#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int myCompare(const char* arg1, const char* arg2, const char* order){
    int idx=0;
    while(arg1[idx]!='\0'&&arg2[idx]!='\0'){
        if(arg1[idx]!=arg2[idx])break;

        idx++;
    }
    if(arg1[idx]=='\0'||arg2[idx]=='\0')
        return strlen(arg1)-strlen(arg2);
    else{
        int diff = strchr(order,arg1[idx])-strchr(order,arg2[idx]);
        if(diff>0) return 1;
        else if(diff<0) return -1;
    }
    return 0;
}
void mySwitch(char** arg1, char** arg2){
    char* tmp = *arg1;
    *arg1 = *arg2;
    *arg2 = tmp;
}
int main(int arg, char** args){
    int n;
    int cur_idx=1;
    char order[27];
    char** words;

    while(true){
        scanf("%d",&n);
        if(n==0)break;

        scanf("%s",order);

        words = (char**)malloc(sizeof(char*)*n);

        for(int i=0;i<n;i++){
            words[i] = (char*)malloc(sizeof(char)*31);
            scanf("%s",words[i]);
        }
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-1-i;j++){
                if(myCompare(words[j],words[j+1],order)>0)
                    mySwitch(&words[j],&words[j+1]);
            }
        }
        printf("year %d\n",cur_idx);
        for(int i=0;i<n;i++){
            printf("%s\n",words[i]);
            free(words[i]);
        }
        free(words);
        cur_idx++;
    }
    return 0;
}