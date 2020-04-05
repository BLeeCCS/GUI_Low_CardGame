public class DeckOfCards
{
   // This method returns a new random card for the main to use its tests
   static Card randomCardGenerator()
   {
      int randomValue = (int) (Math.random() * 14);
      int randomSuit = (int) (Math.random() * 4 + 1);

      Card.Suit randomS = null;
      switch (randomSuit)
      {
      case 1:
         randomS = Card.Suit.CLUBS;
         break;
      case 2:
         randomS = Card.Suit.DIAMONDS;
         break;
      case 3:
         randomS = Card.Suit.HEARTS;
         break;
      case 4:
         randomS = Card.Suit.SPADES;
         break;
      }

      Card randomCard = new Card(Card.VALUES[randomValue], randomS);

      return randomCard;
   }
    
    static public void main(String args[])
   {
       
   }
}

class Card
{
    public static enum Suit {CLUBS, DIAMONDS, HEARTS, SPADES};
    public static final String[] SUIT_STRING = {"Clubs", "Diamonds",
                                                "Hearts", "Spades"};
    
    // Added Joker as "X"
    public static final char[] VALUES = {'A', '2', '3', '4', '5', '6', '7',
                                         '8', '9', 'T', 'J', 'Q', 'K', 'X'};                                   
    private char value;
    private Suit suit;
    private boolean errorFlag;

    // the array to put the order of the card values in here 
    // with the smallest first, include 'X' for a joker
    public static char[] valuRanks;
    
    public Card(char val, Suit s)
    {
        set(val, s);
    }

    public Card()
    {
        value = 0;
        suit = Suit.CLUBS;
        errorFlag = true;
    }

    public String toString()
    {
        if(errorFlag)
        {
            return "invalid";
        }
        else
        {
            String retString = "";
            switch(suit)
            {
                case CLUBS:
                    retString = value + " of Clubs";
                    break;
                case DIAMONDS:
                    retString = value + " of Diamonds";
                    break;
                case SPADES:
                    retString = value + " of Spades";
                    break;
                case HEARTS:
                    retString = value + " of Hearts";
                    break;
                default: 
                    retString = "Unknown suit type";
            }
            return retString;
        }
    }

    public boolean set(char val, Suit s)
    {
        
        if(isValid(val, s))
        {
            value = val;
            suit = s;
            errorFlag = false;
            return true; 
        }
        else
        {
            errorFlag = true;
            return false;
        }
    }

    public char getValue() { return value; }
    public Suit getSuit() { return suit; }
    public boolean getErrorFlag() { return errorFlag; }

    public boolean equals(Card card)
    {
        char otherValue = card.getValue();
        Suit otherSuit = card.getSuit();

        if(value == otherValue && suit == otherSuit)
            return true;
        return false;
    }

    private boolean isValid(char value, Suit suit)
    {
        boolean found = false;

        for (char c : VALUES)
        {
            if(value == c)
                found = true;
        }

        if(!found)
        {
            return false;
        }
            
        if(suit != Suit.CLUBS && 
            suit != Suit.DIAMONDS && 
            suit != Suit.SPADES && 
            suit != Suit.HEARTS)
        {
            return false;
        }

        return true;
    }
    
    // This method will sort the incoming array of cards using a bubble sort routine.
    static void arraySort(Card[] cardArray, int arraySize)
    {
       for (int i = 0; i < arraySize - 1; i++)
       {
          for (int j = 0; j < arraySize - i - 1; j++)
          {
             if (cardArray[j].getValue() > cardArray[j+1].getValue())
             {
                char tempVal = cardArray[j].getValue();
                
                cardArray[j].set(cardArray[j+1].getValue(),cardArray[j+1].getSuit());
                cardArray[j+1].set(tempVal, cardArray[j].getSuit());
             }
          }
       }
    }
}


class Hand
{
   public static int MAX_CARDS;
   private Card[] myCards;
   private int myCardsIndex;
   
   Hand()
   {
       MAX_CARDS = 100;
       myCardsIndex = -1;
       myCards = new Card[MAX_CARDS];
   }
   
   void resetHand()
   {
    myCardsIndex = -1;
   }
   
   boolean takeCard(Card card)
   {
      if(card.getErrorFlag())
        return false;
      if (!isFull())
      {
         myCardsIndex++;
         myCards[myCardsIndex] = card;
         
         return true;
      }
      return false;
   }
   
   Card playCard()
   {
      return myCards[myCardsIndex--];   
   }
   
   public String toString()
   {
      String cardsInHandStr = "";
      int k = 0;
      for (Card i : myCards)
      {
          if(i != null)
          {
                cardsInHandStr += i.toString() + ", ";
                k++;
          }
          else
          {
              return cardsInHandStr;
          }
      }   
      return cardsInHandStr;
   }
   
   public int getNumCards()
   {
      return myCardsIndex + 1;
   }
   
   Card inspectCard(int k)
   {
      if (k > myCardsIndex) 
      {  
         Card tempCard = new Card();
         tempCard.set('z', null); 
         return tempCard;
      }
      return myCards[k];
   }
   
   private boolean isFull()
   {
      return myCardsIndex == MAX_CARDS;
   }
   
   // This method will sort the hand 
   // by calling the arraySort() method in the Card class.
   public void sort()
   {
      Card.arraySort(myCards,myCards.length);
   }
   
   /* In order for playCard() to work in the cardGameFramework class, 
      add the following to the Hand class. This will remove the card at a location 
      and slide all of the cards down one spot in the myCards array.  
      (We will use this next week) */
//   public Card playCard(int cardIndex)
//   {
//      if ( numCards == 0 ) //error
//      {
//         //Creates a card that does not work
//         return new Card('M', Card.Suit.spades);
//      }
//      //Decreases numCards.
//      Card card = myCards[cardIndex];
//      
//      numCards--;
//      for(int i = cardIndex; i < numCards; i++)
//      {
//         myCards[i] = myCards[i+1];
//      }
//      
//      myCards[numCards] = null;
//      
//      return card;
//    }
}

class Deck
{
   public final int MAX_CARDS = 6*56;
   private static Card[] masterPack = new Card[56];
   private Card[] cards;
   private int topCard;    

   Deck()
   {     
      allocateMasterPack();
      int numCards = 56;
      topCard = numCards - 1;
      cards = new Card[numCards];
      for(int i = 0; i < numCards; i++)
      {
         cards[i] = masterPack[i];
      }
   }
   
   Deck(int numPacks)
   {     
      allocateMasterPack();
      int numCards = numPacks * 56;
      topCard = numCards - 1;
      cards = new Card[numCards];

      for(int i = 0; i < numPacks; i++)
      {
          for(int j = 0; j < 56; j++)
          {
              cards[56*i+j] = masterPack[j];
          }
      }
   }

   public void init(int numPacks)
   {
      if (numPacks > 6)
      {
         numPacks = 6;
      }
      int numOfCards = numPacks * 56;
      topCard = numOfCards - 1;

      for (int i = 0; i < numPacks; i++)
      {
         int k = i * 56;
         for (int j = 0; j < 56; j++)
         {
            cards[k] = masterPack[j];
            k++;
         }
      }
   }

   public void shuffle() 
   {
      int numOfCards = cards.length;
      for (int i = 0; i < numOfCards; i++)
      {
         int random = i + (int)(Math.random() * (numOfCards - i));
         Card temp = cards[random];
         cards[random] = cards[i];
         cards[i] = temp;
      }
   }

   public Card dealCard() 
   {
      if(topCard < 0)
      {
         Card empty = new Card();
         return empty;
      }

      Card tempCard = new Card();
      tempCard = cards[topCard];
      cards[topCard] = null;
      topCard--;
      return tempCard;
   }

   public final Card topCardAccessor() 
   {
      if(topCard < 0)
      {
         Card empty = new Card();
         return empty;
      }
      return cards[topCard];
   }

   public final Card inspectCard(int k) 
   {
      int numOfCards = cards.length;
      if (k > numOfCards | --k < 0)
      {
         Card empty = new Card();
         return empty;
      }
      return cards[k];
   }
   
   public static void allocateMasterPack()
   {
      if(masterPack[0] != null)
         return;
      Card.Suit[] placeHolder = Card.Suit.values();
      int k = 0;
      for(int i = 0;i < 4; i++)
      {
         for(int j = 0; j < 14; j++)
         {
            masterPack[k] = new Card(Card.VALUES[j], placeHolder[i]);
            k++;
         }
      }
   }
   
   // This method makes sure that there are not too many instances of the card in the deck if you add it.  
   // Return false if there will be too many.  It should put the card on the top of the deck.
   boolean addCard(Card card)
   {
      if (topCard == MAX_CARDS)
         return false;
      
      cards[topCard + 1] = card;
      
      return true;
   }
   
   // This method removes a specific card from the deck.
   // Put the current top card into its place.
   // Be sure the card you need is actually still in the deck, if not return false.
   boolean removeCard(Card card)
   {
      for (int i = 0; i < cards.length; i++)
      {
         if (cards[i].equals(card)) 
         {
            cards[i] = cards[topCard];
            cards[topCard] = card;
            return true;
         }
      }
      
      return false;
   }
   
   // This method put all of the cards in the deck back into the right order according to their values.
   public void sort()
   {
      Card.arraySort(cards, cards.length);
   }
   
   // This method returns the number of cards remaining in the deck.
   public final int getNumCards()
   {
      return cards.length;
   }
}
