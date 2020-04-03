/*  Title: GUI Cards Phase 2
 *  Authors: Justin Dinkelbach, Brandon Lee, Timothy Hinea, David Sullivan
 *  Date: April 4, 2020
 *  Class: CST 338
 */

import javax.swing.*;
import java.awt.*;
   
public class Assig5 
{
   /* static for the 57 icons and their corresponding labels
    * normally we would not have a separate label for each card, but
    * if we want to display all at once using labels, we need to.
    */
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
   
   static void loadCardIcons()
   {
      /* build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
       * in a SHORT loop.  For each file name, read it in and use it to
       * instantiate each of the 57 Icons in the icon[] array.
       */
      int k = 0;
      for (int i = 0; i < 14; i++)
         for(int j = 0;j < 4; j++)
           icon[k++] = new ImageIcon(turnIntIntoCardValue(i) 
                 + turnIntIntoCardSuit(j) + ".gif");
      
      icon[56] = new ImageIcon("XS.gif");
   }
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      String placeHolder = "";
      if(k == 0)
         placeHolder = "2";
      else if(k == 1)
         placeHolder = "3";
      else if(k == 2)
         placeHolder = "4";
      else if(k == 3)
         placeHolder = "5";
      else if(k == 4)
         placeHolder = "6";
      else if(k == 5)
         placeHolder = "7";
      else if(k == 6)
         placeHolder = "8";
      else if(k == 7)
         placeHolder = "9";
      else if(k == 8)
         placeHolder = "T";
      else if(k == 9)
         placeHolder = "J";
      else if(k == 10)
         placeHolder ="Q";
      else if(k == 11)
         placeHolder ="K";
      else if(k == 12)
         placeHolder ="A";
      else if(k == 13)
         placeHolder ="X";
      return placeHolder;

   }
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      String placeHolder = "";
      if(j == 0)
         placeHolder ="C";
      if(j == 1)
         placeHolder = "D";
      else if(j == 2)
         placeHolder = "H";
      else if(j == 3) 
         placeHolder = "S";
      return placeHolder;
   }
   
   /***************************************************************************
    *                                  Main                                   *
    * A simple main to throw all the JLabels out there for the world to see   *                               
    ***************************************************************************/
   public static void main(String[] args)
   {
      int k;
      
      // prepare the image icon array
      loadCardIcons();
      
      // establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);   
      frmMyWindow.setLayout(layout);
      
      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);
      
      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);
   }
   
   /***************************************************************************
    *                              CardTable class                            *
    ***************************************************************************/
   class CardTable extends JFrame
   {
      // Static Member Data
      static int MAX_CARDS_PER_HAND = 56;
      static int MAX_PLAYERS = 2;   //allows 2 people per game
      
      // Private Data
      private int numCardsPerHand;
      private int numPlayers;
      
      // Public Data
      public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
      
      // Constructor - filters input, adds panels to JFrame, establishes layout
      CardTable(String title, int numCardsPerHand, int numPlayers)
      {
         // Set title of window frame
         super(title);
         
         setLayout(new BorderLayout());
         
         // Create the three Panels
         JPanel pnlComputerHand = new JPanel(new GridLayout(1, numCardsPerHand));
         JPanel pnlHumanHand = new JPanel(new GridLayout(1, numCardsPerHand));
         JPanel pnlPlayArea = new JPanel(new GridLayout(1, numPlayers));
         
         // Create TitledBorders for JPanels
         pnlComputerHand.setBorder(BorderFactory.createTitledBorder("Computer's"
               + " Hand:"));
         pnlComputerHand.setBorder(BorderFactory.createTitledBorder("Player's"
               + " Hand:"));
         pnlComputerHand.setBorder(BorderFactory.createTitledBorder("Playing"
               + " Area"));
         
         
         // Set location of Panels
         add(pnlComputerHand, BorderLayout.NORTH);
         add(pnlHumanHand, BorderLayout.SOUTH);
         add(pnlPlayArea, BorderLayout.CENTER);
 
      }
      
      // Accessor for numCardsPerHand
      public int getNumCardsPerHand()
      {
         return numCardsPerHand;
      }
      
      // Accessor for numPLayers
      public int getNumPlayers()
      {
         return numPlayers;
      }
   }
   
   /***************************************************************************
    *                              GUICard class                              *
    ***************************************************************************/
   static class GUICard
   {
      // Members
      private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = K + Jkr
      private static Icon iconBack;
      
      // Keeps track of whether iconCards has already been loaded
      static boolean iconsLoaded = false;
      
      // Generates the image Icon array from files - when to call?
      static void loadCardIcons()    
      {
         if(iconsLoaded)   // checks if already loaded
            return;
         for (int i = 0; i < 14; i++)  // for 14 card values
         {
            for(int j = 0;j < 4; j++)  // for 4 suits
              iconCards[i][j] = new ImageIcon(turnIntIntoCardValue(i) 
                    + turnIntIntoCardSuit(j) + ".gif");
         }  
        iconBack = new ImageIcon("images/BK.gif"); // Creates back of cards
        iconsLoaded = true;  
      }
      
      // Takes a Card object from the client and returns the Icon for that card
      public static Icon getIcon(Card card)
      {
         return iconCards[valueAsInt(card)][suitAsInt(card)];
      }
      
      // Returns back of card
      public static Icon getBackCardIcon()
      {
         return iconBack;
      }
   }
}



