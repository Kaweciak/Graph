package dsaa.lab10;

import java.util.Scanner;
import java.util.*;

public class Document implements IWithName{
    public String name;
    public SortedMap<String,Link> link;

    public Document(String name) {
        this.name=name.toLowerCase();
        link=new TreeMap<String,Link>();
    }

    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TreeMap<String,Link>();
        load(scan);
    }
    public void load(Scanner scan) {
        String line;
        String[] words;
        Link res;
        do
        {
            line = scan.nextLine();
            words = line.split(" ");
            for(String word : words)
            {
                if(word.length() >= 6 && word.substring(0, 5).equalsIgnoreCase("link="))
                {
                    String link1 = word.substring(5);
                    res = createLink(link1);
                    if(res != null)
                    {
                        link.put(res.ref, res);
                    }
                }
            }
        }
        while(!line.equals("eod"));
    }

    public static boolean isCorrectId(String id) {
        char[] letters = id.toCharArray();
        if(!((letters[0] >= 'a' && letters[0] <= 'z') || (letters[0] >= 'A' && letters[0] <= 'Z'))) return false;
        for(char letter : letters)
        {
            if(!((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z'))) return false;
        }
        return true;
    }

    public static boolean corretChar(char a)
    {
        return (a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z') || (a >= '0' && a <= '9') || a == '_';
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        char[] letters = link.toCharArray();
        int weight = 0, endIndex = 0;
        Link res;
        if(!((letters[0] >= 'a' && letters[0] <= 'z') || (letters[0] >= 'A' && letters[0] <= 'Z'))) return null;
        for(int i = 0; i < letters.length; i++)
        {
            if(letters[i] == '(' && letters[letters.length-1] == ')' && letters.length-i > 2)
            {
                for(int j = i+1; j < letters.length-1; j++)
                {
                    if(letters[j] < '0' || letters[j] > '9') return null;
                }
                weight = Integer.parseInt(link.substring(i+1, letters.length-1));
                endIndex = i;
                break;
            }
            if(!corretChar(letters[i])) return null;
        }
        if(weight == 0) res = new Link(link);
        else res = new Link(link.substring(0, endIndex), weight);
        return res;
    }

    @Override
    public String toString() {
        StringBuilder retStr= new StringBuilder("Document: " + name);
        int wordsInLine = 0;
        if(link.size() != 0) retStr.append("\n");
        Set set = link.entrySet();
        for (Object link1 : set) {
            if (wordsInLine == 10) {
                retStr.append("\n");
                wordsInLine = 0;
            } else if (wordsInLine != 0) {
                retStr.append(" ");
            }
            retStr.append(link1);
            wordsInLine++;
        }
        return retStr.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }
}
