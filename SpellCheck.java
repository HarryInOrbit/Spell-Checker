import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SpellCheck {
    private static Dictionary dictionary = new Dictionary();

    public static void main(String[] args) {
        try {
            dictionary.loadDictionary("dictionary.txt");
            Scanner scanner = new Scanner(System.in);
            boolean run = true;
            while (run) {
                System.out.println("Enter a command (check, add, exit):");
                String command = scanner.nextLine().trim();
                switch (command.toLowerCase()) {
                    case "check":
                        System.out.println("Enter a sentence to check:");
                        String sentence = scanner.nextLine();
                        checkSpelling(sentence);
                        break;
                    case "add":
                        System.out.println("Enter a word to add to the dictionary:");
                        String word = scanner.nextLine();
                        dictionary.addWord(word);
                        System.out.println("Word added to dictionary.");
                        break;
                    case "exit":
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    private static void checkSpelling(String text) {
        String[] words = text.split("\\s+");
        List<String> misspelledWords = new ArrayList<>();
        for (String word : words) {
            if (!dictionary.isWord(word)) {
                misspelledWords.add(word);
                System.out.println("Misspelled word: " + word);
            }
        }

        if (!misspelledWords.isEmpty()) {
            for (String misspelled : misspelledWords) {
                List<String> suggestions = getSuggestions(misspelled);
                System.out.println("Suggestions for " + misspelled + ": " + String.join(", ", suggestions));
            }
        }
    }

    private static List<String> getSuggestions(String misspelledWord) {
        return dictionary.getWords().stream()
                .filter(word -> LevenshteinDistance.calculateDistance(misspelledWord, word) <= 2)
                .sorted(Comparator.comparing(word -> LevenshteinDistance.calculateDistance(misspelledWord, word)))
                .limit(5)
                .collect(Collectors.toList());
    }
}