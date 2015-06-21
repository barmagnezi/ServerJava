package algorithms.compression;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.input.ReaderInputStream;

/**
* The HuffmanReader class extends Reader and contain Reader(Decorator Pattern)
* The class can read from text that compress with Huffman writer.
*
* @author  Bar Magnezi
* @version 1.0
* @since 3.5.2015
*/
public class HuffmanReader extends Reader {
	private Reader ziptext;
	private HuffmanAlg decompress;
	/**
	 * This constructor creates HuffmanReader that read from the Reader that send as an parameter.
	 * This constructor has a bug with some of the strings
	 * @param in The Reader that we want to read.
	 */
	public HuffmanReader(Reader in) throws IOException, ClassNotFoundException {
		super();
		System.out.println("HuffmanReader constructor:This constructor has a bug with some of strings,"
				+ "Better to use with the constructor that get inputstream");
		decompress=new HuffmanAlg();
		decompress.decompress(new ReaderInputStream(in),new FileOutputStream("buffer_hufman_reader(dontDeleteWhileUsingTheHuffmanReader)")).close();
		ziptext=new InputStreamReader(new FileInputStream("buffer_reader(dontDeleteWhileUsingTheHuffmanReader)"));
	}
	/**
	 * This This constructor creates HuffmanReader that read from the InputStream that send as an parameter.
	 * @param in The InputStream that we want to read.
	 */
	public HuffmanReader(InputStream in) throws IOException, ClassNotFoundException {
		super();
		decompress=new HuffmanAlg();
		decompress.decompress(in,new FileOutputStream("buffer_reader(dontDeleteWhileUsingTheHuffmanReader)"));
		ziptext=new InputStreamReader(new FileInputStream("buffer_reader(dontDeleteWhileUsingTheHuffmanReader)"));
	}
	
	/**
	 * This method Override Reader.
	 * The method read from the text according parameters
	 * @param cbuf destination buffer
	 * @param off Offset at which to start storing the characters. 
	 * @param len The length that read.
	 * @return The number of character read,or -1 if the end of the stream has been reached.
	 */
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		int x=ziptext.read(cbuf, off, len);
		return x;

	}
	/**
	 * This method Override Reader.
	 * The method close the stream that used to read from the text.
	 */
	@Override
	public void close() throws IOException {
		ziptext.close();
	}

}
