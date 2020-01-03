package com.fzubb.MyComponent;

public class LoopQueue<T>{
      public Node head;
      public Node tail;
      private   Node cur;
      boolean empty=true;
      public  T next(){
          if(empty)
              return  null;
          if(cur==null)
              cur = head;
          else  if(cur==tail)
              cur=head;
          else
              cur=cur.next;
          return  cur.value;
      }
      public   void add(T value){
             if(empty){
                 if(head==null)
                  head=new Node(null, null);
                  head.value=value;
                  tail=head;
                  empty=false;
             }else {
                   Node p=tail.next;
                   if(p==null) {
                       p = new Node(null, null);
                       tail.next=p;
                   }
                   p.value=value;
                   tail=p;
             }
      }
      public  void delete(T value){
             if(empty)
                 return;
             head.value=null;
             if(head==tail){
                empty=true;
             }else {
                  head=head.next;
             }
      }
      public  void add(T ...values){
          if(values!=null)
              for (int i=0;i<values.length;i++)
                  add(values[i]);
      }
      public  class  Node{
          T  value;
          Node next;

          public Node(T value, Node next) {
              this.value = value;
              this.next = next;
          }

          public T getValue() {
              return value;
          }

          public void setValue(T value) {
              this.value = value;
          }

          public Node getNext() {
              return next;
          }

          public void setNext(Node next) {
              this.next = next;
          }
      }
}
