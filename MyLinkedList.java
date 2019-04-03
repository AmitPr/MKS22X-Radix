import java.util.Iterator;

public class MyLinkedList<E> implements Iterable<E> {
    private int size = 0;
    private Node first;
    private Node last;

    public Iterator<E> iterator() {
        return new MyIterator(this);
    }

    public MyLinkedList() {
        first = last = null;
    }

    public int getSize() {
        return size;
    }

    public void clear() {
        first = last = null;
        size = 0;
    }

    public void addFirst(E data) {
        Node n = new Node(data, first, null);
        if (first == null) {
            first = last = n;
        } else {
            first.setPrevious(n);
            first = n;
        }
        size++;
    }

    public void addLast(E data) {
        Node n = new Node(data, null, last);
        if (last == null) {
            first = last = n;
        } else {
            last.setNext(n);
            last = n;
        }
        size++;
    }

    public void extend(MyLinkedList<E> other) {
        Node n = other.getFirstNode();
        if (n != null) {
            if (last == null) {
                first = n;
                last = other.getLastNode();
            } else {
                last.setNext(n);
                n.setPrevious(last);
                last = other.getLastNode();
            }
            size += other.getSize();
        }
        other.clear();
    }

    public E removeFirst() {
        E data = first.data;
        if (first.next != null) {
            first.next.setPrevious(null);
            first = first.next;
        } else {
            first = null;
            last = null;
        }
        size--;
        return data;
    }

    public Node getFirstNode() {
        return first;
    }

    public Node getLastNode() {
        return last;
    }

    public E getFirst() {
        if (first != null) {
            return first.data;
        }
        return null;
    }

    public E getLast() {
        if (last != null) {
            return last.data;
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node cur = first;
        if (cur != null) {
            sb.append(cur.getData().toString() + ", ");
            while (cur.getNext() != null) {
                sb.append(cur.getNext().getData().toString() + ", ");
                cur = cur.getNext();
            }
        } else {
            sb.append(", ");
        }
        sb.replace(sb.length() - 2, sb.length(), "]");
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

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }

    private class MyIterator implements Iterator<E> {
        Node current = null;

        public MyIterator(MyLinkedList<E> list) {
            current = list.first;
        }

        public boolean hasNext() {
            if (current != null) {
                return true;
            }
            return false;
        }

        public E next() {
            E n = current.data;
            current = current.next;
            return n;
        }
    }
}