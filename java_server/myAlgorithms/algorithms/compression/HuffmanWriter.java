package algorithms.compression;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.WriterOutputStream;

/**
* The HuffmanWriter class extends Writer and contain Writer(Decorator Pattern)
* The class compress the text that will write with Huffman algorithm
*
* @author  Bar Magnezi
* @version 1.0
* @since 3.5.2015
*/
public class HuffmanWriter extends Writer {

	private Writer Out; //Usable when i use the HuffmanWriter(OutputStream out) constructor
	private OutputStream out2; //Usable when i use the HuffmanWriter(Writer out) constructor
	private HuffmanAlg compress;
	private String buffer;
	/**
	 * This This constructor creates HuffmanWriter that write to Writer that send as an parameter.
	 * This constructor has a bug with some of the strings
	 * @param in The Writer that we want to write write inside.
	 */
	public HuffmanWriter(Writer out) {
		super();
		System.out.println("HuffmanWriter constructor:This constructor has a bug with some of strings,"
				+ "Better to use with the constructor that get OutputStream it works perfect");
		Out=out;
		compress=new HuffmanAlg();
		buffer="";
	}
	/**
	 * This This constructor creates HuffmanWriter that write to OutputStream that send as an parameter.
	 * @param in The OutputStream that we want to write write inside.
	 */
	public HuffmanWriter(OutputStream out) {
		super();
		out2=out;
		compress=new HuffmanAlg();
		buffer="";
	}
	/**
	 * This method Override Reader.
	 * The method read from the text according parameters

	 * @param cbuf Array of characters
	 * @param off Offset from which to start writing characters
	 * @param Number of characters to write
	 */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		buffer+=String.copyValueOf(cbuf, off, len);
	}
	/**
	 * This method Override Writer.
	 * The method write compresses all the text that write by using Huffman algorithm and write it to writer that chose in the constructor. 
	 */
	@Override
	public void flush() throws IOException {
		OutputStream z;
		if(Out!=null){ //error!!
			z=new WriterOutputStream(Out); 
			//z=new ObjectOutputStream(new FileOutputStream("bar.txt")); //the hard way
		}
		else
			z=out2;
		compress.compress(new ByteArrayInputStream(buffer.getBytes()),z);
		z.close();
		/*
		 * The first idea if i use buffer file to write the compresses text 
		
		if(Out!=null){ //error!!
			z=new ObjectOutputStream(new WriterOutputStream(Out,"ISO-8859-1")); 
			//z=new ObjectOutputStream(new FileOutputStream("bar.txt")); //the hard way
		}
		else//when i get output stream
			z=new ObjectOutputStream(out2);
		try {
			z.writeObject(x.readObject());
			z.writeObject(x.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		/*
		//the hard way continue
		if(Out!=null){
			char[]cbuf=new char[10];
			Reader r=new InputStreamReader(new FileInputStream("bar.txt"));
			while(r.read(cbuf)!=-1){
				Out.write(cbuf);
			}
			Out.close();
			ObjectInputStream s=new ObjectInputStream(new FileInputStream("abc.txt"));
			try {
				s.readObject();
				System.out.println("aacompress"+s.readObject());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		*/
		//end hard way
		/*
		//the hard way continue 2
		//לשאול מרצה!!!!
		//כאשר אני קורא מבר את האובייקט השני אני מקבל משהו אחד וכאשר אחרי העתקה אני קורא את זה מהקובץ אבג אני מקבל משהו אחר
		if(Out!=null){
		ObjectInputStream s=new ObjectInputStream(new FileInputStream("bar.txt"));
		try {
			s.readObject();
			System.out.println("1)"+s.readObject());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		Writer d=new FileWriter("abc.txt");
		IOUtils.copy(new FileInputStream("bar.txt"), d,"ISO-8859-1");
		System.out.println("Dvdv");
		d.close();		//Out.close();
		System.out.println("Dvdv");
		
		System.out.println("Dvdv");
		try {
			s=new ObjectInputStream(new FileInputStream("abc.txt"));
			System.out.println("DSv");
			s.readObject();
			System.out.println("2)"+s.readObject());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}
		//*/
		
	}

	/**
	 * This method Override Writer.
	 * The method close the stream that used to write from the text and run the function flush
	 */
	@Override
	public void close() throws IOException {
		this.flush();
		if (Out!=null)
			Out.close();
		else
			out2.close();
	}

}
