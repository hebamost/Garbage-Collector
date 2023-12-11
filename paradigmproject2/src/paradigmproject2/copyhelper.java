package paradigmproject2;
import java.io.*;
import java.util.*;

public class copyhelper {
	public static ArrayList<object> newHeap = new ArrayList<>();
    public static HashSet<Integer> moved = new HashSet<>();
    public static ArrayList<object> copyAlgorithm(){
        int start=0,end;
        moved.addAll(Copy.roots);
        while (!Copy.roots.isEmpty()){
            int temp = Copy.roots.get(0);
            end = start + Copy.heap.get(temp).get(1)-Copy.heap.get(temp).get(0);
            object obj = new object(temp,start,end);
            start = end+1;
            newHeap.add(obj);
            Copy.roots.remove(0);
            if(!Copy.pointers.containsKey(temp)){
                continue;
            }
            for(int j=0;j<Copy.pointers.get(temp).size();j++){
                if(!moved.contains(Copy.pointers.get(temp).get(j))){
                    Copy.roots.add(Copy.pointers.get(temp).get(j));
                    moved.add(Copy.pointers.get(temp).get(j));
                }
            }
        }
        return newHeap;
    }

}
