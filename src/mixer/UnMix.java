package mixer;

import java.io.*;
import java.util.Scanner;

public class UnMix {
    
	
	private DoubleLinkedList<Character> message;


    private Mix mixer;

    
	public UnMix(String message) {
        this.message = new DoubleLinkedList<Character>();
        mixer = new Mix(message);
    }

    
	public static void main(String[] args) {
        UnMix v = new UnMix(args[1]);
        v.unMixture(args[0], args[1]);
    }

    
	public String processCommand(String command) {
        
        String[] parsedCmd = command.split("\\s+");

        switch(parsedCmd[0]) {
        case "b":
            int index = Integer.parseInt(parsedCmd[parsedCmd.length-1]);
            String toInsert = "";

            if(parsedCmd.length > 3) {
                toInsert = command.substring(2, command.length() - parsedCmd[parsedCmd.length-1].length() - 1);
            }

            mixer.insertbefore(toInsert, index);
            break;

        case "r":
            
            // Check if replace or remove command shoule be used
            boolean removeFlag;
            String intRegx = "-?\\d+";
            if(parsedCmd[1].matches(intRegx) && parsedCmd[2].matches(intRegx)) {

                // Arguments are not integers
                removeFlag = true;
            }
            else {
                removeFlag = false;
            }

            if(removeFlag) {

                // Use remove command
                mixer.remove(Integer.parseInt(parsedCmd[1]), Integer.parseInt(parsedCmd[2]));
            }
            else {

                // Use replace command
                mixer.replace(parsedCmd[1].charAt(0), parsedCmd[2].charAt(0));
            }

            break;

        case "d":
            // TODO: process delete command
            break;

        default:
            System.out.println("Unknown command: " + parsedCmd[0]);
        } 

        return mixer.getMessage();
    }

    
	private void unMixture(String filename, String userMessage) {
        String original = UnMixUsingFile (filename, userMessage);
        System.out.println ("The Original message was: " + original);
    }

	
    public String UnMixUsingFile(String filename, String userMessage) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            userMessage = processCommand(command);
        } 
        return userMessage;
    }
}
