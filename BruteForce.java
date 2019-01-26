import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BruteForce {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Encryption enc = new Encryption(new File("/Users/evan/Desktop/enc.txt"));
		System.out.println(enc.bruteForce());
		
	}
}

class Encryption {
	
	public static String encrypted = "";
	public static String decrypted = "";
	public static ArrayList<Dict> di = new ArrayList<Dict>();
	public static BufferedReader br;
	public static File dict = new File("/Users/evan/Downloads/en-US.dic");
	public static File file;
	public static String key = "!!";
	
	public Encryption(File file) throws IOException {
		String st = "";
		this.file = file;
		
		br = new BufferedReader(new FileReader(dict));
		while((st = br.readLine()) != null)
			di.add(new Dict(st));
		
		br = new BufferedReader(new FileReader(file));
		while ((st = br.readLine()) != null)
			encrypted += st;
	}
	public Encryption(String key, File file) throws IOException {
		String st = "";
		this.key = key;
		this.file = file;
		
		br = new BufferedReader(new FileReader(dict));
		while((st = br.readLine()) != null)
			di.add(new Dict(st));
		
		br = new BufferedReader(new FileReader(file));
		while ((st = br.readLine()) != null)
			encrypted += st;
	}
	
	public static String bruteForce() throws InterruptedException {
		char[] enc_arr = encrypted.toCharArray();
		char[] key_arr = key.toCharArray();
		
		for(int i = 0; i < 90; i++) {
			key_arr[1] = '!';
			for(int j = 0; j < 90; j++) {
				decrypted = "";
				for(int k = 0; k < encrypted.length(); k++) {
					decrypted += (char)(enc_arr[k] ^ key_arr[k % key_arr.length]);
				}
				//System.out.println(decrypted);
				
				String[] words = decrypted.split(" ");
				
				int low = 0;
				int high = di.size()-1;
				int mid = (high + low) / 2;
				int letter = 0;
				int word = 0;
				int words_found = 0;
				String[] found = new String[10];
				while(word < words.length) {
					try {
						System.out.printf("Words: %d, %s \n", words_found, Arrays.toString(found));
						System.out.printf("Low: %d\nMid: %d\nHigh: %d\n", low, mid, high);
						System.out.printf("Key: %s\n", Arrays.toString(key_arr));
						if(letter >= words[word].length() && words[word].length() != 0) {
							found[words_found] = words[word];
							words_found++;
							word++;
							letter = 0;
						} else if(letter >= di.get(mid).word.length()) {
							word++;
							letter = 0;
						}
						if(words_found == 5) {
							return decrypted;
						}
						if(words[word].length() > 25 || (word > 30 && words_found == 0)) {
							break;
						}
						else if((int)di.get(mid).letters[letter] > (int)words[word].toLowerCase().toCharArray()[letter]) {
							System.out.println(di.get(mid).word + " > " + words[word]);
							high = mid;
							mid = (high + low) / 2;
							letter = 0;
						}
						else if((int)di.get(mid).letters[letter] < (int)words[word].toLowerCase().toCharArray()[letter]) {
							System.out.println(di.get(mid).word + " < " + words[word]);
							low = mid;
							mid = (high + low) / 2;
							letter = 0;
						}
						else {
							letter++;
						}
						if(low >= high-4) {
							word++;
							low = 0;
							high = di.size()-1;
							mid = (high + low) / 2;
						}
					} catch(Exception e) {
						word++;
						letter = 0;
					}
				}
				key_arr[1]++;
			}
			key_arr[0]++;
		}
		return "no key found";
	}
}

class Dict {
	
	public String word;
	public char[] letters;
	
	public Dict() {}
	public Dict(String word) {
		this.word = word.toLowerCase();
		letters = word.toLowerCase().toCharArray();
	}
	
}
