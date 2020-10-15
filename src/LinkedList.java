/*
    This LinkedList (LL) data structure is singly linked and contains the data members head (first node), tail (last node),
    and size (number of nodes in the list).  Instantiations of this LL class contain instance methods that can add,
    remove, get, and reverse the nodes of the LinkedList.  The nodes of this LL class are privately defined and instantiated
    within this LL class.
*/
public class LinkedList<E> {
    public Node<E> head; //first node in the LL
    public Node<E> tail; //last node in the LL
    public int size; //number of nodes in the LL
    /*
        PARAMS: none
        DESCRIPTION: Constructor; initially points head and tail to null since no nodes in this LinkedList exist
        RETURN: none
    */
    public LinkedList() {
        this.head = this.tail = null; //set head and tail to null b/c LL is initially empty
        this.size = 0; //set size to 0 b/c LL is initially empty
    }
    /*
        PARAMS: none
        DESCRIPTION: Overriden toString() method; creates string representation of the LL
        RETURN: none
    */
    @Override
    public String toString() {
        String s = "";
        //loop through LL
        for(Node<E> currentNode = this.head; currentNode != null; currentNode = currentNode.next) {
            s = s + currentNode.value;
            if(currentNode.next != null) {
                //append to list in loop
                s = s + "--> ";
            }
        }
        //return string representation of LL
        return s;
    }
    /*
        PARAMS:
            * E item - data of type E that will be added to the LL as a Node object
        DESCRIPTION: adds a new node at the end of the LL
        RETURN: none
    */
    public void add(E item) {
        Node<E> node = new Node<>(item);
        //if head is null, then list is empty, so add at the head
        if(this.head == null) {
            this.head = node;
            this.tail = node;
        }
        //if head is not null, then list is empty, so add at tail
        else {
            this.tail.next = node; //set current tail's next to the new node
            this.tail = node; //set current tail to the new node
        }
        size++; //increment size
    }
    /*
    PARAMS:
        * E item - data of type E that will be added to the LL as a Node object
        * int position - the location where the new node is to be inserted in the LL
    DESCRIPTION: adds a new node at the end of the LL; throws exception if position is invalid
    RETURN: none
    */
    public void add(E item, int position) throws Exception {
        //if position is invalid, throw exception
        if(position < 0 || position >= this.size) {
            throw new Exception("Position " + position + " out of bounds for size " + this.size);
        }
        Node<E> newNode = new Node<>(item);
        //if position is 0, then add at the head
        if(position == 0) {
            Node<E> temp = this.head; //create temp node to preserve current head node
            this.head = newNode; //set head to the new node
            this.head.next = temp; //set head's next to the previous head
        }
        else {
            Node<E> currentNode = this.head;
            //loop through LL until the node before the specified position
            for (int i = 0; i < position - 1; i++) {
                currentNode = currentNode.next;
            }
            //current node becomes the node right before the node at the specified position
            //this section of the code places the new node in front of the current node, and then places the previous current node's next in front of the new node
            newNode.next = currentNode.next; //set the new node's next to the current node's next
            currentNode.next = newNode; //set current node's next to the new node
        }
        size++; //increment size
    }
    /*
        PARAMS:
            * int position - the location of the node to be returned
        DESCRIPTION: returns the value of the node at the specified position; throws exception if position is invalid or LL is empty
        RETURN: returns the value of the node at the specified position
    */
    public E get(int position) throws Exception {
        //throw exception if LL is empty
        if(this.head == null) {
            throw new Exception("Cannot get nodes from an empty list");
        }
        //throw exception if position is invalid
        if(position < 0 || position >= this.size) {
            return null;
        }
        //loop through LL until the node at the specified position is reached
        Node<E> currentNode = this.head;
        for(int i = 0; i < position; i++) {
            currentNode = currentNode.next;
        }
        //return value of node at the specified position
        return currentNode.value;
    }
    /*
        PARAMS:
            * int position - location of the node to be removed in the LL
        DESCRIPTION: removes a node at the specified position while maintaining the order of the LL; throws exception if LL is empty or position is invalid
        RETURN: returns the value of the node that was removed
    */
    public E remove(int position) throws Exception {
        //throw exception if LL is empty
        if(this.head == null) {
            throw new Exception("Cannot remove nodes from an empty list");
        }
        //throw exception is position is invalid
        if(position < 0 || position >= this.size) {
            throw new Exception("Position " + position + " out of bounds for size " + this.size);
        }
        //if position is 0, remove at the head
        if(position == 0) {
            Node<E> temp = this.head; //create temp node to preserve current head
            this.head = temp.next; //set current head to temp's next (the previous head will be garbage collected)
            size--; //decrement size
            return temp.value;
        }
        //loop through LL until the node before the node at the specified position
        Node<E> currentNode = this.head;
        for(int i = 0; i < position - 1; i++) {
            currentNode = currentNode.next;
        }
        //currentNode becomes the node right before the node at the specified position
        //the code below makes currentNode's next point to currentNode.next.next, thus making the node at the specified position get garbage collected/de-referenced
        Node<E> temp = currentNode.next; //preserve current node's next in a temp variable
        currentNode.next = temp.next; //set currentNode's next to temp's next (temp gets garbage collected)
        size--; //decrement size
        return temp.value;
    }
    /*
        PARAMS:
            * E head - value of head
        DESCRIPTION: reverses the LL from tail to head; throws exception if LL is empty
        RETURN: value at new head
    */
    public E reverse(E head) throws Exception {
        //throw exception if LL is empty
        if(this.head == null) {
            throw new Exception("Cannot reverse nodes of an empty list.");
        }
        //initialize necessary nodes in order to walk through the LL while maintaining, advancing, and referencing the proper nodes
        Node<E> currentNode = this.head;
        Node<E> prevNode =  null, nextNode = null;
        while(currentNode != null) {
            nextNode = currentNode.next; //advance nextNode
            currentNode.next = prevNode; //point currentNode.next to prevNode (establishes links for when the list is reversed)
            prevNode = currentNode; //advance prevNode
            currentNode = nextNode; //advance nextNode
        }
        this.head = currentNode = prevNode; //set head
        return this.head.value; //return head's value
    }

    private static class Node<E> {
        Node<E> next; //points to this node's next node
        E value; //value of the node (type E -- can be any data type except primitives)
        /*
            PARAMS:
                * E value - value to be stored within the node
            DESCRIPTION: constructor; constructs a new node object; sets next to null and value to the value parameter
            RETURN: none
       */
        public Node(E value) {
            this.next = null;
            this.value = value;
        }
        //getters and setters -- the rubric said to have them, but I never really had the need to use it since its data members are public
        public void setValue(E value) {
            this.value = value;
        }
        public E getValue() {
            return this.value;
        }
        public void setNext(E value) {
            this.next = new Node<E>(value);
        }
        public Node<E> getNext() {
            return this.next;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("~~ LINKEDLIST ~~");
        LinkedList<String> LL = new LinkedList<>();
        System.out.println("Adding 5 strings to the LinkedList.");
        LL.add("test1");
        LL.add("test2");
        LL.add("test3");
        LL.add("test4");
        LL.add("test5");
        System.out.println("Current Nodes: " + LL.toString());
        System.out.println("LinkedList Size: " + LL.size);
        System.out.println("----------");
        System.out.println("Adding a string at position 3");
        LL.add("yooooo", 3);
        //LL.add("hello", LL.size-1);
        System.out.println("Current Nodes: " + LL.toString());
        System.out.println("LinkedList Size: " + LL.size);
        System.out.println("----------");
        System.out.println("Removing at position 2");
        //System.out.println("Removed: " + LL.remove(0));
        System.out.println("Removed: " + LL.remove(2));
        //System.out.println("Removed: " + LL.remove(LL.size-1));
        System.out.println("Current Nodes: " + LL.toString());
        System.out.println("LinkedList Size: " + LL.size);
        System.out.println("----------");
        System.out.println("Getting value at position 1: " + LL.get(1));
        System.out.println("Current Nodes: " + LL.toString());
        System.out.println("LinkedList Size: " + LL.size);
        System.out.println("----------");
        LL.reverse("");
        System.out.println("Reversing the LinkedList: " + LL.toString());
    }
}
