package paradigmproject2;
import java.io.*;
import java.util.*;

public class G1_GC {
   // private ArrayList<object> heapArray;
    //private HashMap<Integer, object> heapHashMap;
    private ArrayList<String> roots=new ArrayList<>();
    private HashMap<String, object> heap = new HashMap<>();
    private HashMap<String, List<String>> pointers = new HashMap<>();
    public void G1(int heapSize, String heapPath,String rootsPath,String pointersPath,String Out_heap) throws IOException{
        int regions=16;
        //size of each cell in the new division
        int blocks=heapSize/regions;
        ArrayList<object>[] heapRegions=new ArrayList[regions];

        String line="";
        //heap file---------------------------------
        try{
            BufferedReader b=new BufferedReader(new FileReader(heapPath));
            //traversing heap file
            while((line=b.readLine())!=null){
                String[] single=line.split(",");
                  object j=new object(Integer.parseInt(single[0]),Integer.parseInt(single[1]),Integer.parseInt(single[2]));
                  heap.put(single[0],j); //heap elements in list
            }
        }catch(IOException exception){ exception.printStackTrace(); }
        //PointersFile----------------------------------------------
        try{
            BufferedReader b=new BufferedReader(new FileReader(pointersPath));
            //traversing heap file
            while((line=b.readLine())!=null){
                String[] single=line.split(",");
                if(!pointers.containsKey(single[0])){
                    pointers.put(single[0],new ArrayList<>());
                }
                List<String > value=pointers.get(single[0]);
                value.add(single[1]);
                pointers.put(single[0],value);

            }
        }catch(IOException exception){ exception.printStackTrace(); }

        //roots file-------------------------------------------------
        try{
            BufferedReader b=new BufferedReader(new FileReader(rootsPath));
            //traversing roots file
            while((line=b.readLine())!=null){
                roots.add(line);

            }
        }catch(IOException e){e.printStackTrace();}
        //====================================================
        //lists in each node of the array
        for(int i=0;i<regions;i++){
             heapRegions[i]=new ArrayList<>();
        }
        List<String> nodesUsed=NodesInUse();

        //store elements of heap file in the divided heap regions
        for(String n:nodesUsed){
            object value=heap.get(n);
            int newStart=value.getMemory_start()/blocks;
            heapRegions[newStart].add(value);

        }
        //sorting the heaap regions according to space taken
        for(int j=0;j<regions;j++){
            heapRegions[j].sort((i,k)->k.getMemory_start()-i.getMemory_start());
        }

        int firstEmpty=-1;
        //searching for the first empty cell in heap regions
        for(int i=0;i<regions;i++){
            if(heapRegions[i].isEmpty()){
                firstEmpty=i;
                break;
            }
        }
        //new result heap
        ArrayList<object>[] result=new ArrayList[regions];
        for(int i=0;i<regions;i++){
           result[i]=new ArrayList<>();
        }
        //array to save the space taken by objects in each cell of heap in the same order
        int[] res=new int[regions];
        int space;
        //traverse the heap array
        for(int j=0;j<regions;j++){
            if(!heapRegions[j].isEmpty()){ //to check if the heap is empty
                for(object node:heapRegions[j]){
                    //traversing lists found  in each cell of
                    // the heap array according to the objects
                    if(firstEmpty!=-1){  //check if all heap cells is full
                        for(int k=firstEmpty;k<regions;k++){
                            space=node.getSpaceTaken();
                            if(res[k] < blocks && blocks-res[k]>=space ){
                               // System.out.println("index:////////// " );
                                if(result[k].isEmpty()){
                                    //first element in any cell of result heap
                                    node.setMemory_start(k*blocks);
                                    node.setMemory_end(node.getMemory_start()+space-1);
                                    result[k].add(node);
                                }
                                else{
                                    int start=result[k].get(result[k].size()-1).getMemory_end();
                                    node.setMemory_start(start+1);
                                    node.setMemory_end(node.getMemory_start()+space-1);
                                    result[k].add(node);
                                }
                                res[k]+=space;
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < regions; i++) {
            System.out.println("index: " + i);
            for(object n:result[i]){
               System.out.println("node: " + n.getId());
                System.out.println("start: " + n.getMemory_start());
                System.out.println("end: " + n.getMemory_end());
            }
        }
        //to write new heap.csv file
        try{
            BufferedWriter b=new BufferedWriter(new FileWriter(Out_heap));
            StringBuilder new_heap=new StringBuilder();
            for(int i=0;i<regions;i++){
                if(!result[i].isEmpty()){
                    for(object node:result[i]){
                        new_heap.append(node.getId());
                        new_heap.append(",");
                        new_heap.append(node.getMemory_start());
                        new_heap.append(",");
                        new_heap.append(node.getMemory_end());
                        new_heap.append("\n");
                    }
                }

            }
            b.write(new_heap.toString());
            b.flush();

        }catch(IOException e){e.printStackTrace();}
    }

    private List<String> NodesInUse() {
        // dfs graph list for nodes related to that in use
        Set<String> nodes = new HashSet<>();

        for (String s : roots) {
            dfs(s, nodes);
        }
        return new LinkedList<>(nodes);
    }

    private void dfs(String node, Set<String> set) {
        set.add(node);

        if (pointers.containsKey(node)) {
            for (String child : pointers.get(node)) {
                if (!set.contains(child))
                    dfs(child, set);
            }
        }
    }
}
