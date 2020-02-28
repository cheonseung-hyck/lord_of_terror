#include <stdio.h>
#include <math.h>
#define DZERO 0.00000004

double getDistance(int x1,int y1,int x2,int y2){
    
    return sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
}
int main(int arg, char** args){

    int n;

    scanf("%d",&n);
    int res_arr[n];
    int prb_arr[n][6];

    for(int i=0;i<n;i++){
        int x1,y1,r1,x2,y2,r2;
        scanf("%d %d %d %d %d %d",&x1,&y1,&r1,&x2,&y2,&r2);
        prb_arr[i][0]=x1;prb_arr[i][1]=y1;
        prb_arr[i][2]=r1;prb_arr[i][3]=x2;
        prb_arr[i][4]=y2;prb_arr[i][5]=r2;
    }
    for(int i=0;i<n;i++){
        int result;
        int x1,y1,r1,x2,y2,r2;

        x1=prb_arr[i][0];y1=prb_arr[i][1];
        r1=prb_arr[i][2];x2=prb_arr[i][3];
        y2=prb_arr[i][4];r2=prb_arr[i][5];
        
        double Tdistance = getDistance(x1,y1,x2,y2);
        double Rdistance = r1+r2;
        double diffDistance = Tdistance-Rdistance;

        if(abs(Tdistance)<DZERO&&r1==r2)result=-1;
        else if(diffDistance>DZERO||abs(r1-r2)>Tdistance)result=0;
        else if(abs(diffDistance)<DZERO||abs(r1-r2)==Tdistance)result=1;
        else if(diffDistance<-DZERO)result=2;
        res_arr[i]=result;

    }
    for(int i=0;i<n;i++){
        printf("%d\n",res_arr[i]);
    }
    return 0;

}