import java.util.*;
import java.lang.*;
import java.io.*;


public class Game {
	
	Board sudoku;
	
	public class Cell{
		private int row = 0;
		private int column = 0;
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}
	
	public class Region{
		private Cell[] matrix; //array of Cells
		private int num_cells; //number of Cells
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}
		
	}
	
	public class Board{
		private int[][] board_values; 
		private Region[] board_regions; //array of regions
		private int num_rows;
		private int num_columns;
		private int num_regions; 
		
		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}
		
		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}	
		public void setValues(int[][] values) {
			board_values = values;
		}

	}
	
//	private boolean region_isFilled(Region a) {
//		for (int i=0; i<a.num_cells; i++) {
//			int rowNum = a.matrix[i].getRow();
//			int colNum = a.matrix[i].getColumn();
//			if (sudoku.board_values[rowNum][colNum]==-1) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	private int getRegionIndex (int row, int col) {
		int num=0;
		for (int i=0; i<sudoku.getRegions().length; i++) {
			for (int j=0;j< sudoku.getRegion(i).getCells().length; j++) {
				if (sudoku.getRegion(i).getCells()[j].row == row && sudoku.getRegion(i).getCells()[j].column == col) {
					num=i;
					break;
				}
			}
		}
		return num;
	}
	
	private boolean isSafe (Region region, int RowNum, int ColNum, int num) {
		//upper left
		if (RowNum-1>=0 && RowNum-1<sudoku.num_rows && ColNum-1>=0 && ColNum-1<sudoku.num_columns ) {
			if (sudoku.getValue(RowNum-1, ColNum-1)==num) {
				return false;
			}
		}
		
		//left
		
		if (ColNum-1>=0 && ColNum-1<sudoku.num_columns) {
		
			if (sudoku.getValue(RowNum, ColNum-1)==num) {
				return false;
			}
		}
		
		//bottom left
		if (RowNum+1>=0 && RowNum+1<sudoku.num_rows && ColNum-1>=0 && ColNum-1<sudoku.num_columns ) {
			if (sudoku.getValue(RowNum+1, ColNum-1)==num) {
				return false;
			}
		}
		
		//bottom 
		if (RowNum+1>=0 && RowNum+1<sudoku.num_rows ) {
			if (sudoku.getValue(RowNum+1, ColNum)==num) {
				return false;
			}
		}
	
		//bottom right
		if (RowNum+1>=0 && RowNum+1<sudoku.num_rows && ColNum+1>=0 && ColNum+1<sudoku.num_columns ) {
			if (sudoku.getValue(RowNum+1, ColNum+1)==num) {
				return false;
			}
		}
		
		//right
		if (ColNum+1>=0 && ColNum+1<sudoku.num_columns ) {
			if (sudoku.getValue(RowNum, ColNum+1)==num) {
				return false;
			}
		}
		
		//top right
		if (RowNum-1>=0 && RowNum-1<sudoku.num_rows && ColNum+1>=0 && ColNum+1<sudoku.num_columns ) {
			if (sudoku.getValue(RowNum-1, ColNum+1)==num) {
				return false;
			}
		}
		
		//top
		if (RowNum-1>=0 && RowNum-1<sudoku.num_rows ) {
			if (sudoku.getValue(RowNum-1, ColNum)==num) {
				return false;
			}
		}
		
		
		//if other cells in same region have same number
		for (int i=0; i<region.num_cells; i++) {
			Cell cell = region.matrix[i];
			int CellRow = cell.row;
			int CellCol = cell.column;
			
			if (sudoku.getValue(CellRow, CellCol)==num) {
				return false;
			}
			
			
		}
		
		return true;
		
	}
	
	
	private boolean solveSudoku(Region region) {
		//pass region into solvesudoku
		
		//fill-in each region
		//check if each region is empty first
		//check if it's touching same number 
		//check if any number in the region repeat
	
		for (int i=0; i<sudoku.board_regions.length; i++) {
			//for each region
			Region currentRegion = sudoku.board_regions[i];
			
			//if (region_isFilled(currentRegion) == false) {
			
				//search for an empty cell
				for (int j=0; j<currentRegion.num_cells; j++) {
					
					int rowNum = currentRegion.matrix[j].getRow();
					int colNum = currentRegion.matrix[j].getColumn();
					
					//if the cell is empty
					if (sudoku.board_values[rowNum][colNum]==-1) {
						
						//try all possible nums
						for (int num=1; num<=currentRegion.num_cells; num++) {
							if (isSafe(currentRegion, rowNum, colNum, num)) {
								sudoku.board_values[rowNum][colNum] = num; //insert
								
								//backtrack
								if (solveSudoku(sudoku.board_regions[i])) {
									return true;
								}
							} 
							
						
					}
						return false;
					
					
				}
				}
				
			
		}
		return false;
		
	}
	
	
	private boolean solveSudoku() {
		
		//board = sudoku;
		//check if each region is empty first
		//check if it's touching same number 
		//check if any number in the region repeat
	
		for (int row = 0; row<sudoku.num_rows; row++) {
			for (int column = 0; column<sudoku.num_columns; column++) {
				if (sudoku.getValue(row, column)==-1) {
					int regionNum = getRegionIndex(row, column);
					for (int num = 1; num<=sudoku.getRegion(regionNum).num_cells; num++) {
						if (isSafe(sudoku.getRegion(regionNum), row, column,  num)) {
							sudoku.setValue(row, column, num);
							if (solveSudoku())
								return true;
							else {
								sudoku.setValue(row, column, -1);
							}
						}
					}
					
					return false;
				}
			}
		}
		
    return true;
		
	}	
		
	
	public int[][] solver() {
	
		
		//iterat solvesudoku and pass region
	 //solveSudoku(sudoku.getRegion(0));
		solveSudoku ();
		
		return sudoku.getValues();
	}

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}	
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
	    game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	


}


