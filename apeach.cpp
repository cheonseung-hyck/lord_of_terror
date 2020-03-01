#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int getHours(const char* time){
    char tmp[3]; 
    strncpy(tmp,time+11,2);
    tmp[2]='\0';
    return atoi(tmp)*3600000;
}
int getMinutes(const char* time){
    char tmp[3]; 
    strncpy(tmp,time+14,2);
    tmp[2]='\0';
    return atoi(tmp)*60000;
}
int getSeconds(const char* time){
    char tmp[7]; 
    strncpy(tmp,time+17,6);
    tmp[6]='\0';
    return atof(tmp)*1000;
}
int getDuration(const char* time){
    int length = strlen(time)-25;
    char tmp[length+1];
    strncpy(tmp,time+24,length);
    tmp[length]='\0';
    int result=atof(tmp)*1000-1;
    return result;
}
int getTimeNumber(const char* time,int offset){

    return getHours(time)+getMinutes(time)+getSeconds(time)+offset;
}
int solution(const char** lines,int lines_length){

    int max_process=0;

    for(int i=0;i<lines_length;i++){
        int tmp_max_process=1;
        
        int cur_min_time = getTimeNumber(lines[i],-getDuration(lines[i]));
        int cur_max_time = getTimeNumber(lines[i],0);
        
        for(int j=i-1;j>=0;j--){
            int prev_min_time = getTimeNumber(lines[j],-getDuration(lines[j]));
            int prev_max_time = getTimeNumber(lines[j],0);

            if(prev_max_time>cur_min_time-1000)tmp_max_process++;

            if(prev_min_time+3000<cur_min_time-1000)break;
        }

        if(max_process<tmp_max_process)max_process=tmp_max_process;
    }
    return max_process;
}
int main(int arg, char** args){
    const char * lines[2];
    lines[0] = "2016-09-15 01:00:04.002 2.0s";
    lines[1] = "2016-09-15 01:00:07.000 2.1s";
    int len = sizeof(lines)/sizeof(*lines);
    printf("\n%d",solution(lines,len));
    getchar();
    return 0;
}