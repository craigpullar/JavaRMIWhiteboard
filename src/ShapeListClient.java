
import java.awt.Color;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;
public class ShapeListClient{
	
	private static final String HOST = "localhost";	
	private static final int PORT = 2006;
	private static Registry registry;
	public static void locateRegistry() throws RemoteException{
		registry = LocateRegistry.getRegistry(HOST, PORT);
	}
	public static ShapeListServant getCurrentShapeList() throws AccessException, RemoteException, NotBoundException{
		locateRegistry();
		return (ShapeListServant) registry.lookup(ShapeList.class.getSimpleName());
	}
	public void updateCurrentShapeList(ShapeListServant list) throws AccessException, RemoteException, AlreadyBoundException {
		locateRegistry();
		registry.rebind(ShapeList.class.getSimpleName(), list);
	}
	
	public static void main(String args[]){
//		System.setProperty("java.security.policy","src/client.policy");
//		System.setSecurityManager(new RMISecurityManager()); 
		ShapeListServant aShapeList = null;
		


		try{
			aShapeList = getCurrentShapeList();
			Vector sList = aShapeList.allShapes();
			//Start whiteboard
			System.out.println(sList);
			Whiteboard board = new Whiteboard(500,500,aShapeList);
			board.startWhiteboard();
		} 
		catch(RemoteException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println("Client: " + e);
		}
	}
}
