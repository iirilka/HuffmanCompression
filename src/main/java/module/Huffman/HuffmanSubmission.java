package module.Huffman;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanSubmission implements Huffman {
    private static Node root = null;
    private static String decodeStrings;
    private static Map<Character, Integer> frequencyOfLetter = new HashMap<>();
    private static Map<Character, String> binaryCodes = new HashMap<>();
    private static StringBuilder stringBuilder = new StringBuilder();
    private static PriorityQueue<Node> priorityQueue;

    public static void storeInMapR(Node root, String str, Map<Character, String> codes) {
        if (root == null) {
            return;
        }

        if (root.getLeftChild() == null && root.getRightChild() == null) {
            codes.put(root.getLetter(), str);
        } else {
            storeInMapR(root.getLeftChild(), str + "0", codes);
            storeInMapR(root.getRightChild(), str + "1", codes);
        }
    }


    public static void createFrequencyMap(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!frequencyOfLetter.containsKey((text.charAt(i)))) {
                frequencyOfLetter.put(text.charAt(i), 0);
            }
            frequencyOfLetter.put(text.charAt(i), frequencyOfLetter.get(text.charAt(i)) + 1);
        }
    }

    public static void createTree() {
        priorityQueue = new PriorityQueue<>((Node l, Node r) -> l.getFrequency() - r.getFrequency());
        for (Map.Entry<Character, Integer> entry : frequencyOfLetter.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() != 1) {
            Node left = priorityQueue.poll();
            Node right = priorityQueue.poll();

            int sum = left.getFrequency() + right.getFrequency();
            priorityQueue.add(new Node('\0', sum, left, right));
        }

        root = priorityQueue.peek();

        storeInMapR(root, "", binaryCodes);
    }

    private static int compare(Node leftChild, Node rightChild) {
        return leftChild.getFrequency() - rightChild.getFrequency();
    }

    public static StringBuilder BinaryString(String text) {
        for (int i = 0; i < text.length(); i++) {
            stringBuilder.append(binaryCodes.get(text.charAt(i)));
        }

        return stringBuilder;
    }

    public static int decodeR(Node root, int index, String input) {
        if (root == null) {
            return index;
        }

        if (root.getLeftChild() == null && root.getRightChild() == null) {
            decodeStrings += root.getLetter();
            return index;
        }
        index++;
        if (input.length() > index) {
            if (input.charAt(index) == '0') {
                index = decodeR(root.getLeftChild(), index, input);
            } else {
                index = decodeR(root.getRightChild(), index, input);
            }
        }
        return index;
    }

    public static void decodeString(String encodedText) {
        int index = -1;
        while (index < encodedText.length() - 6) {
            index = decodeR(root, index, encodedText);
        }
    }

    public void encode(String inputFile, String outputFile, String frequencyFile) {
        String text = "";
        InputStreamFile inputStreamFile = new InputStreamFile(inputFile);
        try {
            while (true)
                text += inputStreamFile.readChar();
        } catch (Exception e) {
        }

        createFrequencyMap(text);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(frequencyFile));
            for (Character key : frequencyOfLetter.keySet()) {
                String temp = Integer.toBinaryString(key);
                while (temp.length() != 8) {
                    temp = "0" + temp;
                }
                writer.write(temp + ":" + frequencyOfLetter.get(key));
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
        }
        createTree();
        BinaryString(text);

        OutputStreamFile outputStreamFile2 = new OutputStreamFile(outputFile);
        for (String i : stringBuilder.toString().split("")) {
            if (i.equals("1")) {
                outputStreamFile2.write(true);
            } else {
                outputStreamFile2.write(false);
            }
        }

        outputStreamFile2.close();

    }

    public void decode(String inputFile, String outputFile, String frequencyFile) {
        String text = "";
        InputStreamFile inputStreamFile2 = new InputStreamFile(inputFile);
        try {
            while (true) {
                if (inputStreamFile2.readBoolean())
                    text += 1;
                else
                    text += 0;
            }
        } catch (Exception e) {
        }

        frequencyOfLetter.clear();
        InputStreamFile in = new InputStreamFile(frequencyFile);
        String temp = in.readString();
        String[] encoded = temp.split("\n");

        for (int i = 0; i < encoded.length; i++) {
            String line = encoded[i];

            if (line.length() > 0) {
                String[] arr = line.split(":");
                frequencyOfLetter.put((char) Integer.parseInt(arr[0], 2), Integer.parseInt(arr[1]));
            }
        }
        decodeStrings = "";
        createTree();
        decodeString(text);
        OutputStreamFile outputStreamFile2 = new OutputStreamFile(outputFile);
        outputStreamFile2.write(decodeStrings);
        outputStreamFile2.flush();
        outputStreamFile2.close();
    }


}
