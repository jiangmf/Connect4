package connectFour;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.*;
import java.text.*; 




public class Board extends BoardController
{
	public static String [] modes = {"Player vs Player", "Player vs Computer"};
	public static String mode;
	private static boolean playingAgainstComputer; 
	private static BoardController controller= new BoardController(); 
	private static GameAI dumbAI = new GameAI();
	public static void main(String[] args){
		mode = (String)JOptionPane.showInputDialog(
				null,
				"Select Game Mode",
				"Game Mode",
				JOptionPane.PLAIN_MESSAGE,
				null, modes,
				"--------");
		//mode = mode.toLowerCase();
		if(mode == modes[1]) playingAgainstComputer = true;
		else playingAgainstComputer = false;
		
		 
		BoardGUI theBoard = new BoardGUI();
		theBoard.setTitle("Connect Four");
		theBoard.setVisible(true);
		theBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theBoard.setSize(500, 500);
		//theBoard.setResizable(false); 
		//theBoard.pack(); 
	}
	
	
	public static class BoardGUI  extends JFrame implements ActionListener
	/*The reason this class implements the ActionListener class
	 * is to allow the buttons that add tokens to the board to be clicked
	 * more than once*/
	{	
		//the integers are click counters
		private int cc1=0;
		private int cc2=0;
		private int cc3=0;
		private int cc4=0;
		private int cc5=0;
		private int cc6=0;
		private int cc7=0;
		//this object relays messages from the GUI to the Model class, hence name of controller 
		
		//add buttons
		private JButton addC1 = new JButton("Add");
		private JButton addC2 = new JButton("Add");
		private JButton addC3 = new JButton("Add");
		private JButton addC4 = new JButton("Add");
		private JButton addC5 = new JButton("Add");
		private JButton addC6 = new JButton("Add");
		private JButton addC7 = new JButton("Add");
		
		//menu items
		private JMenuItem resetMI = new JMenuItem("Reset");
		private JMenuItem saveMI = new JMenuItem("Save");
		private JMenuItem loadMI = new JMenuItem("Load");
		private JMenuItem instructMI = new JMenuItem("Instructions");
		private JMenuItem quitMI = new JMenuItem("Quit");
		
		//labels
		private JLabel status = new JLabel("Turn:"+controller.getTurn());
	
		
		private JLabel [][]  cells = new JLabel [6][7];//array of labels to repersent the board
		
		// the reason behind this is to at a later point grab the original colour of each created JLabel 
		private Color defaultColour; 
		
		
	
		public BoardGUI(){
			 
			//Here is where the menu items get sorted into menus
			JMenu actionsMenu = new JMenu("Actions");
			actionsMenu.add(resetMI);
			actionsMenu.add(saveMI);
			actionsMenu.add(loadMI);
			actionsMenu.add(quitMI);
			
			JMenu helpMenu = new JMenu("Help");
			helpMenu.add(instructMI);
			
			JMenuBar boardBar = new JMenuBar();
			boardBar.add(actionsMenu);
			boardBar.add(helpMenu);
			setJMenuBar(boardBar); 
			
			//create and add buttons to a button panel so they appear in the GUI
			JPanel buttonsPanel = new JPanel(new GridLayout(1,1));
			buttonsPanel.add(addC1);
			buttonsPanel.add(addC2);
			buttonsPanel.add(addC3);
			buttonsPanel.add(addC4);
			buttonsPanel.add(addC5);
			buttonsPanel.add(addC6);
			buttonsPanel.add(addC7);
			

			
			JPanel boardPanel = new JPanel(new GridLayout(6,7));
			this.refresh();
			for(int i = 5; i >=0; i--)
			{
				for(int j = 6; j>=0; j--)
				{
					cells[i][j]= new JLabel("");
					//cells[i][j].setBackground(Color.black);
					cells[i][j].setBorder(BorderFactory.createEtchedBorder());
					cells[i][j].setOpaque(true);
					boardPanel.add(cells[i][j]);
					/*This for loop initates the the board's starting point
					 * it creates each label in the array
					 * it adds them to boardPanel */
				}
			}
			defaultColour = cells[0][0].getBackground(); 
			
			Container container = getContentPane();
			container.add(boardPanel,BorderLayout.CENTER);
			container.add(buttonsPanel,BorderLayout.NORTH);
			container.add(status,BorderLayout.SOUTH);
			//Add the actionListeners 
			quitMI.addActionListener(new QuitListener()); 
			instructMI.addActionListener(new InstructListner());
			resetMI.addActionListener(new ResetListener()); 
			addC1.addActionListener(this);
			addC2.addActionListener(this);
			addC3.addActionListener(this);
			addC4.addActionListener(this);
			addC5.addActionListener(this);
			addC6.addActionListener(this);
			addC7.addActionListener(this);
			saveMI.addActionListener(new SaveListener());
			loadMI.addActionListener(new LoadListener()); 
			
			
			
			
		}

		//This disables all the button on the board
		public void disableAll(){
			addC1.setEnabled(false);
			addC2.setEnabled(false);
			addC3.setEnabled(false);
			addC4.setEnabled(false);
			addC5.setEnabled(false);
			addC6.setEnabled(false);
			addC7.setEnabled(false);
		}
		//enables all the buttons
		private void buttonEnable(){
			addC1.setEnabled(true);
			addC2.setEnabled(true);
			addC3.setEnabled(true);
			addC4.setEnabled(true);
			addC5.setEnabled(true);
			addC6.setEnabled(true);
			addC7.setEnabled(true);
		}
		private void refresh(){
			for(int i = 0; i<6;i++){
				for(int j = 0;j <7; j++){
					if(controller.getColour(i,j)=="red"){
						cells[i][j].setBackground(Color.red);
						if(j==1)cc1++;
						else if(j==2)cc2++;
						else if(j==3)cc3++;
						else if(j==4)cc4++;
						else if(j==5)cc5++;
						else if(j==6)cc6++;
						else if(j==7)cc7++;
						
					}else if(controller.getColour(i,j)=="blue"){
						cells[i][j].setBackground(Color.blue);
						if(j==1)cc1++;
						else if(j==2)cc2++;
						else if(j==3)cc3++;
						else if(j==4)cc4++;
						else if(j==5)cc5++;
						else if(j==6)cc6++;
						else if(j==7)cc7++;
					}
				}
			}
			
			
		}
		
		
		
		//This method checks to see if there is winner, if so, it displays who won, and disables the buttons
		public void winner(String winningColour){
			this.disableAll();
			JOptionPane.showMessageDialog(null, "The winner is "+winningColour); 
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			/*the following if statements all work on the same basis
			 * they check to see which button has been clicked
			 * it then tells the controller to add a chip to the appropriate column
			 * then finds out what colour was placed, and relays that to the user
			 * it uses the number of times a button has been clicked to add o the proper row*/

			if(e.getSource()== addC1){
				controller.add(6);

				
				this.refresh();
				if(cc1==6)addC1.setEnabled(false);//this stops the user from entering more than 6 chips in a column, its the same in all conditional statements
			}
			else if(e.getSource()== addC2){
				controller.add(5);
				/*if(controller.getColour(cc2,5)=="blue")cells[cc2][5].setBackground(Color.blue);
				else cells[cc2][5].setBackground(Color.red);
				cc2++;*/
				this.refresh();
				if(cc2==6)addC2.setEnabled(false);
			}
			else if(e.getSource()== addC3){
				controller.add(4);
		
				this.refresh();
				
				if(cc3==6)addC3.setEnabled(false);
			}
			else if(e.getSource()== addC4){
				controller.add(3);
		
				this.refresh();
				
				if(cc4==6)addC4.setEnabled(false);
			}
			else if(e.getSource()== addC5){
				controller.add(2);
				this.refresh();
				
				if(cc5==6)addC5.setEnabled(false);
			}
			else if(e.getSource()== addC6){
				controller.add(1);
				this.refresh();	
				if(cc6==6)addC6.setEnabled(false);
			}
			else {
				controller.add(0); 
				this.refresh();
				if(cc7==6)addC7.setEnabled(false);
			}
			 
			if(playingAgainstComputer){
				dumbAI.setMove();
				controller.add(dumbAI.getMove());
				this.refresh();
			}
			status.setText("Turn:"+controller.getTurn()); 
		
			//this goes and calls the controller to see if there is a winner, and then the board deals with it
			if(controller.winner() != ""){
				this.winner(controller.winner());
				status.setText("The winner is "+controller.winner()); 
			}

		}
		private class SaveListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				
				String gameTitle = (String)JOptionPane.showInputDialog(
	                    null,
	                    "Name your game",
	                    "Save",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null,
	                    null,
	                    "New_Game");
				
				controller.save(gameTitle,controller.getTurn());
				
				
				
			}
		}
		
		
		private class LoadListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JFileChooser loadGame = new JFileChooser();
				 
				int returnVal = loadGame.showOpenDialog(BoardGUI.this);
				if(returnVal ==JFileChooser.APPROVE_OPTION){
					String fileName = (String)loadGame.getName();
					controller.load(fileName);
				}else JOptionPane.showMessageDialog(null,
						"Cannot load the file",
						"Connect Four",
						 JOptionPane.ERROR_MESSAGE);
				 
				 
				
				for(int i = 0 ; i< 6; i++){
					
					for(int j = 0; j<7; j++){
						if(controller.getColour(i, j)=="red") {
							cells[i][j].setBackground(Color.red);
							if(i==1)cc1++;
							else if(i==2)cc2++;
							else if(i==3)cc3++;
							else if(i==4)cc4++;
							else if(i==5)cc5++;
							else if(i==6)cc6++;
							else if(i==7)cc7++;
						}
						else if(controller.getColour(i, j)=="blue"){
							cells[i][j].setBackground(Color.blue);
							if(i==1)cc1++;
							else if(i==2)cc2++;
							else if(i==3)cc3++;
							else if(i==4)cc4++;
							else if(i==5)cc5++;
							else if(i==6)cc6++;
							else if(i==7)cc7++;
						}
					}
				}
				status.setText("Turn:"+controller.getTurn());
				
			}
		}

		private class QuitListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
			
			}
		}
		
		private class InstructListner implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,
						"A simple connect four game");
			}
		}
		
		
		
		//resets the board
		private class ResetListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				for(int i= 5; i >= 0; i--){
					for(int j = 6; j>=0; j--){
						cells[i][j].setText("");
						cells[i][j].setBackground(defaultColour); 
						cells[i][j].setBorder(BorderFactory.createEtchedBorder());
						cells[i][j].setOpaque(true);
						controller.reset();
					}
				}
				cc1 =0;
				cc2 =0;
				cc3 =0;
				cc4 =0;
				cc5 =0;
				cc6 =0;
				cc7 =0;
				addC1.setEnabled(true);
				addC2.setEnabled(true);
				addC3.setEnabled(true);
				addC4.setEnabled(true);
				addC5.setEnabled(true);
				addC6.setEnabled(true);
				addC7.setEnabled(true);
				status.setText("Turn:"+controller.getTurn());
			}
		}

		

		
	}
	
	
	
}