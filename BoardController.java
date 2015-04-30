package connectFour;

public class BoardController {
	private String turn = this.whoOnFirst();//commnicates whose turn it is
	private BoardData board = new BoardData(); 

	
	//this determines who goes first randomly, by using build in Math random function
	private String whoOnFirst(){
		final double who = Math.random();
		if(who <0.5){ 
		this.turn  = "blue";
		return this.turn;
		}else{
		this.turn  = "red";
		return this.turn;
		} 
		
	}
	
	public String getTurn(){
		return this.turn; 
	}
	
	public void changeTurn(){
		if (this.turn =="blue"){
			this.turn = "red";
			
		}else{
			this.turn ="blue";
		}
	}
	
	/*public void compEnabled(boolean playAgainstComp){
		this.board = new BoardData(playAgainstComp);
	}*/
	
	//used to commnicate the colour in the array to the board
	public String getColour(int row, int column)
	{
		
		return board.GetColor(row, column); 
		
	}
	//tells the model to reset its data
	public void reset()
	{
		board.Reset();
		this.whoOnFirst(); 
	}
	
	
	public void add(int column){
		if(turn == "blue"){
			board.AddBlue(column);
			
		}else{
			board.AddRed(column);
		}
		this.changeTurn();
	}
	//tells board who won, by asking the model if there is a winner
	public String winner(){
		String winner ="";
		if(board.isWin() == "blue")winner= "blue";
		else if(board.isWin()=="red")winner= "red";
		return winner; 
	}
	
	public void save(String saveFile,String turn){
		board.save(saveFile,turn);
	}
	
	public void load(String gameFile){
		board.load(gameFile);
		this.turn = board.getTurn(); 
	}
	

	
}
