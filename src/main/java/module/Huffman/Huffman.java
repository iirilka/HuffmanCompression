package module.Huffman;

public interface Huffman {

    public void decode(String inputFile, String outputFile, String frequencyFile);

    public void encode(String inputFile, String outputFile, String frequencyFile);
}
