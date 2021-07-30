package textproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class BookReaderApplication {
	public static final String[] REGIONS = { "blekinge", "bohuslän", "dalarna", "dalsland", "gotland", "gästrikland",
			"halland", "hälsingland", "härjedalen", "jämtland", "lappland", "medelpad", "närke", "skåne", "småland",
			"södermanland", "uppland", "värmland", "västerbotten", "västergötland", "västmanland", "ångermanland",
			"öland", "östergötland" };

	public static void main(String[] args) throws FileNotFoundException {
	
		Scanner scanStopWords = new Scanner(new File("undantagsord.txt"));
		scanStopWords.findWithinHorizon("\uFEFF", 1);
		scanStopWords.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		Set<String> stopWords = new HashSet<String>();
		while (scanStopWords.hasNext()) {
			stopWords.add(scanStopWords.next());
		}
		scanStopWords.close();
		
		//Skapar counter och räknar på alla ord.
		GeneralWordCounter generalWordCounter = new GeneralWordCounter(stopWords);
		Scanner scanWords = new Scanner(new File("nilsholg.txt"));
		scanWords.findWithinHorizon("\uFEFF", 1);
		scanWords.useDelimiter("(\\s|,|\\.|:|;|!|\\?|'|\\\")+"); // se handledning
		while (scanWords.hasNext() ) {
			String word = scanWords.next().toLowerCase();
			generalWordCounter.process(word);
		}
		scanWords.close();
		
		//Skapar controller. Måste ske efter att alla ord är räknade.
		BookReaderController controller = new BookReaderController(generalWordCounter); //casting
		//generalWordCounter.report();
	}

}
