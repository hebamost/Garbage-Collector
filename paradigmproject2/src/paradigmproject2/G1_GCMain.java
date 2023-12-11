package paradigmproject2;

import java.io.IOException;

public class G1_GCMain {
    public static void main(String args[]) throws IOException {
    	        G1_GC n=new G1_GC();
    	        //n.G1(80,"heap.csv","roots.txt","pointers.csv","new_heapG1.csv");
    	        if(!args[0].isEmpty())
    	        {n.G1(Integer.parseInt(args[0]),args[1],args[2],args[3],args[4]);}
    	 


    }

}