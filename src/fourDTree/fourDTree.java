package fourDTree;

/*
	Class structure for 4 dimensional binary search tree. 
	This class has functions for creating the tree structure from a double array[nodes][values], adding new node to the tree from double array[values], 
	deleting node from the tree from double array[values] and checking box intersection(node values can represent rectangle diagonal vertices, this rectangles can overlap) nodes' 
	values that fit the criteria for intersection are output to console.
	Nodes values should be in correct order and properly set so that the values can represent a rectangle. E.g. values values[0,0,1,1] are correct since these values define a diagonal of a 
	rectangle and v[0]<v[2] && v[1] < v[3]. values[1,1,0,0] are incorrect.
 */
public class fourDTree {
	
	private node root;
	
	protected fourDTree(double[][] input)
	{
		//construct 4dtree from array of coordinates
		node temp;
		
		for(int i=0; i<input.length; i++)
		{
			temp = new node(input[i]);
			root = insertNode(temp, root, 0);
			
		}
		
	
		
	}
	
	protected void checkIntersection(double[] values)
	{
		//wrapper for box intersection check
		System.out.println("intersecting boxes");
		intersectionTest(new node(values), this.root, 0);
	}
	
	private void intersectionTest(node target, node root, int depth)
	{
		//base case
		if(root == null)
			return;
		//check for intersection
		if(intersects(target, root))
			printNodeValues(root);
		//selecting subtree for node search based on dimension of the level
		if(depth % 4 == 0)
		{
			if(getValues(target)[2] >= getValues(root)[0])
				intersectionTest(target, root.readRightNode(), depth + 1);
			else if(getValues(target)[2] < getValues(root)[0])
			{
				intersectionTest(target, root.readLeftNode(), depth + 1);
				intersectionTest(target, root.readRightNode(), depth + 1);
			}
		}
		if(depth % 4 == 1)
		{
			if(getValues(target)[3] >= getValues(root)[1])
				intersectionTest(target, root.readRightNode(), depth + 1);
			else if(getValues(target)[3] < getValues(root)[1])
			{
				intersectionTest(target, root.readLeftNode(), depth + 1);
				intersectionTest(target, root.readRightNode(), depth + 1);
			}
		}
		if(depth % 4 == 2)
		{
			if(getValues(target)[0] >= getValues(root)[2])
				intersectionTest(target, root.readRightNode(), depth + 1);
			else if(getValues(target)[0] < getValues(root)[2])
			{
				intersectionTest(target, root.readLeftNode(), depth + 1);
				intersectionTest(target, root.readRightNode(), depth + 1);
			}
		}
		if(depth % 4 == 3)
		{
			if(getValues(target)[1] >= getValues(root)[3])
				intersectionTest(target, root.readRightNode(), depth + 1);
			else if(getValues(target)[1] < getValues(root)[3])
			{
				intersectionTest(target, root.readLeftNode(), depth + 1);
				intersectionTest(target, root.readRightNode(), depth + 1);
			}
		}
		
		
	}
	
	private void printNodeValues(node n)
	{
		//helper function for console output of node values
		for(int i=0; i<4; i++)
			System.out.print(getValues(n)[i]+",");
		System.out.println();
	}
	
	private boolean intersects(node n1, node n2)
	{
		//helper function, checks for intersection between two boxes
		double[] x,y;
		x = getValues(n1);
		y = getValues(n2);
		if(x[0] <= y[2] && x[1] <= y[3] && x[2] >= y[0] && x[3] >= y[1])
			return true;
		else
			return false; 
	}
	
	
	
	
	private node minNode(node n1, node n2, node n3, int d)
	{
		//helper function for finding node with minimal value based on selected dimension
		node min = n1;
		
		if(n2 != null)
			if(getValues(n2)[d] < getValues(min)[d]) min = n2;
		
		if(n3 != null) 
			if(getValues(n3)[d] < getValues(min)[d]) min = n3;
			
		
		return min;
	}
	
	private node findMin(node root, int depth, int d)
	{
		//finds minimum node in subtree based on selected dimension
		if(root == null) 
			return null;
		if((depth % 4) == d)
		{
			if(root.readLeftNode() == null){
				return root;
			}
			else
				return findMin(root.readLeftNode(), depth + 1, d);
		}
		
		return minNode(root, findMin(root.readLeftNode(), depth+1, d), findMin(root.readRightNode(), depth+1, d), d);
	}
	protected void removeNode(double[] values)
	{
		//wrapper function
		deleteNode(new node(values), this.root, 0);
	}
	private node deleteNode(node target, node root, int depth)
	{
		//deletes selected node
		
		//base case
		if(root == null) 
			return null;
		//current dimension
		int d = depth % 4;
		if(nodeEqual(target, root))
		{
			//node to be correct node
			
			//pick node for substition by finding minimum node based on current dimension
			if(root.readRightNode() != null)
			{
				//from right subtree
				node min = findMin(root.readRightNode(), depth + 1, d);
                                    				copyValues(root, min);
				root.setRightNode(deleteNode(min, root.readRightNode(), depth + 1));
			}
		
			else if(root.readLeftNode() != null)
			{
				//from left subtree
				node min = findMin(root.readLeftNode(), depth + 1, d);
				copyValues(root, min);
				root.setRightNode(deleteNode(min, root.readLeftNode(), depth + 1));
			}
			else 
				//delete leaf node
				return null;
			return root;
		}
		//if node to be deleted is not found, continue search
		if(getValues(target)[d] < getValues(root)[d]) 
			root.setLeftNode(deleteNode(target, root.readLeftNode(), depth + 1));
		else
			root.setRightNode(deleteNode(target, root.readRightNode(), depth + 1));
		return root;
	}
	private boolean nodeEqual(node n1, node n2)
	{
		//returns true if nodes are equal
		for(int i=0; i<4; i++){
			if(getValues(n1)[i] != getValues(n2)[i])
				return false;
		}
		return true;
	}
	private void copyValues(node n1, node n2)
	{
		//write values from node(n2) to node(n1).
		n1.insertValues(getValues(n2));
		
	}
	private boolean compareNodeByDim(node node1, node node2, int dim)
	{
		//helper function for comparing nodes by specific dimension, returns false if value lower than else true
		double[] x,y;
		x = getValues(node1);
		y = getValues(node2);
		if(x[dim] < y[dim]) return false;
		else return true;
	}
	
	private double[] getValues(node n)
	{
		return n.coordintes;
	}
	
	
	private node insertNode(node target, node root, int depth)

	{
		//node insertion code
		
		//empty tree
		if(root == null)
		{
			return target;
		}
		
		else
		{
			//recursively build tree 
			if(depth % 4 == 0)
			{
				if(!compareNodeByDim(target, root, 0)) root.setLeftNode(insertNode(target, root.readLeftNode(), depth + 1));
				else root.setRightNode(insertNode(target, root.readRightNode(), depth + 1));
			}
			if(depth % 4 == 1)
			{
				if(!compareNodeByDim(target, root, 1)) root.setLeftNode(insertNode(target, root.readLeftNode(), depth + 1));
				else root.setRightNode(insertNode(target, root.readRightNode(), depth + 1));
			}
			if(depth % 4 == 2)
			{
				if(!compareNodeByDim(target, root, 2)) root.setLeftNode(insertNode(target, root.readLeftNode(), depth + 1));
				else root.setRightNode(insertNode(target, root.readRightNode(), depth + 1));
			}
			if(depth % 4 == 3)
			{
				if(!compareNodeByDim(target, root, 3)) root.setLeftNode(insertNode(target, root.readLeftNode(), depth + 1));
				else root.setRightNode(insertNode(target, root.readRightNode(), depth + 1));
			}
			
		}
		
		return root;
		
	}
	
	protected void addNode(double[] values){
		//add single node
		node temp = new node(values);
		root = insertNode(temp, root, 0);
	}

	private class node
	{
		//definiton for node class
		private double[] coordintes;
		private node left, right;
		
		private node(double[] values)
		{
			coordintes = new double[4];
			for(int i=0; i<4; i++)
			{
				coordintes[i] = values[i];
			}
		}
		private void insertValues(double[] values)
		{
			coordintes = values;
		}
		private void setLeftNode(node n)
		{
			this.left = n;
		}
		private void setRightNode(node n)
		{
			this.right = n;
		}
		private node readLeftNode()
		{
			return this.left;
		}
		private node readRightNode()
		{
			return this.right;
		}
	}
	
}
