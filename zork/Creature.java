import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Creature {
	public Creature() { }
	public Creature(NodeList nodeList) {
		// TODO Auto-generated constructor stub
	}
	private String name;
	private String status;
	private String description;
	private String[] vulnerability; //may need to make some sort of class for this
	private Attack attack;
	private Trigger[] triggers;
}
