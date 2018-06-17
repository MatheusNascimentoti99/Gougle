package util;

public class Queue implements IQueue{

    private Celula head;
    private Celula tail;
    private int size;

    @Override
    public void add(Object data) {
        if(isEmpty()){
            head = tail = new Celula(data);
        }
        else{
        Celula novo = new Celula(data);
        tail.next = novo;
        tail = novo;
        }
        size++;
        
    }

    @Override
    public Object remove() {
        Celula temp = null;
        if(!isEmpty()){
            temp = head;
            if(head == tail){
                head = tail = null;
            }
            else
                head = head.next;
            size--;
 
        }
        return temp.data;
    }

    @Override
    public Object peek() {
        return isEmpty() ? null :head.data;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Celula {
        private Object data;
        private Celula next;

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Celula getNext() {
            return next;
        }

        public void setNext(Celula next) {
            this.next = next;
        }
        private Celula(Object data){
            this.data = data;
        }
    }
}
