import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Whiteboard {
	private int width;
	private int height;
	private ShapeListServant shapes;
	private JPanel panel;
	private JFrame frame;
	private JButton rectangleSelector;
	private JButton ovalSelector;
	private JButton triangleSelector;
	private GraphicalObject.ShapeType selectedShapeType;
	private JButton colourSelector;
	private int colourIndex;
	private JComboBox sizeSelector;
	private int currentSize;
	private Color[] colours = {Color.BLACK, Color.YELLOW,Color.BLUE,Color.RED,Color.GREEN};
	private ShapeListClient client;
	private JButton refreshButton;

	
	public Whiteboard(int width, int height,ShapeListServant shapes) {
		this.width = width;
		this.height = height;
		this.shapes = shapes;
		client = new ShapeListClient();
	}
	
	public void startWhiteboard() {
		selectedShapeType = GraphicalObject.ShapeType.RECTANGLE;
		colourIndex = 0;
		frame = new JFrame("Whiteboard");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Set size
		frame.setSize(this.width, this.height);
		//Create JPanel
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		frame.setContentPane(panel);
		setUpMouseEvents();
		setUpTools();

		
		frame.setVisible(true);
		
		try {
			this.updateBoardFromRegistry();
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setUpTools() {
		//rect
		rectangleSelector = new JButton();
		rectangleSelector.setSize(60,60);
		rectangleSelector.setLocation(0, 0);
		rectangleSelector.setText("Square");
		rectangleSelector.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e)
		  {
			selectedShapeType = GraphicalObject.ShapeType.RECTANGLE;
		  }
		}
		);
		
		//oval
		ovalSelector = new JButton();
		ovalSelector.setSize(60,60);
		ovalSelector.setLocation(0, 60);
		ovalSelector.setText("Circle");
		ovalSelector.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectedShapeType = GraphicalObject.ShapeType.OVAL;
				
			}
			
		});
		
		//triangle
		triangleSelector = new JButton();
		triangleSelector.setSize(60,60);
		triangleSelector.setLocation(0,120);
		triangleSelector.setText("Tri");
		triangleSelector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedShapeType = GraphicalObject.ShapeType.TRIANGLE;
				
			}
			
		});
		
		//Colour selector
		colourSelector = new JButton();
		colourSelector.setSize(50,50);
		colourSelector.setLocation(5,185);
		colourSelector.setBackground(colours[colourIndex]);
		colourSelector.setOpaque(true);
		colourSelector.setBorder(null);
		
		colourSelector.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				colourIndex++;
			
				if(colourIndex == colours.length) {
					colourIndex = 0;
				}
				colourSelector.setBackground(colours[colourIndex]);
			}
			
		});
		
		//Size selector
		Integer[] sizes = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		sizeSelector = new JComboBox(sizes);
		sizeSelector.setLocation(0,245);
		sizeSelector.setSize(60,20);
		sizeSelector.setSelectedIndex(currentSize-1);
		sizeSelector.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentSize = (Integer) sizeSelector.getSelectedItem();
				
			}
			
		});
		
		//Refresh Button
		refreshButton = new JButton("Refresh");
		refreshButton.setLocation(0, 270);
		refreshButton.setSize(60,40);
		refreshButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					updateBoardFromRegistry();
				} catch (AccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (NotBoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
			
		});
		
				
		
		panel.add(refreshButton);
		panel.add(rectangleSelector);
		panel.add(ovalSelector);
		panel.add(triangleSelector);
		panel.add(colourSelector);
		panel.add(sizeSelector);
		panel.revalidate();
		panel.repaint();
	}
	public void updateBoardFromRegistry() throws AccessException, RemoteException, NotBoundException {
		
		panel.removeAll();
		setUpTools();
		shapes = client.getCurrentShapeList();
		for (Shape shape : shapes.allShapes())
			drawShape(shape.getAllState());

			
	}
	public void drawShape(GraphicalObject g) {
		panel.add(g);
		panel.revalidate();
		panel.repaint();
		frame.repaint();

	}
	public void setUpMouseEvents(){
		panel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
	
				   if(SwingUtilities.isRightMouseButton(e)){
						try {
							updateBoardFromRegistry();
						} catch (AccessException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						} catch (NotBoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					   
					   Component[] components = panel.getComponents();
					   for (Component com: components){
						   if (e.getX() > com.getX() &&
								   e.getX() < (com.getX() + com.getWidth()) &&
								   e.getY() > com.getY() &&
								   e.getY() < (com.getY() + com.getHeight())
								   
								   ){
							   panel.remove(com);
							   try {
								shapes.removeShape((GraphicalObject) com);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							   try {
								client.updateCurrentShapeList(shapes);
							} catch (AccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (AlreadyBoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							   
							   break;
						   }
					   }
					   panel.revalidate();
					   panel.repaint();
					   frame.repaint();
				   }
				   else {
					try {
						updateBoardFromRegistry();
					} catch (AccessException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (NotBoundException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	
					   GraphicalObject g = new GraphicalObject(selectedShapeType, e.getX(), e.getY(), 10*currentSize, 10*currentSize, colours[colourIndex]);
					   shapes.newShape(g);
					   drawShape(g);
					 try {
						client.updateCurrentShapeList(shapes);
					} catch (AccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (AlreadyBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
					   
				   }
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public ShapeListServant getShapeList() {
		return shapes;
	}
	public void setShapeList(ShapeListServant shapes) {
		this.shapes = shapes;
	}

}
