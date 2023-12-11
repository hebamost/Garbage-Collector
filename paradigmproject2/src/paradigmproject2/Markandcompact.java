package paradigmproject2;

import java.util.HashMap;
import java.util.Scanner;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class Markandcompact {
	public ArrayList<object> heapArray;
    public HashMap<Integer, object> heapHashMap;
    public ArrayList<Integer> roots;
public ArrayList handleroots(String rootsfile) {
	ArrayList<Integer> roots = new ArrayList<Integer>();
    try {
        FileReader reader = new FileReader(rootsfile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            roots.add(Integer.parseInt(line));
        }
        reader.close();

    } catch (IOException e) {
        System.out.println("Error !");
    }
    return  roots; //return array list of roots
}
public static ArrayList<object> heapArray(String heapfile){
    ArrayList<object> heapArray = new ArrayList<object>();
    try (BufferedReader br = new BufferedReader(new FileReader(heapfile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            values[0] = values[0].replaceAll("[^0-9]+", "");
            heapArray.add(new object( Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]))); //id +start +end
        
        }
    } catch (FileNotFoundException e) {
        System.out.println("File Not Found !");
    } catch (IOException e) {
        System.out.println("Error !");
    }

    return  heapArray;
}

public static void handlepointers(String pointersfile , HashMap<Integer,object> heapHashMap){
    try (BufferedReader br = new BufferedReader(new FileReader(pointersfile))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            values[0] = values[0].replaceAll("[^0-9]+", "");
            heapHashMap.get(Integer.parseInt(values[0])).addChild(heapHashMap.get(Integer.parseInt(values[1])));
          
        }
    } catch (FileNotFoundException e) {
        System.out.println("File Not Found !");
    } catch (IOException e) {
        System.out.println("Error !");
    }
}
public static HashMap<Integer,object> createheaphash(ArrayList<object> heapArray ){
    HashMap<Integer,object> heapHash = new HashMap<Integer,object>();
    for( int i = 0 ; i < heapArray.size() ; i++ ){
        heapHash.put( heapArray.get(i).getId() , heapArray.get(i) );
    }
    return heapHash;
}

public Markandcompact(String heapfile , String rootsfile , String pointersfile) {
	
	 this.roots= handleroots(rootsfile);
     this.heapArray = heapArray(heapfile);
     this.heapHashMap =createheaphash(heapArray);
    handlepointers(pointersfile,heapHashMap);
}
public ArrayList<object> markandcompact(){
	 mark();
     compact();
     return heapArray;
	
}
private void mark (){
    for( int rootId : roots ){
        markobject(heapHashMap.get( rootId ));
    }
}
private void markobject ( object object ){
    if( object.isMarked() ) return;
    object.setMarked(true);
    for( object child : object.getChildren() ) markobject(child);
}
private void compact(){
    int p = 0;
    for ( int i = 0 ; i < heapArray.size() ; i++ ){
        object obj = heapArray.get(i);
        if( obj.isMarked() ){
            obj.setMemory_start(p);
            obj.setMemory_end( p + obj.getSpaceTaken() - 1 );
            p += obj.getSpaceTaken();
        }
        else{
            heapArray.remove(obj);
            i--;
        }
    }
}

public static File storeHeap( ArrayList<object> heapArray , String fileName ){
    File newHeapFile =null;
    try (PrintWriter writer = new PrintWriter(new File(fileName))) {
        for( object obj : heapArray ){
            StringBuilder line = new StringBuilder();
            line.append(String.valueOf(obj.getId()));
            line.append(',');
            line.append(String.valueOf(obj.getMemory_start()));
            line.append(',');
            line.append(String.valueOf(obj.getMemory_end()));
            line.append('\n');
            writer.write(line.toString());
            System.out.println(line.toString());
        }



    } catch (FileNotFoundException e) {
        System.out.println("File Not Found !");
    }
    return newHeapFile;
}


public static void main(String[] args) {
	if( args.length != 4 ){
        System.out.println("Invalid number of arguments!");
        return;
    }

//	Markandcompact garbagecollector =new Markandcompact("C:\\Users\\Kimo Store\\Desktop\\heap.csv", "C:\\Users\\Kimo Store\\Desktop\\roots.txt" , "C:\\Users\\Kimo Store\\Desktop\\pointers.csv");
//  storeHeap(garbagecollector.markandcompact() , "C:\\Users\\Kimo Store\\Desktop\\newheap.csv");
	Markandcompact garbagecollector =new Markandcompact(args[0], args[1] , args[2]);
	  storeHeap(garbagecollector.markandcompact() , args[3]);

}

}


