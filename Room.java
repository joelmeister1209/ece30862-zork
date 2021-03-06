import java.util.ArrayList;
import org.w3c.dom.*;

import java.util.List;

public class Room {
	private String name;
	private String status;
	private String type = "regular";
	private String description;
	public List<Border> border;
	private List<String> containers;
	private List<String> items; //just has item names
	private List<String> creatures;
	private List<Trigger> triggers;

	public List<Trigger> getTriggers(){
		return this.triggers;
	}
	public List<String> getItemList(){
		return this.items;
	}
	public List<String> getContainers(){
		return this.containers;
	}
	public List<String> getCreatures(){
		return this.creatures;
	}
	public void addItem(String i){
		this.items.add(i);
	}
	public void takeItem(String i){
		this.items.remove(i);
	}
	public Border getBorder(String s){
		for(int i = 0; i < border.size(); i++){
			if(s.equals( border.get(i).getDirection() )){
				return border.get(i);
			}
		}
		return null;
	}
	Room(){
		border = new ArrayList<Border>();
		containers = new ArrayList<String>();
		items = new ArrayList<String>();
		creatures = new ArrayList<String>();
		triggers = new ArrayList<Trigger>();
	}
	public Room buildRoom(Node n_in){
		NodeList na = ((Element)n_in).getElementsByTagName("name");
		this.name = ((Element)na.item(0)).getTextContent();
		NodeList ty = ((Element)n_in).getElementsByTagName("type");
		this.type = ((Element)ty.item(0)).getTextContent();
		NodeList desc = ((Element)n_in).getElementsByTagName("description");
		this.description = ((Element)desc.item(0)).getTextContent();
		NodeList bord = ((Element)n_in).getElementsByTagName("border");
		for (int i = 0; i < bord.getLength(); i++){
			this.border.add(new Border(((Element)bord.item(i))));
		}
		NodeList cont = ((Element)n_in).getElementsByTagName("container");
		for (int i = 0; i < cont.getLength(); i++){
			this.containers.add(((Element)cont.item(i)).getTextContent());
		}
		NodeList it = ((Element)n_in).getElementsByTagName("item");
		for (int i = 0; i < it.getLength(); i++){
			this.items.add(((Element)it.item(i)).getTextContent());
		}
		NodeList cr = ((Element)n_in).getElementsByTagName("creature");
		for (int i = 0; i < cr.getLength(); i++){
			this.creatures.add(((Element)cr.item(i)).getTextContent());
		}
		NodeList trig = ((Element)n_in).getElementsByTagName("trigger");
		for (int i = 0; i < trig.getLength(); i++){
			Trigger t =  new Trigger(trig.item(i));
			this.triggers.add( t);
			if(t.getCondition() != null){
				this.status = t.getCondition().getStatus();
			}
		}

		return this;
	}
	public void setName(String n) { this.name = n; }
	public void setStatus(String s) { this.status = s;}
	public void setType(String t) {this.type = t;}
	public void setDescription(String d) {this.description = d; }
	public String getName() { return this.name; }
	public String getStatus() { return this.status;}
	public String getType() { return this.type;}
	public String getDescription() { return this.description; }
	
}
