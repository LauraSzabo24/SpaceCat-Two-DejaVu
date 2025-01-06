 


public interface MayGenericList<E>
{    
    public boolean addToEnd(E obj);
    public void add(int index, E obj);
    public boolean contains(E obj);
    public E get(int index);
    public boolean isEmpty();
    public E remove(int index);
    public boolean remove(E obj);
    public E set(int index, E obj);
    public int size();
}
