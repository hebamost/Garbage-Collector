package paradigmproject2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class Markandsweep {
	 public ArrayList<object> heapArray; //heap.csv
	    private HashMap<Integer, object> heapHashMap;
	    private ArrayList<Integer> roots;

	    private Markandsweep(ArrayList<object> heapArray, HashMap<Integer, object> heapHashMap, ArrayList<Integer> roots){
	        this.heapArray = heapArray;
	        this.heapHashMap =heapHashMap;
	        this.roots=roots;
	    }
	    private void markobject ( object object ){
	        if( object.isMarked() ) return;
	        object.setMarked(true);
	        for( object child : object.getChildren() ) markobject(child);
	    }
	    public void mark (){
	        for( int rootId : roots ){
	            markobject(heapHashMap.get( rootId ));
	        }
	    }
	    public ArrayList<object> markandsweep(){
	        mark();
	        sweep();
	        return heapArray;
	    }
	    private void sweep(){
	        int p = 0;
	        for ( int i = 0 ; i < heapArray.size() ; i++ ){
	            object node = heapArray.get(i);
	            if( node.isMarked() ){
	                node.setMarked(false);
	            }
	            else{
	                heapArray.remove(node);
	                i--;
	            }
	            
	        }
	    }
	    public static void main(String[] args) { 
	    	if (args.length!=4) {
	    		System.out.println("Invalid");
	    		return;
	    	}
	        Markandcompact garbagecollector =new Markandcompact(args[0],args[1],args[2]);// to get input
	        var sweeping=new Markandsweep(garbagecollector.heapArray,garbagecollector.heapHashMap,garbagecollector.roots);
	        garbagecollector.storeHeap(sweeping.markandsweep() , args[3]);
	    }

}