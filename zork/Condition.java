import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class Condition {
	String has;
	String object;
	String owner;
	String status;
	public Condition(){
		
	}
	public Condition(NodeList n_in){

		for(int i = 0; i < n_in.getLength() ; i++){
        	Element cond = (Element) n_in.item(i);
			NodeList h = ((Element)cond).getElementsByTagName("has");
			if(h.getLength() > 0) this.has = ((Element)h.item(0)).getTextContent();
			NodeList obj = ((Element)cond).getElementsByTagName("object");
			if(obj.getLength() > 0) this.object = ((Element)obj.item(0)).getTextContent();
			NodeList own = ((Element)cond).getElementsByTagName("owner");
			if(own.getLength() > 0) this.owner = ((Element)own.item(0)).getTextContent();
			NodeList stat = ((Element)cond).getElementsByTagName("status");
			if(stat.getLength() > 0) this.status = ((Element)stat.item(0)).getTextContent();
	
		}
	}
	public void print(){
		System.out.println("Condition: "+this.has + " "+this.object +" "+ this.owner);
	}
}
