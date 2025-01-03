import java.util.*;

public class Memory
{
    private LinkedList<int[]> moves;
    public Memory()
    {
        
    }
    public void memorize(int[] movement)
    {
        moves.add(movement);
    }
}