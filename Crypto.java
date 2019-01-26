import java.io.*;

public class Crypto {

	public static String M = "";
	public static String key = "e+";
	public static String encrypted = "";
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("/Users/evan/Downloads/plaintext.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		  
		String st;
		while ((st = br.readLine()) != null)
			M += st;
		
		if(M.length() % 2 == 1)
			M += " ";
		
		char[] plaintext_arr = M.toCharArray();
		char[] key_arr = key.toCharArray();
		
		for(int i = 0; i < plaintext_arr.length; i++) {
			encrypted += (char)((int)plaintext_arr[i] ^ (int)key_arr[i % key_arr.length]);
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Users/evan/Desktop/enc.txt")));
		
		System.out.println(encrypted);
		
		bw.write(encrypted);
		bw.close();
		/*M = "";
		
		char[] encrypted_arr = encrypted.toCharArray();
		
		for(int i = 0; i < encrypted_arr.length; i++) {
			M += (char)(encrypted_arr[i] ^ key_arr[i % key_arr.length]);
		}
		
		System.out.println(M);*/
		
		
		

	}

}
