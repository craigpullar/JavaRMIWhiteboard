import java.io.Serializable;
import java.rmi.RemoteException;


public class ShapeServant implements Shape, Serializable{
	private static final long serialVersionUID = 1L;
	private GraphicalObject g;
	private int version;
	private int objectId;
	
	public ShapeServant(GraphicalObject g, int version, int id) {
		this.g = g;
		this.version = version;
		this.objectId = id;
	}

	@Override
	public int getVersion() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GraphicalObject getAllState() throws RemoteException {
		return this.g;
	}
	
	public int getObjectId() {
		return this.objectId;
	}

}
