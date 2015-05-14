
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class ShapeListServer{
	private static final int PORT = 2006;
	private static Registry registry;
	 
	
		public static void startRegistry() throws RemoteException {
			registry = java.rmi.registry.LocateRegistry.createRegistry(PORT);
		}
		
		public static void registerObject(String name, Remote remoteObj) throws RemoteException, AlreadyBoundException {
			registry.bind(name, remoteObj);
			System.out.println("Registered: " + name + " -> " + remoteObj.getClass().getName() + "[" + remoteObj + "]");
		}
		public static void main(String args[]) throws RemoteException, InterruptedException{
//			System.setProperty("java.security.policy","src/server.policy");
//		     System.setSecurityManager ( new RMISecurityManager() );
		     
		try{
			startRegistry();
			registerObject(ShapeList.class.getSimpleName(), new ShapeListServant());
			System.out.println("ShapeList server ready");
		}
		catch(Exception e) {	
			e.printStackTrace();
		} 
		Thread.sleep(5 * 60 * 1000000);
	}
}
