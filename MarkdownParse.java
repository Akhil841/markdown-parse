// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkdownParse {

    static int findCloseParen(String markdown, int openParen) {
        int closeParen = openParen + 1;
        int openParenCount = 1;
        while (openParenCount > 0 && closeParen < markdown.length()) {
            if (markdown.charAt(closeParen) == '(') {
                openParenCount++;
            } else if (markdown.charAt(closeParen) == ')') {
                openParenCount--;
            }
            closeParen++;
        }
        if(openParenCount == 0) {
          return closeParen - 1;
        }
        else {
          return -1;
        }

    }
    public static Map<String, List<String>> getLinks(File directory) throws IOException {
        Map<String, List<String>> result = new HashMap<>();
        if(directory.isDirectory()) {
            for(File f: directory.listFiles()) {
                result.putAll(getLinks(f));
            }
            return result;
        }
        else {
            Path p = directory.toPath();
            int lastDot = p.toString().lastIndexOf(".");
            if(lastDot == -1 || !p.toString().substring(lastDot).equals(".md")) {
                return result;
            }
            ArrayList<String> links = getLinks(Files.readString(p));
            result.put(directory.getPath(), links);
            return result;
        }
    }
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
	/* I ADDED THIS COMMENT IN VIM! */
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
        	boolean image = false;
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
<<<<<<< HEAD
            if (nextOpenBracket == -1) break;
            if (nextOpenBracket != 0 && markdown.toCharArray()[nextOpenBracket-1] == '!') image = true;
=======
            int nextCodeBlock = markdown.indexOf("\n```");
            if(nextCodeBlock < nextOpenBracket && nextCodeBlock != -1) {
                int endOfCodeBlock = markdown.indexOf("\n```");
                currentIndex = endOfCodeBlock + 1;
                continue;
            }
>>>>>>> upstream/main
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            if (nextCloseBracket == -1) break;
            int openParen = markdown.indexOf("(", nextCloseBracket);
<<<<<<< HEAD
            if (openParen == -1) break;
            int closeParen = markdown.indexOf(")", openParen);
            if (closeParen == -1) break;
            String check = markdown.substring(openParen + 1, closeParen);
        	if (check.contains("(")) {
        		openParen = markdown.indexOf("(", openParen + 1);
        	}
            String outString = markdown.substring(openParen + 1, closeParen);
            if (!image && !outString.isEmpty()) toReturn.add(outString);
            currentIndex = closeParen + 1;
=======

            // The close paren we need may not be the next one in the file
            int closeParen = findCloseParen(markdown, openParen);
            
            if(nextOpenBracket == -1 || nextCloseBracket == -1
                  || closeParen == -1 || openParen == -1) {
                return toReturn;
            }
            String potentialLink = markdown.substring(openParen + 1, closeParen).trim();
            if(potentialLink.indexOf(" ") == -1 && potentialLink.indexOf("\n") == -1) {
                toReturn.add(potentialLink);
                currentIndex = closeParen + 1;
            }
            else {
                currentIndex = currentIndex + 1;
            }
>>>>>>> upstream/main
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
