import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Attack {
	String print;
	Condition condition;
	List<String> actions;
	public Condition getCondition(){
		return this.condition;
	}
	public String getPrint() {
		return this.print;
	}
	public List<String> getActions(){
		return this.actions;
	}
	public Attack(Node n_in) {
		actions = new ArrayList<String>();
		
		NodeList pr = ((Element)n_in).getElementsByTagName("print");
		if(pr.getLength() > 0) this.print = ((Element)pr.item(0)).getTextContent();

		NodeList cond = ((Element)n_in).getElementsByTagName("condition");
		this.condition = new Condition(cond);
		
		NodeList act = ((Element)n_in).getElementsByTagName("action");
		for (int i = 0; i < act.getLength(); i++){
			String s =  new String(act.item(i).getTextContent());
			this.actions.add(s);
		}

		
	}

}
