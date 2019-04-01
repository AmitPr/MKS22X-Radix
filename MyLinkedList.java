public class MyLinkedList<E> {
    private int size = 0;
    private Node first;
    private Node last;

    public MyLinkedList() {
        first=last=null;
    }
    public int getSize() {
        return size;
    }
    public void clear(){
        first=last=null;
    }
    public void addFirst(E data){
        Node n = new Node(data, first,null);
        first.setPrevious(n);
        first=n;
    }
    public void addLast(E data){
        Node n = new Node(data,null,last);
        last.setNext(n);
        last=n;
    }
    public void extend(MyLinkedList<E> other){
        Node n = other.getFirstNode();
        last.setNext(n);
        n.setPrevious(last);
        other.clear();
    }
    public Node getFirstNode(){
        return first;
    }
    public Node getLastNode(){
        return last;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Node cur = first;
        if(cur!=null){
            sb.append(cur.getData().toString());
        }else{
            sb.append(", ");
        }
        while(cur.getNext()!=null){
            sb.append(cur.getNext().toString()+", ");
            cur=cur.getNext();
        }
        sb.replace(sb.length()-2, sb.length(), "]");
        sb.append("]");
        return sb.toString();
    }
    private class Node {
        private E data;
        private Node next;
        private Node previous;

        public Node() {
            next = null;
            previous = null;
            data = null;
        }

        public Node(E data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
        public E getData(){
            return data;
        }
        public void setData(E data){
            this.data=data;
        }
        public Node getNext(){
            return next;
        }
        public void setNext(Node next){
            this.next=next;
        }
        public Node getPrevious(){
            return previous;
        }
        public void setPrevious(Node previous){
            this.previous=previous;
        }
    }
}