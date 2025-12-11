import java.io.*;
import java.util.*;

public class SortComparison {

    static int cardCompare(String card1, String card2) {
        Map<Character, Integer> suitRank = Map.of(
            'H', 1,
            'C', 2,
            'D', 3,
            'S', 4
        );

        char suit1 = card1.charAt(card1.length() - 1);
        char suit2 = card2.charAt(card2.length() - 1);

        int num1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
        int num2 = Integer.parseInt(card2.substring(0, card2.length() - 1));

        int s1 = suitRank.get(suit1);
        int s2 = suitRank.get(suit2);
        if (s1 != s2) return Integer.compare(s1, s2);

        return Integer.compare(num1, num2);
    }

    static ArrayList<String> bubbleSort(ArrayList<String> array) {
        ArrayList<String> arr = new ArrayList<>(array);
        int n = arr.size();
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (cardCompare(arr.get(i), arr.get(i + 1)) > 0) {
                    String temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);

        return arr;
    }

    static ArrayList<String> mergeSort(ArrayList<String> array) {
        if (array.size() <= 1) return array;

        int mid = array.size() / 2;
        ArrayList<String> left = new ArrayList<>(array.subList(0, mid));
        ArrayList<String> right = new ArrayList<>(array.subList(mid, array.size()));

        return merge(mergeSort(left), mergeSort(right));
    }

    static ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
        ArrayList<String> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (cardCompare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }

    static void sortComparison(String[] filenames) throws IOException {

        FileWriter fw = new FileWriter("sortComparison.csv");
        PrintWriter pw = new PrintWriter(fw);

        pw.print(",");
        for (String file : filenames) {
            int count = countLines(file);
            pw.print(" " + count + ",");
        }
        pw.println();

        pw.print("bubbleSort, ");
        for (String file : filenames) {
            ArrayList<String> cards = readCards(file);
            long start = System.currentTimeMillis();
            bubbleSort(cards);
            long end = System.currentTimeMillis();
            pw.print((end - start) + ", ");
        }
        pw.println();

        pw.print("mergeSort, ");
        for (String file : filenames) {
            ArrayList<String> cards = readCards(file);
            long start = System.currentTimeMillis();
            mergeSort(cards);
            long end = System.currentTimeMillis();
            pw.print((end - start) + ", ");
        }
        pw.println();

        pw.close();
    }

    static ArrayList<String> readCards(String filename) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));

        String line;
        while ((line = br.readLine()) != null) {
            if (!line.isBlank()) list.add(line.trim());
        }

        br.close();
        return list;
    }

    static int countLines(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int count = 0;
        while (br.readLine() != null) count++;
        br.close();
        return count;
    }

    public static void main(String[] args) throws IOException {
        sortComparison(new String[]{"sort10.txt", "sort100.txt", "sort10000.txt"});
        System.out.println("sortComparison.csv generated.");
    }
}
