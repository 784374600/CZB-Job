package com.fzubb.MyComponent;

import java.util.Collection;

public class Selector{
    LoopQueue<SelectedComponent> queue;
    LoopQueue.Node cur;
    public  Selector(SelectedComponent[] components){
         queue=new LoopQueue<>();
         queue.add(components);
    }
    public  Selector(Collection<SelectedComponent> components){
        queue=new LoopQueue<>();
        for (SelectedComponent com:components
             ) {
            queue.add(com);
        }
    }
    public   SelectedComponent next(){
        SelectedComponent first=null;
        SelectedComponent next=null;
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
