package fourDTree;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Testing the 4D Tree class
		 * 
		 */

		//sample array with 4 values for nodes
		double[][] input = {{1,1,2,2},{8,1,9,2},{7,1,8,3},{6,1,7,4},{5,1,6,6},{4,1,6,2}};
		//create tree from array of values
		fourDTree tree = new fourDTree(input);
		//insert new node
		double[] temp = {1,4,2,5};
		tree.addNode(temp);
		//remove node
		tree.removeNode(temp);
		//check node for intersection
		double[] temp2 = {7.5,1,9,2};
		tree.checkIntersection(temp2);
		
		
		
	}

}
