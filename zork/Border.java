import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Border {
	private String name;
	private String direction;
	public Border(Element e_in){
		
    		NodeList na = (e_in).getElementsByTagName("name");
    		NodeList dir = (e_in).getElementsByTagName("direction");
    		if(na.getLength() > 0)
    			this.name = ((Element)na.item(0)).getTextContent();
    		if(dir.getLength() > 0)
    			this.direction = ((Element)dir.item(0)).getTextContent().substring(0,1);
        
	}
	public String getName() {return this.name;}
	public String getDirection() {return this.direction;}
}
