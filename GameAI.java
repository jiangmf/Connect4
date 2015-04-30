package connectFour;
import java.util.*;
/*********
 * 
 * @author mario
 * this is the weak AI, it just picks a number between 1 and 6 to drop into the board
 * just needs the column to throw it in, and a boolean to know if it is it's turn
 * 
 */
public class GameAI {
	private int move;

	
	//constructor just intakes if it is the computer's turn. 

	
	public int getMove(){
		return move;
	}
	 
	public void setMove(){
		move();
	}
	

	
	private void move(){
		Random rand = new Random();
		
		this.move = rand.nextInt(6)+1;  
	}
	
}
