package textproc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class GeneralWordCounter implements TextProcessor {
	private Map<String, Integer> wordCount;
	private Set<String> stopWords;

	public GeneralWordCounter(Set<String> stopWords) {
		wordCount = new HashMap<>();
		this.stopWords = stopWords;
	}

	public void process(String word) {
		for (String stopWord : stopWords) {		//checks if a stopword
			if (stopWord.equalsIgnoreCase(word)) {
				return;	
			}
		}

		if (!(wordCount.get(word) == null)) {				//checks if the word has appeared before
			int n = wordCount.get(word);
			n++;														//adds to pre-existing count
			wordCount.put(word, n);
		} else {														//new word, new count
			wordCount.put(word, 1);
		}
	}

	public void report() {
		//se labbhandledning D9. Map.Entry är ett interface och vi har objekt av typen Map.Entry<String, Integer>
		Set<Map.Entry<String, Integer>> wordSet = wordCount.entrySet();
		List<Map.Entry<String, Integer>> wordList = new ArrayList<>(wordSet);		//Här får arraylist direkt alla element från set
		wordList.sort(new WordCountComparator());
		for (int i = 0; i < 20; i++) {
			System.out.println(wordList.get(i));
		}
	}
	
	//Vad är skillnaden på Map.Entry och Entry?
	public List<Map.Entry<String, Integer>> getWordList() {
		return new ArrayList<>(wordCount.entrySet());
	}
}
