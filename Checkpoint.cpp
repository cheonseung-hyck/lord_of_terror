#include <stdio.h>
#include <stdlib.h>

struct stack{
    int pointer;
    int size;
    int* start;
};
struct set{
    int* start;
    int pointer;
    int size;
};
void printSet(set* set){
    for(int i=0;i<set->pointer;i++){
        printf("%d ",(set->start)[i]);
    }
    printf("\n");
}
int binarySearch(set* set,int v){

    int low=0;
    int high=(set->pointer)-1;
    int mid=(low+high)/2;
    while(high>low){
        
        if(*(set->start+mid)==v){
            break;
        }
        else if(*(set->start+mid)>v){
            high=mid-1;
        }
        else if(*(set->start+mid)<v){
            low=mid+1;
        }
        mid=(low+high)/2;
    }
    if(*(set->start+mid)!=v){
        
        if(*(set->start+mid)<v){
            mid++;
        }

        mid=(-1)*mid-1;
    }
    return mid;
}

void addElement(set* set,int v){
    int index;

    
    if((set->pointer)==0){
        *(set->start)=v;
        (set->pointer)++;
        return;
    }

    index=binarySearch(set,v);
    if(index<0){
        index=index*(-1)-1;
        for(int i=set->pointer;i>=index;i--){
            (set->start)[i+1]=(set->start)[i];
        }
        (set->start)[index]=v;
        (set->pointer)++;
    }
}
void deleteElement(set* set, int v){

     int index;
 
    if((set->pointer)==0)
        return;

    index=binarySearch(set,v);
    if(index>=0){
        for(int i=index;i<(set->pointer)-1;i++){
            (set->start)[i]=(set->start)[i+1];
        }
        (set->pointer)--;
    }

}
set* setInitialize(int size){
    set* mset = (set*)malloc(sizeof(set));
    mset->start = (int*)malloc(sizeof(int)*size);
    mset->size=size;
    mset->pointer=0;

    return mset;
}
set* getIntersect(set* set1,set* set2){
    int i=0;
    int j=0;
    int min = (set1->pointer)>(set2->pointer)?(set2->pointer):(set1->pointer);

    set* rset=setInitialize(min);

    
    while(i<set1->pointer&&j<set2->pointer){
        if((set1->start)[i]==(set2->start)[j]){
            addElement(rset,(set1->start)[i]);
            i++;j++;
        }
        else{
            if((set1->start)[i]>(set2->start)[j]){
                j++;
            }
            else if((set1->start)[i]<(set2->start)[j]){
                i++;
            }
        }
    }
    return rset;
}
set* getUnion(set* set1,set* set2){
    

    set* resset=setInitialize((set1->pointer)+(set2->pointer));

    for(int i=0;i<set1->pointer;i++){
        addElement(resset,*((set1->start)+i));
    }
    for(int i=0;i<set2->pointer;i++){
        addElement(resset,*((set2->start)+i));
    }
    return resset;
}

void freeSet(set* set){
    free(set->start);
    free(set);
}

void push(stack* stack, int v){
    
    *(stack->start+(stack->pointer))=v;
    (stack->pointer)++;  
}
int pop(stack* stack){
    (stack->pointer)--;
    return *(stack->start+(stack->pointer));
}
stack* stackInitialize(int size){
    stack* mstack = (stack*)malloc(sizeof(stack));
    mstack->start = (int*)malloc(sizeof(int)*size);
    mstack->pointer=0;
    mstack->size=size;

    return mstack;
}
void freeStack(stack* stack){

    free(stack->start);
    free(stack);
}

void DFS(int** adjMatrix,stack* stack,bool* visit, int start,int goal,int n,set* resSet){
    visit[start] = true;
    push(stack,start);
    if(start == goal){ 
        set* tmpSet = setInitialize(stack->pointer);
        for(int i=0;i<stack->pointer;i++){
            addElement(tmpSet,*((stack->start)+i));
        }
        if(resSet->pointer>0){
            *resSet = *getIntersect(tmpSet,resSet);
        }
        else{
            *resSet = *getUnion(tmpSet,setInitialize(1));
        }
        
        pop(stack);
        addElement(resSet,-1);
        //freeSet(tmpSet);
        return;
    }
    if(stack->pointer>n){
        pop(stack);
        return;
    }

    for(int i=1;i<n+1;i++){
        if(adjMatrix[start][i]==1 && !visit[i]){
            DFS(adjMatrix,stack,visit,i,goal,n,resSet);
            visit[i]=false;
        }           
    }
    pop(stack);
}

int main(int argc, char** args){
    
    int** adjMatrix;
    int n, m, s, e;
    int* start;
    int* end;

    scanf("%d %d",&n ,&m);

    adjMatrix = (int**)malloc(sizeof(int*)*(n+1));
    for(int i=0;i<n+1;i++){
        adjMatrix[i] = (int*)malloc(sizeof(int)*(n+1));
        for(int j=0;j<n+1;j++){
            adjMatrix[i][j]=0;
        }
    }
    for(int i=0;i<m;i++){
        int a,b;
        scanf("%d %d",&a,&b);
        adjMatrix[a][b]=1;
        adjMatrix[b][a]=1;
    }
    scanf("%d %d",&s,&e);
    start = (int*)malloc(sizeof(int)*s);

    for(int i=0;i<s;i++){
        scanf("%d",&start[i]);
    }
    getchar();
    end = (int*)malloc(sizeof(int)*e);
    for(int i=0;i<e;i++){
        scanf("%d",&end[i]);
    } 
    getchar();
    
    bool* visit = (bool*)malloc(sizeof(bool)*(n+1));
    stack* stack=stackInitialize(n);
    set* rset=setInitialize(n);

    for(int i=0;i<s;i++){
        for(int j=0;j<e;j++){

            for(int i=1;i<(n+1);i++){
                visit[i]=false;
            }

            DFS(adjMatrix,stack,visit,start[i],end[j],n,rset);
            stack->pointer=0;
        }
    }
    deleteElement(rset,-1);
    printf("%d\n",rset->pointer);
    printSet(rset);
    
    getchar();
    return 0;
}