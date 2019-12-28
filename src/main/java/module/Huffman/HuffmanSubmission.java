package module.Huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanSubmission implements Huffman {
    static Node root = null;
    static String decodeStrings;
    static Map<Character, Integer> frequencyOfLetter = new HashMap<>();
    static Map<Character, String> binaryCodes = new HashMap<>();
    static StringBuilder stringBuilder = new StringBuilder();
    static PriorityQueue<Node> priorityQueue;

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

    public static void createATree() {
        priorityQueue = new PriorityQueue<>((Node l, Node r) -> l.getFrequency() - r.getFrequency());
        for (Map.Entry<Character, Integer> entry : frequencyOfLetter.entrySet()) {
            priorityQueue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (priorityQueue.size() != 1) {
            //Remove 2 nodes at a time.
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

    public void decode(String inputFile, String outputFile, String frequencyFile) {

    }

    public void encode(String inputFile, String outputFile, String frequencyFile) {

    }

}
