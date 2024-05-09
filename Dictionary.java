import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary {
    private Map<String, String> words;

    public Dictionary() {
        words = new HashMap<>();
    }

    public void loadDictionary(String file) throws IOException {
        // Load the dictionary from the file
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            words.put(line.trim().toLowerCase(), line.trim());
        }
        reader.close();
    }

    public boolean isWord(String word) {
        return words.containsKey(word.toLowerCase());
    }

    public void addWord(String word) {
        words.put(word.toLowerCase(), word);
    }

    public Set<String> getWords() {
        return words.keySet();
    }
}