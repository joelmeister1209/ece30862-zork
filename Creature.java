import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Creature {
	private String name;
	private String status;
	private String description;
	private Attack attack;
	private List<String> vulnerability; //may need to make some sort of class for this
	private List<Trigger> triggers;

	public String getName() { return this.name; }
	public String getStatus() { return this.status; }
	public String getDescription() { return this.description; }
	public Attack getAttack() {return this.attack; }
	public List<String> getVulnerabilities() { return this.vulnerability; }
	public List<Trigger> getTriggers() { return this.triggers; }
	
	public Creature() {
		vulnerability = new ArrayList<String>();
		triggers = new ArrayList<Trigger>();
	}	
	public Creature buildCreature(Node n_in){
		NodeList na = ((Element)n_in).getElementsByTagName("name");
		if(na.getLength() > 0) this.name = ((Element)na.item(0)).getTextContent();

		NodeList sta = ((Element)n_in).getElementsByTagName("status");
		//if(sta.getLength() > 0) this.status = ((Element)sta.item(0)).getTextContent();
		
		NodeList desc = ((Element)n_in).getElementsByTagName("description");
		if(desc.getLength() > 0) this.description = ((Element)desc.item(0)).getTextContent();
		
		NodeList att = ((Element)n_in).getElementsByTagName("attack");
		if(att.getLength() > 0 ) this.attack = new Attack( att.item(0) );

		NodeList vul = ((Element)n_in).getElementsByTagName("vulnerability");
		for (int i = 0; i < vul.getLength(); i++){
			String v =  new String(vul.item(i).getTextContent() );
			this.vulnerability.add( v);
		}
		
		NodeList trig = ((Element)n_in).getElementsByTagName("trigger");
		for (int i = 0; i < trig.getLength(); i++){
			Trigger t =  new Trigger(trig.item(i));
			this.triggers.add( t);
		}

		return this;
	}
}
