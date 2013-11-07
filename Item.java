import org.w3c.dom.*;
import java.util.ArrayList;
import java.util.List;

public class Item {
	private String name;
	private String status;
	private String description;
	private String writing;
	private TurnOn turn_on;
	private List<Trigger> triggers;

	public Item() {	
		triggers = new ArrayList<Trigger>();
	}
	public Item buildItem(Node n_in) {
		if(n_in == null) return this;
		NodeList na = ((Element)n_in).getElementsByTagName("name");
		if(na.getLength() > 0)
			this.name = ((Element)na.item(0)).getTextContent();
		NodeList stat = ((Element)n_in).getElementsByTagName("status");
		if(stat.getLength() > 0)
			this.status = ((Element)stat.item(0)).getTextContent();
		NodeList desc = ((Element)n_in).getElementsByTagName("description");
		if(desc.getLength() > 0)
			this.description = ((Element)desc.item(0)).getTextContent();
		NodeList wr = ((Element)n_in).getElementsByTagName("writing");
		if(wr.getLength() > 0)
			this.writing = ((Element)wr.item(0)).getTextContent();
		return this;
	}
	public String getName() { return this.name; }
	public String getWriting() { return this.writing; }
	
}
