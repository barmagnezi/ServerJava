package algorithms.compression;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



/**
* The WordDictionary class compress and decompress by using WordDictionary
*
* @author  Bar Magnezi
* @version 1.0
* @since 27.4.2015
* 
* 
*/
public class WordDictionary extends  CommonCompresses{

	/**
	 * This methods compress the text(that come from any InputStream) to WordDictionary
	 * @param text The stream that we want to compress
	 * @return InputStream The InputStream will be FileInputStream that redirect to WordDictionary.txt file in the folder texts
	 */
	@Override
	public OutputStream compress(InputStream text){
		try {
			return compress(text,new FileOutputStream("texts/WordDictionary"));
		} catch (FileNotFoundException e) {
			System.out.println("Can't create the automatic file(texts/WordDictionary)");
			return null;
		}
	}
	/**
	 * This methods compress the text(that come from any InputStream) to WordDictionary
	 * @param text The stream that we want to compress
	 * @param path The path will be the location of WordDictionary that will create
	 * @return InputStream The InputStream will be FileInputStream that redirect to path.txt file
	 */
	public OutputStream compress(InputStream text,OutputStream out) {
		try {
			/*with ByteArrayOutputStream
			 * byte[] cbuf = null;
			//reader.read(cbuf);
			//String input =String.valueOf(cbuf);
			 * */
			 /*with ObjectInputStream 
			String input = (String) reader.readObject();
			String[] sp = input.split(" ");
			*/
			//with scanner
			Scanner  s=new Scanner(new InputStreamReader(text));
			HashMap<String, ArrayList<Short>> dictionary=new HashMap<String, ArrayList<Short>>();
			for (int i = 0; s.hasNext(); i++)
			{
				String word = s.next();
				ArrayList<Short> indices;
				
				if (dictionary.containsKey(word))
					indices = dictionary.get(word);
				else
					indices = new ArrayList<Short>();
				
				indices.add((short) i);
				dictionary.put(word, indices);
			}
			s.close();
			/*with ByteArrayOutputStream
			//we create ByteArrayOutputStream to save in it
			//we create ObjectOutputStream to write object to  ByteArrayOutputStream
			ByteArrayOutputStream OutBytearray=new ByteArrayOutputStream();
			ObjectOutputStream object_out=new ObjectOutputStream(OutBytearray);
			object_out.writeObject(dictionary);
			byte[] arr=OutBytearray.toByteArray();
			ByteArrayInputStream InBytearray=new ByteArrayInputStream(arr);
			ObjectInputStream object_reader=new ObjectInputStream(InBytearray);
			return  (InputStream) object_reader;
			*/
			ObjectOutputStream object_out=new ObjectOutputStream(out);			
			object_out.writeObject(dictionary);
			object_out.close();

			return out;
			
		} catch (IOException e1) {
			System.out.println("The scanner cant read from this input");
			return null;
		} finally{
			try {
				text.close();
			} catch (IOException e) {
				System.out.println("The zipText Can not be close");
				return null;
			}
		}
		
	}

	/**
	 * This methods decompress the WordDictionary to text
	 * @param zipText The WordDictionary that we want to decompress(the stream need be point to WordDictionary)and the object must be create by WordDictionary compress
	 * @return InputStream The InputStream will be FileInputStream that redirect to WordDictionary_decompress.txt file in the folder texts
	 */
	@Override
	public  OutputStream decompress(InputStream zipText) {
		try {
			return decompress(zipText,new FileOutputStream("texts/WordDictionary_decompress"));
		} catch (FileNotFoundException e) {
			System.out.println("Can't create the automatic file(huffmanzip_decompress.txt)");
			return null;
		}
	}

	/**
	 * This methods decompress the WordDictionary to text
	 * @param zipText The WordDictionary that we want to decompress(the stream need be point to WordDictionary)and the object must be create by WordDictionary compress
	 * @param path The path will be the location of text that will create
	 * @return InputStream The InputStream will be FileInputStream that redirect to path.txt file
	 */
	public OutputStream decompress(InputStream zipText,OutputStream out){
		try {
			HashMap<Short,String> helpHashMap=new HashMap<Short,String>();
			ObjectInputStream OZipText=new ObjectInputStream(zipText);
			HashMap<String, ArrayList<Short>> dictionary=((HashMap<String, ArrayList<Short>>) OZipText.readObject());
			for(String word:dictionary.keySet()){
				for(Short index:dictionary.get(word)){
					helpHashMap.put(index, word);
				}
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
			Short i=0;
			while(helpHashMap.isEmpty()==false){
				writer.write(helpHashMap.remove(i)+" ");
				i++;
			}
			writer.close();
			return out;
			
			
		} catch (ClassNotFoundException e1){
			System.out.println("The text did not compress by WordDictionary");
			return null;
		} catch(IOException e){
			System.out.println("The zipText Can not be read");
			return null;
		}finally{
			try {
				zipText.close();
			} catch (IOException e) {
				System.out.println("The zipText Can not be close");
				return null;
			}
		}

	}

}
