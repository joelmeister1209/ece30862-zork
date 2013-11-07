import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Container {
	private String name;
	private String status;
	private String description;
	private List<String> accept; //the name of an item that is accepted (ex: lock -> key)
	private List<String> items;
	private List<Trigger> triggers;
	public boolean isOpen;
	
	public List<String> getAcceptList(){
		return this.accept;
	}
	public void emptyContainer(){
		this.items.clear();
	}
	public void removeItem(String iName){
		this.items.remove(iName);
	}
	public void addItem(String i) {this.items.add(i); }
	public List<String> getItemList() {return this.items;}
	public Container() {
		items = new ArrayList<String>();
		accept = new ArrayList<String>();
		
		isOpen = false;
	}
	public Container buildContainer(Node n_in) {
//TODO needs triggers fixed

		if(! (n_in instanceof Element) ) return null; 
		Element ele = (Element) n_in;
    		NodeList na = (ele).getElementsByTagName("name");
    		NodeList sta = (ele).getElementsByTagName("status");
    		NodeList desc = (ele).getElementsByTagName("description");
    		NodeList acc = (ele).getElementsByTagName("accept");
    		
    		if(na.getLength() > 0)
    			this.name = ((Element)na.item(0)).getTextContent();
    		if(sta.getLength() > 0)
    			this.status = ((Element)sta.item(0)).getTextContent();
    		if(desc.getLength() > 0)
    			this.description = ((Element)desc.item(0)).getTextContent();

    		NodeList it = (ele).getElementsByTagName("item");
    		for(int j = 0; j < it.getLength(); j++){
    			Element iEle = (Element)it.item(j);
    			this.items.add(iEle.getTextContent());
    		}
    		for(int j = 0; j < acc.getLength(); j++){
    			Element iEle = (Element)acc.item(j);
    			this.accept.add(iEle.getTextContent());
    		}

    		NodeList trig = (ele).getElementsByTagName("trigger");
/*
    		if(trig.getLength() > 0)
    			this.triggers.add(new Trigger(trig));
*/
		return this;
	}
	public String getName() {return this.name;}
	public String getDescription(){ return this.description;}
	public String getStatus() {return this.status;}
}
