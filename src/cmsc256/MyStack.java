package cmsc256;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Scanner;

import bridges.base.SLelement;
public class MyStack <E> implements StackInterface<E>{

    private SLelement<E> top;
    private int size;

    public MyStack(){
        clear();
    }

    public MyStack(int size){
        this(); //calls default
    }

    @Override
    public void push(E newEntry) {
        if(newEntry == null){
            throw new IllegalArgumentException(); //if null throw exception
        }
        top = new SLelement<E>(newEntry, top); // else push newEntry to the tp of the stack
        size++; //increase the size of the stack

    }

    @Override
    public E pop() {
        if(isEmpty()){ //if the stack is empty throw exception
            throw new EmptyStackException();
        }
        E it = top.getValue();
        top = top.getNext();
        size--;
        return it;
    }

    @Override
    public E peek() {
        if(isEmpty()){ //if the stack is empty throw exception
            throw new EmptyStackException();
        }
        return top.getValue(); //return the top value
    }

    @Override
    public boolean isEmpty() {
        return size == 0; // if size equals 0 return true, else returns false

    }

    @Override
    public void clear() {
        top = null;
        size = 0;
    }


    public static boolean isBalanced(File webpage) throws FileNotFoundException {

            MyStack stack = new MyStack(); //create a stack to hold each tag
            Scanner input = new Scanner(webpage); //read the webpage file
            boolean opening = true; //boolean variable to check if the tag is to open or close


            while (input.hasNextLine()) { //keep reading the webpage until out of lines

                String line = input.nextLine();  // create a string for the webpage line that we are reading

                for(int i = 0; i < line.length(); i++){  //go through each character of the webpage
                    String match = "";  //create a string variable that will be added to the stack
                    Character character = line.charAt(i); //create a character that we will use to compare

                    if(character =='<'){ //once we find a < character we can start
                        opening = true;

                        try {
                            for (int j = i; line.charAt(j) != '>'; j++) { //Loop to add characters to a String until it hit a > character

                                if (line.charAt(j) == '/') { // check to see if this is a closing or opening
                                    opening = false; // because we found '/', that indicates a closing tag that we will later pop
                                    j++; // we want to skip the character '/', so we can compare to an opening teg
                                }

//                              match.concat(String.valueOf(line.charAt(j))); //add the characters to the string
                                match += line.charAt(j); //add the characters to the string
                                i = j;
                            }
                        }
                        catch(Exception e){
                            return false;
                        }

                        if(i < line.length() && line.charAt(i + 1) == '>') {
//                          match.concat(String.valueOf('>')); //add the '>' so everything matches
                            match += '>'; //add the '>' to the string so everything matches correctly
                            i++; // increment i
                        }
                        if(opening){ //this is an opener
                            stack.push(match); //add the string to the stack
                        }

                        else{ //this is the closer
                            if(stack.isEmpty()){ //if the stack is empty we can immediately return false
                                return false;
                            }
                            if(match.equals(stack.peek())){ //check to see if this closer matches the opener
                                stack.pop(); //if it matches remove from the stack

                            }
                            else{
                                return false; //if it doesn't match the top, we can return false
                            }
                        }
                    }
                }
            }

        return stack.isEmpty(); //the stack should be empty by the end
    }

}
