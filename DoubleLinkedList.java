import java.util.*;
public class DoubleLinkedList<E> implements GenericList<E>
{
    private Node<E> head;
    private int location;
    public int left;
    public int right;
    private CatDate[] dates;
    public DoubleLinkedList()
    {
        head = new Node<E>();
        location = 0;
        right = -1;
        left = 0;
    }
    public void setLoc(int lo)
    {
        location = lo;
    }
    public void redLoc()
    {
        location--;
    }
    public void incLoc()
    {
        location++;
    }
    public int getLoc()
    {
        return location;
    }
    public String[] colorList()
    {
        String[] colors = new String[size()]; 
        Node<E> n = head;
        int i = 0;
        while(n.getNext() != null)
        {
          n = n.getNext();
          colors[i] = (((WorldMap)n.getValue()).getColor());
          i++;
        }
        return colors;
    }
    public CatDate[] dateList()
    {
        CatDate[] dates = new CatDate[size()]; 
        Node<E> n = head;
        int i = 0;
        while(n.getNext() != null)
        {
          n = n.getNext();
          if(((WorldMap)n.getValue()).getHasCat())
          {
              dates[i] = (((WorldMap)n.getValue()).getCatDate());
          }
          i++;
        }
        return dates;
    }
    public void cleanWorlds()
    {
        Node<E> n = head;
        int i = 0;
        while(n.getNext() != null)
        {
          n = n.getNext();
          if(((WorldMap)n.getValue()).getHasCat())
          {
              ((WorldMap)n.getValue()).getCatDate().noHabla(true);
              if(((WorldMap)n.getValue()).getCatDate().isDated())
              {
                  
              }
              else
              {
                ((WorldMap)n.getValue()).getCatDate().setTransparency(0);
              }
          }
          i++;
        }
    }
    public E getCurrent()
    {
        System.out.println("location " + location);
        if(get(location)!=null)
        {
            return get(location);
        }
        return null;
    }
    public E getNext()
    {
        if(get(location+1)==null || ((location+1)>size()))
        {
            return null;
        }
        return get(location+1);
    }
    public E getPrevious()
    {
        if((location-1)<0)
        {
            return null;
        }
        return get(location-1);
    }
    public void printAll()
    {
      System.out.println("Linked List final loc " + location);
      Node<E> n = head;
      while(n.getNext() != null)
      {
          n = n.getNext();
          System.out.println("count " + n.getValue());
      }
    }
    public boolean addToStart(E obj)
    {
        left++;
        Node<E> n = head.getNext();       
        Node<E> pre = new Node<E>();
        pre.setValue(obj);
        
        pre.setNext(n);
        n.setPrevious(pre);
        
        head.setNext(pre);
        pre.setPrevious(head);
        
        System.out.println("value " + pre.getValue());
        return true;
    }
    public boolean addToEnd(E obj)
    {   
        right++;
        Node<E> n = head;
        Node<E> p = head;
        while(n.getNext()!=null)
        {
            p=n;
            n= n.getNext();
        }
        Node<E> next = new Node<E>();
        next.setValue(obj);
        n.setNext(next);
        n.setPrevious(p);
        return true;
    }
    public void add(int index, E obj)
    {
        if(index>=size() || index<0)
        {
            addToEnd(obj);
        }
        else{
            Node<E> p = head;
            Node<E> n = head;
            int count= 0;
            while(count<index)
            {
                p=n;
            n= n.getNext();
            count ++;
            }
            Node<E> next = new Node<E>();
            next.setValue(obj);
            next.setNext(n.getNext());
            next.setPrevious(n);
            n.setNext(next);
            n.setPrevious(p);
        }
    }
    public boolean contains(E obj)
    {
      Node<E> n = head;
      while(n.getNext() != null)
      {
              n = n.getNext();
              if(n.getValue() != null && n.getValue().equals(obj))
              {
                  return true;
              }
      }
      return false;
    }
    public E get(int index)
    {
        Node<E> n = head;
        if(index>=size() || index<0)
        {
            return null;
        }
        for(int i=0; i<=index; i++)
        {
            n = n.getNext(); 
        }
        return n.getValue();
    }
    public boolean isEmpty()
    {
        if(size()<=0)
        {
            return true;
        }
        return false;
    }
    public E remove(int index)
    {
        if(index>=size() || index<0)
        {
            return null;
        }
        Node<E> n = head;
        int count= 0;
        while(count<index)
        {
            n= n.getNext();
            count ++;
        }
        Node<E> deleted = n.getNext();
        n.setNext(deleted.getNext());
        return deleted.getValue();
    }
    public boolean remove(E obj)
    {
      Node<E> n = head;
      while(n.getNext() != null)
      {
              if(n.getNext().getValue() != null && n.getNext().getValue().equals(obj))
              {
                  n.setNext(n.getNext().getNext());
                  return true;
              }
              n = n.getNext();
      }
      //remove(size()-1);
      return false;
    }
    public E set(int index, E obj)
    {
        Node<E> n = head;
        if(index>=size() || index<0)
        {
            return null;
        }
        for(int i=0; i<=index; i++)
        {
            n = n.getNext(); 
        }
        E original = n.getValue();
        n.setValue(obj);
        return original;
    }
    public int size()
    {
      Node<E> n = head;
      int size = 0;
      while(n.getNext() != null)
      {
              size++; 
              n = n.getNext();
      }
      return size;
    }
    
    class Node<E>
    {
        private E value;
        private Node<E> next;
        private Node<E> previous;
        public E getValue()
        {
            return value;
        }
        public E setValue(E data)
        {
            value = data;
            return value;
        }
        public Node<E> getNext()
        {
            return next;
        }
        public Node<E> setNext(Node<E> node)
        {
            next = node;
            return next;
        }
        public Node<E> getPrevious()
        {
            return previous;
        }
        public Node<E> setPrevious(Node<E> node)
        {
            previous = node;
            return previous;
        }
    }
}