#include <stdio.h>
#include <stdlib.h>

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
    int k=0;
    int min = (set1->pointer)>(set2->pointer)?(set2->pointer):(set1->pointer);

    set* rset=setInitialize(min);

    
    while(i<min&&j<min){
        if((set1->start)[i]==(set2->start)[j]){
            *((rset->start)+k)=*(set1->start+i);
            i++;j++;k++;
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
set* getComplement(set* set1, set* universal){

    set* rset = getUnion(setInitialize(1),universal);
    for(int i=0;i<set1->pointer;i++){
        deleteElement(rset,*((set1->start)+i));
    }
    return rset;
}
set* getDifference(set* set1,set* set2){
    set* rset;
    set* tmp;
    set* tmp2;
    rset=getIntersect(set1,tmp2=getComplement(set2,tmp=getUnion(set1,set2)));
    //free(tmp);free(tmp2);

    return rset;
}

void freeSet(set* set){
    free(set->start);
    free(set);
}

int main(int argc, char** argv){
    
    set* set1 = setInitialize(10);
    set* set2 = setInitialize(10);
    set* universalSet;

    addElement(set1,1);addElement(set1,2);addElement(set1,3);addElement(set1,4);
    addElement(set2,3);addElement(set2,4);addElement(set2,5);addElement(set2,6);

    universalSet = getUnion(set1,set2);
    addElement(universalSet,7);addElement(universalSet,8);addElement(universalSet,9);

    printf("set 1 : ");printSet(set1);
    printf("set 2 : ");printSet(set2);
    printf("set U : ");printSet(universalSet);

    printf("Union set : ");printSet(getUnion(set1,set2));
    printf("Intersect set : ");printSet(getIntersect(set1,set2));
    printf("difference set(set1-set2) : ");printSet(getDifference(set1,set2));
    printf("complement of set1 : ");printSet(getComplement(set1,universalSet));
    

    return 0;
}