
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
	
	//inner class Node of a graph 
	private static class Node {
		private int i;
		private int j;
		int value; 
		
		private Node(int i, int j, int value) {
			this.i = i; 
			this.j = j; 
			this.value = value;
		}

	}
	
	public static  int min_moves(int[][] board) {
		
		
		Node startNode = new Node (0, 0, board[0][0]);
		
		if (startNode.value==0) {
			return -1;
		}
		
		Node endNode = new Node (board.length-1, board[0].length-1,  board[board.length-1][board[0].length-1]);
			
		//find shortest path with BFS (lecture 14)
		
		Queue<Node> queue = new LinkedList <Node>();
		queue.add(startNode);

		//dequeue all (put in visited) and enqueue the nodes in its adj list
		
		//********************************* 
		
		int [][] distances = new int [board.length][board[0].length]; 
		
		//finding shortest path using BFS
		while (!queue.isEmpty()) {
			Node currNode = queue.remove(); //dequeues - not removing??????
			

				//if exist, if distance == 0, 
				//then adjNode.distance+=currNode.distance+currNode.value
			Node topNode = new Node (0, 0, 0);
			topNode.i = currNode.i-currNode.value;
			topNode.j = currNode.j;
			
			if (isNode(topNode, board)==true) { //if node exist
				if (distances[topNode.i][topNode.j] == 0 && !(topNode.i==0 && topNode.j==0)) { //hasn't been visited
				topNode.value = board[topNode.i][topNode.j]; //find its value
				
				//update distance
				if (topNode.value!=0) {
				distances[topNode.i][topNode.j] = distances[currNode.i][currNode.j] + 1;
				queue.add(topNode);
				}
				
				}
			}
			
			//end if it's the destination node
			if (topNode.i==endNode.i && topNode.j==endNode.j) { 
				distances[topNode.i][topNode.j] = distances[currNode.i][currNode.j] + 1;
				return distances[topNode.i][topNode.j];
			}
			
//enqueue bottom node (i+value, j)
			
			Node bottomNode = new Node (0, 0, 0);
			bottomNode.i = currNode.i+currNode.value;
			bottomNode.j = currNode.j;
			
			
			if (isNode(bottomNode, board)==true) { //if node exist
				if (distances[bottomNode.i][bottomNode.j] == 0 && !(bottomNode.i==0 && bottomNode.j==0)) { //hasn't been visited
					bottomNode.value = board[bottomNode.i][bottomNode.j]; //find its value
				
				//update distance
				if (bottomNode.value!=0 ) {
				distances[bottomNode.i][bottomNode.j] = distances[currNode.i][currNode.j] + 1;
				queue.add(bottomNode);
				}
				
				}
			}
			
			//end if it's the destination node
			if (bottomNode.i==endNode.i && bottomNode.j==endNode.j) { 
				distances[bottomNode.i][bottomNode.j] = distances[currNode.i][currNode.j] + 1;
				return distances[bottomNode.i][bottomNode.j];
			}
			
			
// when change variable of adjNode, previously stored node in queue changes??????				
//enqueue left node (i, j-value)
			
			Node leftNode = new Node (0, 0, 0);
			leftNode.i = currNode.i;
			leftNode.j = currNode.j-currNode.value;

			if (isNode(leftNode, board)==true) { //if node exist
				if (distances[leftNode.i][leftNode.j] == 0 && !(leftNode.i==0 && leftNode.j==0)) { //hasn't been visited
					leftNode.value = board[leftNode.i][leftNode.j]; //find its value
				
				//update distance
				if (leftNode.value!=0) {
				distances[leftNode.i][leftNode.j] = distances[currNode.i][currNode.j] + 1;
				queue.add(leftNode);
				}
				}
			}
			
			//end if it's the destination node
			if (leftNode.i==endNode.i && leftNode.j==endNode.j) {
				distances[leftNode.i][leftNode.j] = distances[currNode.i][currNode.j] + 1;
				return distances[leftNode.i][leftNode.j];
			}
			
//enqueue right node (i, j+value)
			Node rightNode = new Node (0, 0, 0);
			rightNode.i = currNode.i;
			rightNode.j = currNode.j+currNode.value;
			
			if (isNode(rightNode, board)==true) { //if node exist
				if (distances[rightNode.i][rightNode.j] == 0 && !(rightNode.i==0 && rightNode.j==0)) { //hasn't been visited
					rightNode.value = board[rightNode.i][rightNode.j]; //find its value
				
				//update distance
				if (rightNode.value!=0) {
				distances[rightNode.i][rightNode.j] = distances[currNode.i][currNode.j] + 1;
				queue.add(rightNode);
				}
				
				}
			}
			
			//end if it's the destination node
			if (rightNode.i==endNode.i && rightNode.j==endNode.j) { 
				distances[rightNode.i][rightNode.j] = distances[currNode.i][currNode.j] + 1;
				return distances[rightNode.i][rightNode.j];
			}
		}
		
		return -1; // if not possible
	}
	
	private static boolean isNode (Node node, int[][] board) {
		
		if (node.i>=0 && node.i<board.length) {
			if (node.j>=0 && node.j<board[0].length) {
				return true;
			}
		}
		return false;
		
	}

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}
			
		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Honors honors = new Honors();
		//honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'

		// or you can test like this
		int [][] board = {{2, 0, 2}};
		//int [][]board = {{2,1,2,0},{1,2, 0,3}, {3, 1, 1, 3}, {1, 1, 2, 0}, {1, 1, 1, 0}}; // not actual example
		 int answer = min_moves(board); 
		 System.out.println(answer);
		
		
		
	}

}
