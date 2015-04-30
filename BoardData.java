package connectFour;
import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;

public class BoardData {
private String [][] data;
private String turn; 
	public BoardData(){
		this.data = new String[6][7];
		for(int row=0;row<6;row++){
			for(int col=0;col<7;col++){
				data[row][col] = "n/a";
			}
		}
	}
	
	//copies the data from the board and saves it to be loaded at a future time
	public BoardData Copy(){
		BoardData d = new BoardData();
		for(int row=0;row<6;row++){
			for(int col=0;col<7;col++){
				d.data[row][col] = this.data[row][col];
			}
		}
		return d;
	}
	public void save(String fileName,String turn){
		try{
			String gameState = this.stateToString();
			File game = new File(fileName+".txt"); 
			
			FileWriter fileWriter = new FileWriter(game);
			fileWriter.write(gameState);
			fileWriter.write("\n"+turn);
			fileWriter.close(); 
			
		
			
		}catch(IOException e) 
		{
			JOptionPane.showMessageDialog(null,
					"Something has gone wrong with the save",
					"Connect Four",
					 JOptionPane.ERROR_MESSAGE);
		}
	
		
		
	}
	
	public void load(String fileName){
		try{
			
			File gameFile= new File(fileName);
			
			Scanner gameDataReader = new Scanner(new FileReader(gameFile));
			
			while(gameDataReader.hasNext()){
				
			
				for(int i=0;i<6;i++){
					for(int j=0;j<7;j++){
						data[i][j] = gameDataReader.next();
					}
				}
				this.turn+=gameDataReader.next();
			}
		}catch(IOException e){
			JOptionPane.showMessageDialog(null,
					"Cannot load the file: "+fileName,
					"Connect Four",
					 JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void AddRed(int col){
		int row = 0;
		while(data[row][col] != "n/a"){
			row++;
		}
		data[row][col] = "red";
	}
	
	public void AddBlue(int col){
		int row = 0;
		while(data[row][col] != "n/a"){
			row++;
		}
		data[row][col] = "blue";
	}
	
	//returns the colour at the specified location
	public String GetColor(int row, int col){
		return this.data[row][col];
	}
	
	//resets the game and the board
	public void Reset(){
		for(int row=0;row<6;row++){
			for(int col=0;col<7;col++){
				data[row][col] = "";
			}
		}
	}

	private String stateToString(){
		String toString="";
		for(int i = 0; i<6;i++){
			for(int j = 0; j<7; j++){
				toString +=  data[i][j] +"\n"; 
			}
		}
		
		return toString; 
	}
	public String isWin(){
		//vertical check
		String winner = "";
		for (int col=0;col<7;col++){   
            for (int row=0;row<3;row++){ 
                    if (data[row][col].equals(data[row+1][col]) && data[row][col].equals(data[row+2][col]) && data[row][col].equals(data[row+3][col]) && data[row][col].equals("red"))winner="red";
                    if (data[row][col].equals(data[row+1][col]) && data[row][col].equals(data[row+2][col]) && data[row][col].equals(data[row+3][col]) && data[row][col].equals("blue"))winner="blue";
            }
		}
		//horizontal check
		for (int row=0;row<6;row++){
            for(int col=0;col<4;col++){
                    if (data[row][col].equals(data[row][col+1]) && data[row][col].equals(data[row][col+2]) && data[row][col].equals(data[row][col+3]) && data[row][col].equals("red"))winner="red";
                    if (data[row][col].equals(data[row][col+1]) && data[row][col].equals(data[row][col+2]) && data[row][col].equals(data[row][col+3]) && data[row][col].equals("blue"))winner="blue";
            }                                      
        }
		//diagonal check
		for (int row=0;row<3;row++){ 
            for(int col=0;col<4;col++){   
                    if (data[row][col].equals(data[row+1][col+1]) && data[row][col].equals(data[row+2][col+2]) && data[row][col].equals(data[row+3][col+3]) && data[row][col].equals("red"))winner="red";
                    if (data[row][col].equals(data[row+1][col+1]) && data[row][col].equals(data[row+2][col+2]) && data[row][col].equals(data[row+3][col+3]) && data[row][col].equals("blue"))winner="blue";          
            }
		}
   
		for (int row=3;row<6;row++){ 
            for(int col=0;col<4;col++){   
                    if (data[row][col].equals(data[row-1][col+1]) && data[row][col].equals(data[row-2][col+2]) && data[row][col].equals(data[row-3][col+3]) && data[row][col].equals("red"))winner="red";
                    if (data[row][col].equals(data[row-1][col+1]) && data[row][col].equals(data[row-2][col+2]) && data[row][col].equals(data[row-3][col+3]) && data[row][col].equals("blue"))winner="blue";
            }
		}
		return winner;
	}
	
	public String getTurn(){
		return this.turn; 
	}
}