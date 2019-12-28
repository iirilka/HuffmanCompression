package module.Huffman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class Application {
    private static Logger log = Logger.getLogger(Application.class.getName());
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Huffman huffman = new HuffmanSubmission();

        log.info("Шифрование");
        huffman.encode("message.txt", "message.hf", "frequency.txt");
        log.info("Расшифрование");
        huffman.decode("message.hf", "message_new.txt", "frequency.txt");

    }
}
