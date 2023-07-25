import java.util.Scanner;
public class AnimalGuess
{
   private static Scanner stdin = new Scanner(System.in);
   
   /**
   * The main method prints instructions and repeatedly plays the 
   * animal-guessing game. As the game is played, the taxonomy tree
   * grows by learning new animals. The <CODE>String</CODE> argument
   * (<CODE>args</CODE>) is not used in this implementation.
   **/
   public static void main(String[ ] args)
   {
      BTNode<String> root;

      instruct( );
      root = beginningTree( );
      do
         play(root);
      while (query("Shall we play again?"));

      System.out.println("Thanks for teaching me a thing or two.");
   }
   
   
   /**
   * Print instructions for the animal-guessing game.
   **/
   public static void instruct( )
   {
      System.out.println("Please think of an animal.");
      System.out.println("I will ask some yes/no questions to try to figure");
      System.out.println("out what you are.");
   }
   

   /**
   * Play one round of the animal guessing game.
   * @param <CODE>current</CODE>
   *   a reference to the root node of a binary taxonomy tree that will be
   *   used to play the game.
   * <dt><b>Postcondition:</b><dd>
   *   The method has played one round of the game, and possibly
   *   added new information about a new animal.
   * @exception java.lang.OutOfMemoryError
   *   Indicates that there is insufficient memory to add new
   *   information to the tree.
   **/
   public static void play(BTNode<String> current)
   {
      while (!current.isLeaf( ))
      {
         if (query(current.getData( )))
            current = current.getLeft( );
         else
            current = current.getRight( );
      }

      System.out.print("My guess is " + current.getData( ) + ". ");
      if (!query("Am I right?"))
         learn(current);
      else
         System.out.println("I knew it all along!");
   }
   

   /**
   * Construct a small taxonomy tree with four animals.
   * @param - none
   * @return
   *   a reference to the root of a taxonomy tree with the animals:
   *   kangaroo, mouse, trout, robin.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory to create the tree.
   **/
   public static BTNode<String> beginningTree( )   
   {
      BTNode<String> root;
      BTNode<String> childLevel1;
      BTNode<String> childLevel2;

      final String ROOT_QUESTION = "Are you a mammal?";
      final String CAT_QUESTION = "Are you bigger than a cat?";
      final String UNDERWATER_QUESTION = "Do you live underwater?";
      final String MARSUPIAL_QUESTION = "Are you a marsupial?";
      final String VERTEBRATE_QUESTION = "Are you a vertebrate?";
      final String FLYING_QUESTION = "Can you fly?";
      
      // Create the root node with the question �Are you a mammal?�
      root = new BTNode<String>(ROOT_QUESTION, null, null);

      // Create and attach the left subtree.
      childLevel1 = new BTNode<String>(CAT_QUESTION, null, null);
      childLevel2 = new BTNode<String>(MARSUPIAL_QUESTION, null, null);
      childLevel2.setLeft(new BTNode<String>("Kangaroo", null, null));
      childLevel2.setRight(new BTNode<String>("Racoon", null, null));
      childLevel1.setLeft(childLevel2);
      childLevel1.setRight(new BTNode<String>("Mouse", null, null));
      root.setLeft(childLevel1);

      // Create and attach the right subtree.
      childLevel1 = new BTNode<String>(UNDERWATER_QUESTION, null, null);
      childLevel2 = new BTNode<String>(VERTEBRATE_QUESTION, null, null);
      childLevel2.setLeft(new BTNode<String>("Trout", null, null));
      childLevel2.setRight(new BTNode<String>("Squid", null, null));
      childLevel1.setLeft(childLevel2);
      
      childLevel2 = new BTNode<String>(FLYING_QUESTION, null, null);
      childLevel2.setLeft(new BTNode<String>("Robin", null, null));
      childLevel2.setRight(new BTNode<String>("Snake", null, null));
      childLevel1.setRight(childLevel2);
      root.setRight(childLevel1);

      return root;
   }
 
 
   /**
   * Elicits information from the user to improve a binary taxonomy tree.
   * @param <CODE>current</CODE>
   *   a reference to a leaf node of a binary taxonomy tree
   * <dt><b>Precondition:</b><dd>
   *   <CODE>current</CODE> is a reference to a leaf in a binary
   *   taxonomy tree
   * <dt><b>Postcondition:</b><dd>
   *   Information has been elicited from the user, and the tree has
   *   been improved.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory to add new
   *   information to the tree. 
   **/
   public static void learn(BTNode<String> current)
   // Precondition: current is a reference to a leaf in a taxonomy tree. This
   // leaf contains a wrong guess that was just made.
   // Postcondition: Information has been elicited from the user, and the tree
   // has been improved.
   {
      String guessAnimal;   // The animal that was just guessed
      String correctAnimal; // The animal that the user was thinking of
      String newQuestion;   // A question to distinguish the two animals
      
      // Set Strings for the guessed animal, correct animal and a new question.
      guessAnimal = current.getData( );
      System.out.println("I give up. What are you? ");
      correctAnimal = stdin.nextLine( );
      System.out.println("Please type a yes/no question that will distinguish a");
      System.out.println(correctAnimal + " from a " + guessAnimal + ".");
      newQuestion = stdin.nextLine( );
      
      // Put the new question in the current node, and add two new children.
      current.setData(newQuestion);
      System.out.println("As a " + correctAnimal + ", " + newQuestion);
      if (query("Please answer"))
      {
         current.setLeft(new BTNode<String>(correctAnimal, null, null));
         current.setRight(new BTNode<String>(guessAnimal, null, null));
      }
      else
      {
         current.setLeft(new BTNode<String>(guessAnimal, null, null));
         current.setRight(new BTNode<String>(correctAnimal, null, null));
      }         
   }

   public static boolean query(String prompt)
   {
      String answer;
    
      System.out.print(prompt + " [Y or N]: ");
      answer = stdin.nextLine( ).toUpperCase( );
      while (!answer.startsWith("Y") && !answer.startsWith("N"))
      {
     System.out.print("Invalid response. Please type Y or N: ");
     answer = stdin.nextLine( ).toUpperCase( );
      }

      return answer.startsWith("Y");
   }

}

