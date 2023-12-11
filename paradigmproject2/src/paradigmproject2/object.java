package paradigmproject2;
import java.util.ArrayList;
public class object {
	 int ob_identifier;
	   // int p_identifier;
	    //int ch_identifier;
	    int start;
	    int end;

	    int spaceTaken;
	    ArrayList<object> children=new ArrayList<>();
	    boolean marked;
	    object(int id,int mem_start,int mem_end){
	        this.ob_identifier=id;
	        this.start=mem_start;
	        this.end =mem_end;
	        this.spaceTaken=mem_end-mem_start+1;
	        marked=false;
	    }

	    public int getId() {
	        return ob_identifier;
	    }

	    public int getMemory_end() {
	        return end;
	    }

	    public void setMemory_end(int memory_end) {
	        this.end = memory_end;
	    }

	    public int getMemory_start() {
	        return start;
	    }

	    public void setMemory_start(int memory_start) {
	        this.start = memory_start;
	    }

	    public int getSpaceTaken() {
	        return spaceTaken;
	    }

	    public ArrayList<object> getChildren() {
	        return children;
	    }

	    public void addChild(object child) {
	        this.children.add(child);
	    }

	    public boolean isMarked() {
	        return marked;
	    }

	    public void setMarked(boolean marked) {
	        this.marked = marked;
	    }

}
