import java.util.*;

class GuessingGame{
    int guessChecker(int guess, int actual){
        if(guess==actual){
            return 1;
        }
        if(Math.abs(actual-guess)<=2){
            return 2;
        }
        if(Math.abs(actual-guess)<=5){
            return 3;
        }
        if(Math.abs(actual-guess)>5){
            return 4;
        }
        return 0;
    }

    int randomNumberGenerator(int lowerBound, int upperBound){
        return (int)(Math.random()*(upperBound-lowerBound+1)+lowerBound);
    }

    void playGame(Scanner sc){
        System.out.println("Enter the range of your choice: ");
        System.out.println("Enter lower bound: ");
        int lowerBound=sc.nextInt();
        System.out.println("Enter upper bound: ");
        int upperBound=sc.nextInt();
        int actual=randomNumberGenerator(lowerBound,upperBound);
        //System.out.println(actual);

        System.out.println("Enter how many guesses you want: ");
        int guessCount = sc.nextInt();
        int count=1;
        
        while(count<=guessCount){
            System.out.println("Enter your guess..");
            int guess=sc.nextInt();

            if(guessChecker(guess, actual)==1){
                System.out.println("Hurray! You guessed it right.");
                break;
            }
            if(count<guessCount && guessChecker(guess, actual)==2){
                System.out.println("Almost there..");
            }
            if(guessChecker(guess, actual)==3){
                System.out.println("Close to the number");
            }
            if(guessChecker(guess, actual)==4){
                System.out.println("Too far from the number");
            }
            if(count==guessCount && guessChecker(guess, actual)==2){
                System.out.println("Oops! You couldn't guess the number..\nSince you were too close. Would you like to have one more chance?\nY/N");
                String choice = sc.next();
                
                if(choice.contains("Y")){
                    System.out.println("You got a bonus guess!");
                    count-=1;
                }else{
                    break;
                } 
            }
            count+=1;
        }
        if(count>guessCount){
            System.out.println("No more guesses left. You couldn't guess the number.");
        }
    }
}

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("...Welcome to the Guessing Game...");
        int ch;
        GuessingGame newgame;

        do{
            System.out.println("Enter your choice:\n1. Play\n2. Exit");
            ch = sc.nextInt();

            switch(ch){
                case 1: 
                newgame = new GuessingGame();
                newgame.playGame(sc);
                break;

                case 2:
                System.out.println("Exiting game");
                break;

                default:
                System.out.println("Invalid choice. Please try again.");
            }

        }while(ch!=2);
        sc.close();
    }
}
