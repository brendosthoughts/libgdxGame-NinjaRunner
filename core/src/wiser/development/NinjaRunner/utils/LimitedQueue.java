package wiser.development.NinjaRunner.utils;

import java.util.LinkedList;


public class LimitedQueue<Vector2> extends LinkedList<Vector2> {
    private int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(Vector2 o) {
        super.add(o);
        while (size() > limit) { super.remove(); }
        return true;
    }
    
}