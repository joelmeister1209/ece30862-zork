import java.util.*;
import org.w3c.dom.*;

public class Map{
	public HashMap<String,Room> rooms;
	private HashMap<String, Container> containers;
	private HashMap<String, Creature> creatures;
	private List<Item> items;
	Map(){
		rooms = new LinkedHashMap<String,Room>();
		containers = new LinkedHashMap<String,Container>();
		items = new ArrayList<Item>();
		creatures = new LinkedHashMap<String, Creature>();
	}
	public void setItemStatus(String it, String s){
		for (int i = 0 ; i < items.size() ; i++){
			if(items.get(i).getName() == null) continue;
			if(items.get(i).getName().equals(it)){
				items.get(i).setStatus(s);
				return;
			}
		}
	}
	public void setContainerStatus(String c, String s){
		containers.get(c).setStatus(s);
	}
	public void addItemToContainer(String i, String c){
		this.containers.get(c).addItem(i);
	}
	public void openContainer(String cName){
		//this.containers.get(cName).emptyContainer();
		this.containers.get(cName).isOpen = true;
	}
	public Creature getCreatureByName(String cName) { 
		if(! this.creatures.containsKey(cName)) return null;
		return this.creatures.get(cName);
	}
	public Container getContainerByName(String cName) { 
		if(! this.containers.containsKey(cName)) return null;
		return this.containers.get(cName);
	}
	public Item getItemByName(String iName) { 
		for (int i = 0 ; i < items.size() ; i++){
			if(items.get(i).getName() == null) continue;
			if(items.get(i).getName().equals(iName)){
				return items.get(i);
			}
		}
		return null; 
	}
	public Map buildMap(NodeList map){
        for(int i = 0; i < map.getLength() ; i++){
        	Element ele = (Element) map.item(i);
        	NodeList ro = ele.getElementsByTagName("room");
        	for(int j = 0; j < ro.getLength(); j++){
        		Room r = new Room();
        		rooms.put(r.buildRoom(ro.item(j)).getName(), r);
        		//rooms.add(r.buildRoom(ro.item(j)));
        	}
        	NodeList it = ele.getElementsByTagName("item");
        	for(int j = 0; j < it.getLength(); j++){
				Item item = new Item();
        		items.add(item.buildItem(it.item(j)));
        	}
        	NodeList cn = ele.getElementsByTagName("container");
        	for(int j = 0; j < cn.getLength(); j++){
        		Container cont = new Container();
        		containers.put(cont.buildContainer(cn.item(j)).getName(), cont);
        	}
        	NodeList cr = ele.getElementsByTagName("creature");
        	for(int j = 0; j < cr.getLength(); j++){
				Creature creature = new Creature();
        		creatures.put(creature.buildCreature(cr.item(j)).getName(), creature);
        	}
        	
        }
        return this;
	}
}
