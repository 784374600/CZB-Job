package com.fzubb.MyComponent;

import java.util.Collection;

public class Selector<T extends SelectedComponent>{
    LoopQueue<T> queue;
    public  Selector(T[] components){
         queue=new LoopQueue<>();
         queue.add(components);
    }
    public  Selector(Collection<T> components){
        queue=new LoopQueue<>();
        queue.add(components);
    }
    public   T next(){
        T first=null;
        T next=null;
        while (next==null||!next.selected()){
            synchronized (this) {
                next = queue.next();
            }
            if(first==null)
                first=next;
            else if(first==next)
                return  null;
        }
        return  next;
    }

}
