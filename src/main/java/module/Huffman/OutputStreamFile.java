package module.Huffman;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class OutputStreamFile {
    private BufferedOutputStream bufferedOutputStream;
    private int buffer;
    private int numberOfBits;

    public OutputStreamFile(String filename) {
        try {
            OutputStream outputStream = new FileOutputStream(filename);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBit(boolean x) {
        buffer <<= 1;
        if (x) buffer |= 1;
        numberOfBits++;
        if (numberOfBits == 8) clearBuffer();
    }

    private void writeByte(int x) {
        assert x >= 0 && x < 256;
        if (numberOfBits == 0) {
            try {
                bufferedOutputStream.write(x);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }


        for (int i = 0; i < 8; i++) {
            boolean bit = ((x >>> (8 - i - 1)) & 1) == 1;
            writeBit(bit);
        }
    }

    public void write(boolean x) {
        writeBit(x);
    }

    private void clearBuffer() {
        if (numberOfBits == 0) return;
        if (numberOfBits > 0) buffer <<= (8 - numberOfBits);
        try {
            bufferedOutputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        numberOfBits = 0;
        buffer = 0;
    }

    public void flush() {
        clearBuffer();
        try {
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close() {
        flush();
        try {
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(char x) {
        if (x < 0 || x >= 256) throw new IllegalArgumentException("Неверный 8-bit char = " + x);
        writeByte(x);
    }

    public void write(String s) {
        for (int i = 0; i < s.length(); i++)
            write(s.charAt(i));
    }

}
