package textproc;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class WordCountComparator implements Comparator<Entry<String,Integer>> {

	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		int n = o2.getValue() - o1.getValue();
		if (n == 0) {
			n = o1.getKey().compareToIgnoreCase(o2.getKey());
		}
		return n;
	}

}
