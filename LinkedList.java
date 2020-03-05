

class Node<V, K>{

    V value;
    K key;
    Node<V,K> next;
    public Node(V v, K k){
        this.value=v;
        this.key=k;
    }
}
public class LinkedList<V, K>{

    Node<V,K> tail;
    Node<V,K> head;

    public void insert(V v, K k){
        Node<V,K> newNode = new Node<V,K>(v,k);
        if(head==null){
            head=newNode;
            tail=newNode;
        }
        else{
            tail.next=newNode;
            tail=newNode;
        }
    }
    public V search(K k){
        Node<V,K> sNode=head;
        while(sNode!=null){
            if(k==sNode.key)
                return sNode.value;
            else
                sNode=sNode.next;
        }
        return null;
    }
    public void delete(K k){
        if(head==null){
            System.out.println("empty list");
            return;
        }
        if(head.key==k){
            head=head.next;
            return;
        }
        Node<V,K> sNode=head;
        while(sNode.next!=null){
            if(sNode.next.key==k){
                if(tail==sNode.next)tail=sNode;
                sNode.next=sNode.next.next;
            }
            else
                System.out.println("can't find the data");
        }
        
    }
    public void iterate(){
        Node<V,K> sNode=head;
        while(sNode!=null){
            System.out.println(sNode.key+" "+sNode.value);
            sNode=sNode.next;
        }
    }
    public static void main(String[] args){

        LinkedList<String, Integer> myList = new LinkedList<String,Integer>();
        myList.insert("The Sky is Blue",1);
        myList.insert("The Roses are Red",2);
        myList.insert("And you are still blabla",3);

        myList.iterate();
        
        myList.delete(1);

        System.out.println();
        myList.iterate();

        System.out.println();
        System.out.println(myList.search(2));
        System.out.println();
        myList.search(1);
    }
}