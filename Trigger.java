import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Trigger {
	String type;
	String command;
	String print;
	Condition condition;
	public Trigger(Node n_in){
		this.type = "single";
        Element trig = (Element) n_in;
		NodeList t = ((Element)trig).getElementsByTagName("type");
		if(t.getLength() > 0) this.type = ((Element)t.item(0)).getTextContent();
		NodeList com = ((Element)trig).getElementsByTagName("command");
		if(com.getLength() > 0) this.command = ((Element)com.item(0)).getTextContent();
		NodeList pr = ((Element)trig).getElementsByTagName("print");
		if(pr.getLength() > 0) this.print = ((Element)pr.item(0)).getTextContent();
		
		NodeList cond = ((Element)trig).getElementsByTagName("condition");
		this.condition = new Condition(cond);
	}
}
