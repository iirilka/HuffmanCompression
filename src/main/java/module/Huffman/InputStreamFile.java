package module.Huffman;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;

public final class InputStreamFile {
    private static final int EOF = -1;
    private BufferedInputStream bufferedInputStream;
    private int buffer;
    private int numberOfBits;


    public InputStreamFile(String name) {

        try {
            File file = new File(name);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                fillBuffer();
                return;
            }

        } catch (IOException ioe) {
            System.err.println("Невозможно открыть " + name);
        }
    }

    private void fillBuffer() {
        try {
            buffer = bufferedInputStream.read();
            numberOfBits = 8;
        } catch (IOException e) {
            System.err.println("EOF");
            buffer = EOF;
            numberOfBits = -1;
        }
    }

    public boolean isEmpty() {
        return buffer == EOF;
    }


    public char readChar() {
        if (isEmpty()) throw new NoSuchElementException("Чтение из пустого входящего потока!");

        if (numberOfBits == 8) {
            int x = buffer;
            fillBuffer();
            return (char) (x & 0xff);
        }
        int x = buffer;
        x <<= (8 - numberOfBits);
        int oldN = numberOfBits;
        fillBuffer();
        if (isEmpty()) throw new NoSuchElementException("Чтение из пустого входящего потока!");
        numberOfBits = oldN;
        x |= (buffer >>> numberOfBits);
        return (char) (x & 0xff);
    }


    public boolean readBoolean() {
        if (isEmpty()) throw new NoSuchElementException("Чтение из пустого входящего потока!");
        numberOfBits--;
        boolean bit = ((buffer >> numberOfBits) & 1) == 1;
        if (numberOfBits == 0) fillBuffer();
        return bit;
    }

    public String readString() {
        if (isEmpty()) throw new NoSuchElementException("Чтение из пустого входящего потока!");

        StringBuilder sb = new StringBuilder();
        while (!isEmpty()) {
            char c = readChar();
            sb.append(c);
        }
        return sb.toString();
    }


}