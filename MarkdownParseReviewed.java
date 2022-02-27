// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParseReviewed {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        ArrayList<Integer> openBracketIndexes = new ArrayList<>();
        ArrayList<Integer> openParenIndexes = new ArrayList<>();
        ArrayList<Integer> closeBracketIndexes = new ArrayList<>();
        ArrayList<Integer> closeParenIndexes = new ArrayList<>();
        //make and arraylist of all of the indexes of the occurances of the 
        //different [, ], (, and )

        //for the [
        int currentIndex = 0;
        while(currentIndex < markdown.lastIndexOf("[")) {
            int openBracketIndex = markdown.indexOf("[", currentIndex);
            openBracketIndexes.add(openBracketIndex);
            currentIndex = openBracketIndex + 1;
        }
        //for ]
        currentIndex = 0;
        while(currentIndex < markdown.lastIndexOf("]")) {
            int closeBracketIndex = markdown.indexOf("]", currentIndex);
            closeBracketIndexes.add(closeBracketIndex);
            currentIndex = closeBracketIndex + 1;
        }
        //for (
        currentIndex = 0;
        while(currentIndex < markdown.lastIndexOf("(")) {
            int openParenIndex = markdown.indexOf("(", currentIndex);
            openParenIndexes.add(openParenIndex);
            currentIndex = openParenIndex + 1;
        }
        //for )
        currentIndex = 0;
        while(currentIndex < markdown.lastIndexOf(")")) {
            int closeParenIndex = markdown.indexOf(")", currentIndex);
            closeParenIndexes.add(closeParenIndex);
            currentIndex = closeParenIndex + 1;
        }

        //checks the character before the open
        for(int i = 0; i < openParenIndexes.size(); i++){
            if(openParenIndexes.get(i) == 0){
                openParenIndexes.remove(i);
                i--;
            }else if(markdown.charAt(openParenIndexes.get(i) - 1) != ']'){
                openParenIndexes.remove(i);
                i--;
            }
        }

        //does the image type check
        for(int i = 0; i < openBracketIndexes.size(); i++){
            if(openBracketIndexes.get(i) == 0){
                openBracketIndexes.remove(i);
                i--;
            }else if(markdown.charAt(openBracketIndexes.get(i) - 1) == '!'){
                openBracketIndexes.remove(i);
                i--;
            }
        }

        //does check for the closed brackets that must have a (, [, or : after for it to remain.
        for(int i = 0; i < closeBracketIndexes.size(); i++){
            if(closeBracketIndexes.get(i) == 0){
                closeBracketIndexes.remove(i);
                i--;
            }else if(markdown.charAt(closeBracketIndexes.get(i) - 1) != '[' || 
                markdown.charAt(closeBracketIndexes.get(i) - 1) != '(' ||
                markdown.charAt(closeBracketIndexes.get(i) - 1) != ':'){

                closeBracketIndexes.remove(i);
                i--;
            }
        }


        /**
         * basically get an Integer Array of all of the indexes of [, ],(, and )
         * to then compare the contents between each bracket and each parenthesis
         * 
         * Note:ignore check previous index if index == 0. if it's in the [ list then
         *      skip that index and go to the next in the list elsewise remove the index
         *      from the list
         * 
         * 1.   also remove all places where there is  \ character 
         *      before the []() but if there is a second \\ then ignore.
         * 2.   check all closed brackets to see if the next character is either (
         *      or [ due to how markdown reads links as [text](link) or
         *      [text][reference]
         * 3.   if it follows the [text][reference] type, save the references into a
         *      ArrayList<String> 
         *              note: do the loops above to check if there's any "]: " to 
         *                    see if it uses references and to mark where references could be
         * 4.   create data class indexPair(int indexOpen, int indexClosed) and add all the
         *      indexes of [ and ] that aren't disqualified previously
         * 5.   disqualify pairs based on whether they contain
         */

        for(int openParen:openParenIndexes){
            toReturn.add(markdown.substring(openParen+1, markdown.indexOf(")",openParen)));
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}