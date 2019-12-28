package module.Huffman;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Application  {
    public static void main(String[] args)throws FileNotFoundException, IOException {
        Huffman  huffman = new HuffmanSubmission();
        huffman.encode("message.txt", "message.hf", "frequency.txt");
        huffman.decode("message.hf", "message_new.txt", "frequency.txt");

    }
}
