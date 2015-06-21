package algorithms.compression;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;
/**
* The HuffmanAlg class extends CommonCompresses
* The class compress and decompress by Huffman algorithm.
*
* @author  Bar Magnezi
* @version 1.0
* @since 3.5.2015
*/
public class HuffmanAlg extends CommonCompresses {
	
	/**
	 * This methods compress the text(that come from any InputStream) to huffman zip
	 * @param text The stream that we want to compress
	 * @return OutputStream The OutputStream will be FileOutputStream that redirect to Huffmanzip.txt file in the folder texts
	 */
	@Override
	public OutputStream compress(InputStream text){
		try {
			return compress(text,new FileOutputStream("texts/Huffmanzip.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Can't create the automatic file(texts/Huffmanzip.txt)");
			return null;
		}
	}
	
	
	/**
	 * This methods compress the text(that come from any InputStream) to huffman zip
	 * @param text The stream that we want to compress
	 * @param out The path will be the location of huffman zip that will create
	 * @return OutputStream The OutputStream will be OutputStream that redirect to parameter.
	 */
	public OutputStream compress(InputStream text,OutputStream out) {
		
		//from InputStream to string
		Scanner  s=new Scanner(new InputStreamReader(text));
		String strText=new String("");
		while(s.hasNextLine())
			strText=strText+s.nextLine()+"‰";
		
		s.close();
		try {
			text.close();
		} catch (IOException e1) {
			System.out.println("Can't close the parameter(text)");
			e1.printStackTrace();
			return null;
		}
		
		// count appearances of characters
		HashMap<Character, Hchar> countAppearance=new HashMap<>();
		for(char c: strText.toCharArray()){
			Hchar hc=countAppearance.get(c);
			if(hc==null){
					hc=new Hchar();
					hc.character=""+c;
					hc.count=0;
					countAppearance.put(c, hc);
			}
			hc.count++;
		}
		//create PriorityQueue that order by the number of times the character appears
		PriorityQueue<Hchar> pq=new PriorityQueue<Hchar>(
				new Comparator<Hchar>() {
					@Override
					public int compare(Hchar o1, Hchar o2) {
						return o1.count-o2.count;
					}
				});
		pq.addAll(countAppearance.values());
		//create Huffman tree 
		while(pq.size()>1){
			Hchar hc0=pq.poll();
			Hchar hc1=pq.poll();
			Hchar hc2=new Hchar();
			hc2.count=hc0.count+hc1.count;
			hc2.character=hc0.character+hc1.character;
			hc2.left=hc0;
			hc2.right=hc1;
			pq.add(hc2);
		}
		Hchar top=pq.peek();
		DFSbinRep(top,"");

		try {
			//write the dictionary
			ObjectOutputStream output=new ObjectOutputStream(out);
			String stringDictionary="";		
			for(Hchar hc : countAppearance.values()){
				if(hc.character.equals(" ")){
					stringDictionary+="__"; 
				}
				else{
					stringDictionary+=hc.character;
				}
				stringDictionary+=" "+hc.binRep+" ";
			}
			stringDictionary+="TextStart";
			output.writeObject(stringDictionary);
			
			//write the compress text
			BitSet huffText = new BitSet();
			int i=0;
			for(char c:strText.toCharArray())
				i=addCharToBitset(huffText,countAppearance.get(c).binRep,i);
			huffText.set(i, true);  		//the last bit always true (like EOF)
			output.writeObject(huffText);
			output.close();
			out.flush();
			return out;
		} catch (FileNotFoundException e) {
			System.out.println("can't create the file in this outputstream");
			return null;
		}catch (IOException e) {
			System.out.println("can't create the file in this outputstream");
			return null;
		}

	}
	
	
	/**
	* This methods decompress the huffman zip to text
	 * @param zipText The huffman zip that we want to decompress(the stream need be point to huffman zip)and the object must be create by huffman zip compress
	 * @return OutputStream The OutputStream will be FileOutputStream that redirect to huffmanzip_decompress.txt file in the folder texts
	 */
	@Override
	public OutputStream decompress(InputStream zipText) {
		try {
			return decompress(zipText,new FileOutputStream("texts/huffmanzip_decompress.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Can't create the automatic file(huffmanzip_decompress.txt)");
			return null;
		}
	}
	
	/**
	* This methods decompress the huffman zip to text
	 * @param zipText The huffman zip that we want to decompress(the stream need be point to huffman zip)and the object must be create by huffman zip compress.
	 * @param path The path will be the location of the text that will decompress.
	 * @return InputStream The InputStream will be FileInputStream that redirect to path that send in the parameter.
	 */
	public OutputStream decompress(InputStream zipText,OutputStream out)  {


		ObjectInputStream objectInput;
		String InputDictionary = null;
		BitSet bitHuffmanText=null;
		try {
			 objectInput=new ObjectInputStream(zipText);
			 InputDictionary=(String) objectInput.readObject();
			 bitHuffmanText=(BitSet) objectInput.readObject();
			 
		} catch (FileNotFoundException e) {
			System.out.println("The InputStream not support this huffman decompress!");
			return null;
		} catch (IOException e) {
			System.out.println("The InputStream not support this huffman decompress!");
			return null;
		} catch (ClassNotFoundException e) {
			System.out.println("The InputStream not support this huffman decompress!");
			return null;
		}
		

		//create dictionary 
		HashMap<String, String> dictionary=new HashMap<String, String>();
		String[] o4=InputDictionary.split(" ");
		int i=0;
		while(o4[i].equals("TextStart")==false){
			String ch;
			String binRep;
			if(o4[i].equals("__"))
				ch=" ";
			else
				ch=o4[i];
			i++;
			binRep=o4[i];
			dictionary.put(binRep, ch);
			i++;
		}		
		PrintWriter writer=null;
		writer=new PrintWriter(new OutputStreamWriter(out));

		//start to read the text
		String text_decompress="";
		boolean flag=true;
		for(i=0;i<bitHuffmanText.length()-1;i++){ //the last bit always true (like EOF)
			flag=true;
			String newBinRep=""; //the bit of the new char
			while(flag && i<bitHuffmanText.length()-1){				
				if(bitHuffmanText.get(i)==true)
					newBinRep+="1";
				else
					newBinRep+="0";
				if(dictionary.containsKey(newBinRep)==true){
					if(dictionary.get(newBinRep).equals("‰")){
						writer.println(text_decompress);
						text_decompress="";	
					}
					else
						text_decompress+=dictionary.get(newBinRep);
					flag=false;
				}
				else
					i++;
				if(text_decompress.length()>100000){
						writer.write(text_decompress);
						text_decompress="";
						writer.flush();
					}
			}
		}
		writer.println(text_decompress);
		writer.close();
		try {
			out.flush();
		} catch (IOException e) {
			System.out.println("Can't flush to this outputstream");
		}
		return out;
	}
	
	
	
	//private
	private class Hchar{
		int count;
		String character;
		Hchar left=null,right=null;
		String binRep="";
		//BitSet bitRep=null;
		@Override
		public int hashCode(){return character.hashCode(); }
	}
	
	private void DFSbinRep(Hchar node,String bin) {
		node.binRep+=bin;
		if(node.left!=null){
		DFSbinRep(node.left, node.binRep+"0");
		}
		if(node.right!=null){
		DFSbinRep(node.right, node.binRep+"1");
		}
	}
	
	private int addCharToBitset(BitSet num,String ch,int index){
		for(int i=index;i<index+ch.length();i++){
			if (ch.charAt(i-index)=='0')
				num.set(i, false);
			else
				num.set(i, true);
		}
		return index+ch.length();
	}
	
	/*
	// update the field bitRep(bitset) in Hchar
	private void binRepToBitRep(Collection<Hchar> chars) {
		for(Hchar ch:chars){
			ch.bitRep=new BitSet(ch.binRep.length());
			for(int i=0;i<ch.binRep.length();i++){
				if(ch.binRep.charAt(i)=='0')
					ch.bitRep.set(i, false);
				else
					ch.bitRep.set(i,true);
			}
		}
	}*/
}

