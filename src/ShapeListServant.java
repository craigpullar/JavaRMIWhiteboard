
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;
public class ShapeListServant implements ShapeList, Serializable{
	private static final long serialVersionUID = 1L;
	private Vector<ShapeServant> theList; // contains the list of Shapes
	private int version;
	public ShapeListServant(){
		version = 0;
		
	}
	public Shape newShape(GraphicalObject g) { 
		if (theList == null){
			theList = new Vector<ShapeServant>();
		}
		version++;
		Shape s = new ShapeServant( g, version, g.getID());
		theList.addElement((ShapeServant) s);
		return s;
	}
	public void removeShape(GraphicalObject g) throws RemoteException {
		for (ShapeServant shape: theList){
			if (shape.getObjectId() == g.getID()){
				theList.remove(shape);
				break;
			}

				
		}
	}
	@Override
	public Vector<ShapeServant> allShapes() throws RemoteException {
		if (theList == null){
			theList = new Vector<ShapeServant>();
		}
		return theList;
	}
	@Override
	public int getVersion() throws RemoteException {
		return version;
	}
}