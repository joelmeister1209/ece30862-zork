import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
//import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Zork {
	static Map map;
    static ArrayList<String> inventory = new ArrayList<String>();
	static Room currRoom = null;
	static Item currItem = null;

	public static void main(String[] args) throws Exception{
		if(args.length < 1){
			System.out.println("Usage: java Zork <filename>");
			System.exit(0);
		}
	    try {
	    	System.out.println("Welcome to Zork!");
	    	map = new Map();
	        DOMParser p = new DOMParser();
	        p.parse(args[0]);
	        Document doc = p.getDocument();
	        map = map.buildMap(doc.getElementsByTagName("map"));
	        StartGame();
	      } catch (Exception e) {e.printStackTrace();}
	}
	private static void StartGame() throws SAXException, JAXBException{
        String directions = "nesw";
        Scanner in = new Scanner(System.in);
    	currRoom = map.rooms.get("Entrance");
        while(true){
	    	System.out.println("\nRoom: " + currRoom.getName()
	    			+(currRoom.getType().equals("exit") ? " (exit)" : ""));
        	System.out.println("What would you like to do?");
        	String command = in.nextLine();
        	if(directions.contains(command)){//move
        		moveTo(command);
        	}else if(command.equals("i")){//inventory
        		printInv();
        	} else if(command.split(" ")[0].equals("take")){//take <item>
        		if(command.split(" ").length == 0) { System.out.println("Please enter item name"); continue;}
        		take(command.split(" ")[1]);
        	} else if(command.split(" ")[0].equals("open")){//open <item/exit>
        		if(command.split(" ").length == 1) { System.out.println("Please enter item name"); continue;}
        		open(command.split(" ")[1]);//handles container and exit
			} else if(command.split(" ")[0].equals("read")){ //read <item>
        		if(command.split(" ").length == 1) { System.out.println("Please enter item name"); continue;}
				read(command.split(" ")[1]);
			} else if(command.split(" ")[0].equals("drop")){ //drop <item>
        		if(command.split(" ").length == 1) { System.out.println("Please enter item name"); continue;}
				drop(command.split(" ")[1]);
			} else if(command.split(" ")[0].equals("put")){ //put <item> in <container>
        		if(command.split(" ").length < 4) { System.out.println("Usage: put <item> in <container>"); continue;}
				putItem(command.split(" ")[1],command.split(" ")[3]);
			} else if(command.split(" ")[0].equals("turn")){ //turn on <item>
        		if(command.split(" ").length < 3) { System.out.println("Usage: turn on <item>"); continue;}
				turnOn(command.split(" ")[2]);
			} else if(command.split(" ")[0].equals("attack")){ //attack <creature> with <item>
        		if(command.split(" ").length < 4) { System.out.println("Usage: attack <creature> with <item>"); continue;}
				attack(command.split(" ")[1],command.split(" ")[3]);
        	} else if(command.equals("look")){//look
	        	look();
        	} else if(command.startsWith("help")){//help (I added)
        		printHelp();
        	} else if(command.startsWith("quit")){//help (I added)
				System.out.println("Are you sure? (y/n)");
				if(!(command = in.nextLine()).equals("y")) {
					continue;
				}
        		break; //breaks the main loop so we can clean up
        	}else{
        		System.out.println("Command <"+command+"> not recognized");
        	}
        }
        in.close();
	}
	private static void attack(String c, String i){
	//TODO this action
		System.out.println("IMPLEMENT ATTACK");
	}
	private static void turnOn(String i){
	//TODO this action
		if(! inventory.contains(i)){
			System.out.println("No item in inventory");
			return;
		}
		//currRoom.getItem(i).turnOn(); //idk what im doing lol
		System.out.println("IMPLEMENT TURN ON");
	}
	private static void open (String target) throws SAXException, JAXBException{
		if(target.equals("exit")){
			if(currRoom.getType().equals("exit")){
				System.out.println("Game Over: Victory!");
				System.exit(0);
			}
			System.out.println("Not an exit!");
		} else if( currRoom.getContainers().contains(target) ){
			System.out.print("<"+target+"> ");
			if(map.getContainerByName(target).getAcceptList().size() > 0){
				System.out.println("is unable to be opened...");
				return;
			}
			map.openContainer(target);
			if( map.getContainerByName(target).getItemList().size() < 1){
				System.out.println("is empty");
				return;
			}
			System.out.print("contains ");
			for(int i = 0; i < map.getContainerByName(target).getItemList().size(); i++){
				if(i!=0) System.out.print(", ");
				System.out.print("<"+map.getContainerByName(target).getItemList().get(i)+">");
			}
		} else {
			System.out.println("No container <"+target+"> in <"+currRoom.getName()+">"); 
		}
	}
	private static void look(){
//TODO creatures 
		System.out.println(currRoom.getDescription());
		System.out.print("Items: ");
		if(currRoom.getItemList().isEmpty()){
			System.out.println("No items in room");
		}else {
		    	for(int j=0; j < currRoom.getItemList().size(); j++ ){
		    		System.out.println(currRoom.getItemList().get(j));
		    	}
		}
		if(! currRoom.getContainers().isEmpty()){
		//don't say if there aren't any containers
			for(int j=0; j < currRoom.getContainers().size(); j++){
				String cName = currRoom.getContainers().get(j);
				Container c = map.getContainerByName(cName);
				System.out.print(c.getName()+": ");
				if(!strIsNullorEmpty(c.getStatus())) 
					System.out.println(c.getStatus());
				else if (c.isOpen) System.out.println("open");
				else System.out.println("closed");
			}
		}
			for(String creatName : currRoom.getCreatures()){
				Creature creat = map.getCreatureByName(creatName);
				if(creat == null) continue;
				System.out.print(creat.getName());
				if(!strIsNullorEmpty(creat.getDescription())) 
					System.out.print(": "+creat.getDescription()+" ");
				if(!strIsNullorEmpty(creat.getStatus())) 
					System.out.println("status > "+creat.getStatus());
			}
		
	}
	private static void take(String iName){
		if(currRoom.getItemList().contains(iName)){
			currRoom.takeItem(iName);
			inventory.add(iName);
			System.out.println("Item <"+iName+"> added to inventory");
			return;
		} 
		Container c = null;
		for(int i =0; i<currRoom.getContainers().size(); i++){
			c=map.getContainerByName(currRoom.getContainers().get(i));
			if(c.getItemList().contains(iName)){
				if(! c.isOpen) {
					break;
				}
				map.getContainerByName(c.getName()).removeItem(iName);
				inventory.add(iName);
				System.out.println("Item <"+iName+"> added to inventory");
				return;
			}
		}
		System.out.println("Item <"+iName+"> is not in room");
	}
	private static void drop(String iName){
		if(!inventory.remove(iName)){
			System.out.println("No such item in inventory");
			return;
		}
		currRoom.addItem(iName);
	}
	private static void read(String iName){
		if(!inventory.contains(iName)){
			System.out.println("No such item in inventory");
			return;
		}
		currItem = map.getItemByName(iName);
		if(currItem.getWriting() == null){
			System.out.println("Nothing written");
		} else {
			System.out.println(currItem.getWriting());
		}
	}
	private static void action(String action){
		String[] aSplit = action.split(" ");
		if(aSplit[0].equals("Add")){
			if(aSplit.length < 4) return;
			String obj = aSplit[1];
			String dest = aSplit[3];
			if(map.getContainerByName(dest)!=null){
				map.addItemToContainer(obj, dest);
			} else if(currRoom.getName().equals(dest)){
				currRoom.addItem(obj);
			} else if(dest.equals("inventory")){
				inventory.add(obj);
			}
		}else if(aSplit[0].equals("Update")){
			if(aSplit.length < 4) return;
			String obj = aSplit[1];
			String status = aSplit[3];
			if(map.getItemByName(obj) != null){
				map.setItemStatus(obj, status);
			}else if(map.getContainerByName(obj) != null){
				map.setContainerStatus(obj, status);
			}else if(currRoom.getName().equals(obj)){
				currRoom.setStatus(status);
			}
		}else if(aSplit[0].equals("Delete")){
			if(aSplit.length < 2) return;
			String obj = aSplit[1];
			System.out.println("IMPLEMENT DELETE");
		}else if(action.equals("Game Over")){
			System.out.println("Game Over");
			System.exit(0);
		}
	}
	private static void putItem(String i, String c){
		if(!inventory.contains("key")) inventory.add("key"); //delete this
		if(!inventory.contains(i)){
			System.out.println("Item <"+i+"> not in inventory");
			return;
		}
		for(Trigger trig : map.getContainerByName(c).getTriggers()){
			Condition cond = trig.getCondition();
			if(cond == null){ 
				System.out.println(trig.getPrint());
				return;
			}
			if(!strIsNullorEmpty( cond.getStatus()) ){
				//status
				if(currRoom.getStatus().equals("locked")){
					System.out.println(trig.getPrint());
					return;
				}
			} else{
				//owner
				Container cont = map.getContainerByName(c);
				if(trig.getCondition().getHas().equals("yes")
						&& trig.getCondition().getObject().equals(i)
						&& trig.getCondition().getOwner().equals(c)){
					System.out.println(trig.getPrint());
					action(trig.getAction());
					map.openContainer(c);
				}
			}
		} //end for
		if(map.getContainerByName(c) == null){
			System.out.println("Container <"+c+"> not in room");
			return;
		}
		if(! map.getContainerByName(c).isOpen){
			System.out.println("Container <"+c+"> is closed");
			return;
		}
		if(map.getContainerByName(c).getAcceptList().size() > 0 
				&& ! map.getContainerByName(c).getAcceptList().contains(i))
		{
			System.out.println("Container <"+c+"> does not accept <"+i+">");
			return;
		}
		inventory.remove(i);
		map.addItemToContainer(i, c);
	}
	private static void moveTo(String command){
		Border b = currRoom.getBorder(command);
		if(b == null) {
			System.out.println("No room in that direction!");
		} else {
			if(map.rooms.containsKey(b.getName())){
				for(Trigger trig : currRoom.getTriggers()){
					if(!trig.getCommand().equals(command)){ break; }
					Condition cond = trig.getCondition();
					if(cond != null){
						if(!strIsNullorEmpty( cond.getStatus()) ){
							//status
							if(currRoom.getContainers().contains(cond.getObject())){
								if(!map.getContainerByName(cond.getObject()).isOpen){
									System.out.println(trig.getPrint());
									return;
								}
							}
						} else{
							//owner
							if(cond.getOwner().equals("inventory")){
								if(cond.getHas().equals("no")){
									if(!inventory.contains(cond.getObject())){
										//stay in room
										System.out.println(trig.getPrint());
										return;
									}
								}
							} else {
								Container cont = map.getContainerByName(cond.getStatus());
								if(cond.getHas().equals("no")){
									/*//not sure if this will ever be a condition
									if(){
										//stay in room
										System.out.println(trig.getPrint());
										return;
									} else{
										//move
									}
									*/
								}
							}
						}
					}else{ //shouldn't happen
						System.out.println(trig.getPrint());
						return;
					}
					
				}
				currRoom = map.rooms.get(b.getName());
			}
		}
	}
	private static void printInv(){
		System.out.print("Inventory: ");
		if(inventory.isEmpty()){
			System.out.println("empty");
		}else {
			System.out.print(inventory.get(0));
			for(int i = 1; i < inventory.size() ; i++){
				System.out.print(", "+inventory.get(i));
			}
			System.out.println();
		}
	}
   	private static void printHelp(){
		System.out.println("n, s, e, w - movement commands\n"
				+"i - inventory\n"
				+"take (item)\n"
				+"open (container)\n"
				+"open exit\n"
				+"read (item)\n"
				+"drop (item)\n"
				+"put (item)\n"
				+"turn on (item)\n"
				+"attack (creature) with (item)\n"
				+"look"
				);
	}
	private static boolean strIsNullorEmpty(String s){
		if(s == null) return true;
		if(s.length() < 1 ) return true;
		return false;
	}
}
