// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
        	boolean image = false;
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            if (nextOpenBracket == -1) break;
            if (nextOpenBracket != 0 && markdown.toCharArray()[nextOpenBracket-1] == '!') image = true;
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            if (nextCloseBracket == -1) break;
            int openParen = markdown.indexOf("(", nextCloseBracket);
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