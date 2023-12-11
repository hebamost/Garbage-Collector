package paradigmproject2;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Copy {
	public static HashMap<Integer,ArrayList<Integer>> heap = new HashMap<>();
    public static HashMap<Integer,ArrayList<Integer>> pointers = new HashMap<>();
    public static ArrayList<Integer> roots = new ArrayList<>();
    void readFiles(String path) throws IOException{
        try{
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNext()){
                String[] str = sc.next().split(",");
                ArrayList<Integer> arr=new ArrayList<>();
                for(int i=0;i<str.length;++i){
                    str[i]=str[i].replaceFirst("﻿", "");
                    if(str[i].length()>6){str[i]=str[i].substring(1);}
                    arr.add(Integer.parseInt(str[i]));
                }
                if(str.length==3){
                    ArrayList<Integer> array=new ArrayList<>();
                    array.add(arr.get(1));
                    array.add(arr.get(2));
                    System.out.println("Name :"+array.get(0));
                    heap.put(arr.get(0),array);
                }
                else if(str.length==2){
                    if(!pointers.containsKey(arr.get(0))){
                        pointers.put(arr.get(0),new ArrayList<Integer>());
                    }
                    pointers.get(arr.get(0)).add(arr.get(1));
                    System.out.println(pointers.get(arr.get(0)));
                }
                else{
                    roots.add(arr.get(0));
                }
            }
            sc.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
      void writeToFile(String path){
        ArrayList<object> newHeap = copyhelper.copyAlgorithm();
        try {
            FileWriter writer = new FileWriter(path);
            for (object object : newHeap){
                writer.write(object.getId() + "," + object.getMemory_start() + "," + object.getMemory_end() + "\n");
            }
            writer.close();
            System.out.println("Writing to file has finished");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        if(args.length != 4){
            System.out.println("Invalid number of arguments!!! ");
            return;
        }
        Copy m = new Copy();
        m.readFiles(args[0]);
        m.readFiles(args[1]);
        m.readFiles(args[2]);
        m.writeToFile(args[3]);
    }
}
