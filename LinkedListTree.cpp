#include <stdio.h>
#include <stdlib.h>
#include <string>
template <class V, class K>
struct Node{
    V data;
    K key;
    Node *left = NULL;
    Node *right = NULL;
};

template <class V, class K>
class ListTree{
public:
    Node<V, K> *root = NULL;

    Node<V,K>* searchNode(K k, int idx=0){
        int curIdx=0;
        Node<V, K>** nodeArr = new Node<V, K>*[500]; //can load upto 2^500
        Node<V, K> *curNode = root;
  
        while (true){
            nodeArr[curIdx++]=curNode;
            if (k < curNode->key){
                if (curNode->left == NULL)
                    break;
                else
                    curNode = curNode->left;
            }
            else if (k > curNode->key){
                if (curNode->right == NULL)
                    break;
                else
                    curNode = curNode->right;
            }
            else
                break;
        }
        return nodeArr[curIdx-idx-1];
    }



    void insertData(K k, V v){
        Node<V, K> *newNode = new Node<V, K>;
        newNode->data = v;
        newNode->key = k;

        if(root==NULL)
            root=newNode;
        else{
            Node<V,K>* snode = searchNode(k);

            if(snode->key>k)
                snode->left=newNode;
            else if(snode->key<k)
                snode->right=newNode;
            else
                printf("duplicate key.. try another key..\n");
        }
    }
    V searchData(K k){
        Node<V,K> *node = searchNode(k);
        if(node->key==k)
            return node->data;
        else{
            printf("can't find data...\n");
            return NULL;
        }
    }
    void DeleteData(K k){
        Node<V,K> *snode = searchNode(k);

        
        if(snode->key==k){
            if(snode->left==NULL||snode->right==NULL){
                if(snode==root){
                    if(snode->left!=NULL)root=snode->left;
                    else if(snode->right!=NULL)root=snode->right;
                    else root=NULL;
                    return;
                }

                Node<V,K> *parentNode = searchNode(k,1);
                if(parentNode->key>k){
                    if(snode->left!=NULL)
                        parentNode->left=snode->left;
                    else if(snode->right!=NULL)
                        parentNode->left=snode->right;
                    else
                        parentNode->left=NULL;
                }
                else if(parentNode->key<k){
                    if(snode->left!=NULL)
                        parentNode->right=snode->left;
                    else if(snode->right!=NULL)
                        parentNode->right=snode->right;
                    else
                        parentNode->right=NULL;
                }
            }
            else{
                Node<V,K> *ssnode=snode;
                ssnode=ssnode->right;
                while(ssnode->left!=NULL){
                    ssnode=ssnode->left;
                }
                Node<V,K> *ssparentNode = searchNode(ssnode->key,1);
                if(ssparentNode==snode)
                    ssparentNode->right = ssnode->right;
                else
                    ssparentNode->left = ssnode->right;

                snode->key=ssnode->key;
                snode->data=ssnode->data;
            }
        }
        else{
            printf("can't find data!");
        }
    }
    
};
template <class V, class K>
void inorderSearch(Node<V,K> *node){

    if(node==NULL)return;

    inorderSearch(node->left);
    printf("%d %s\n",node->key, node->data);
    inorderSearch(node->right);

}
int main(int arg, char **args){

    ListTree<char *, int> myTree;
    char ar1[12] = "I want you";
    char ar2[12] = "I hate you";
    char ar3[12] = "I owen you";
    char ar4[12] = "I miss you";
    char ar5[12] = "I flex you";

    myTree.insertData(11, ar1);
    myTree.insertData(7, ar2);
    myTree.insertData(3, ar3);
    myTree.insertData(5, ar4);
    myTree.insertData(9, ar5);

    inorderSearch(myTree.root);
    myTree.DeleteData(7);
    printf("\n");
    inorderSearch(myTree.root);
    myTree.DeleteData(3);
    myTree.DeleteData(5);
    myTree.DeleteData(11);
    printf("\n");
    inorderSearch(myTree.root);


    getchar();
    return 0;
}

