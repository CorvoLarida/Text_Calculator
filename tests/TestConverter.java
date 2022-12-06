package tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class TestConverter{
    public static void main(String[] args) {
        String s = "12645532+";
        Converter.convert(s);
        System.out.println("String: " + Converter.getListString());
        System.out.println("Numbers: " + Converter.getListNumbers());
        System.out.println("Symbols: " + Converter.getListSymbols());
    }
}


class Converter {
    private static String string;
    private static final String[] symbols = Calculator.symbols;
    private static List<String> theList = new ArrayList<>();
    private static List<BigDecimal> numbers = new ArrayList<>();
    private static List<String> stringSymbols = new ArrayList<>();

    static void convert(String stringIn){
        convertString(stringIn);
        convertToNumbersAndSymbols(theList);
    }
    static void convertString(String stringIn) {
        theList.clear();
        string = stringIn;
        TreeMap<Integer, String> map = new TreeMap<>();

        for (String symbol : symbols) {
            List<Integer> indx = IntStream.iterate(string.indexOf(symbol), k -> k != -1, k -> string.indexOf(symbol, k + 1))
                    .boxed()
                    .toList();
            System.out.println("indx: " + indx);
            for (Integer integer : indx) {
                map.put(integer, symbol);
            }
        }
        System.out.println("Is Map empty: " + map.isEmpty());
        if (map.isEmpty()) {
            theList.add(string);
        }
        else {
            List<Integer> pos = new ArrayList<>(map.keySet());
            List<String> smbs = new ArrayList<>(map.values());

            theList.add(string.substring(0, pos.get(0)));
            theList.add(smbs.get(0));
            for (int i = 0; i < pos.size() - 1; i++) {
                theList.add(string.substring(pos.get(i) + smbs.get(i).length(), pos.get(i + 1)));
                theList.add(smbs.get(i + 1));
            }
            theList.add(string.substring(pos.get(pos.size() - 1) + smbs.get(smbs.size() - 1).length()));
            theList.removeIf(o -> o.equals(""));

            if (theList.get(0).equals("-")) theList.add(0, "0");
        }
    }
    static List<String> getListString(){
        return theList;
    }

    static void convertToNumbersAndSymbols(List<String> list){
        stringSymbols.clear();
        numbers.clear();
        for (int k = 0; k < list.size(); k++){
            convertElement(list.get(k));
        }
    }
    private static void convertElement(String element){
        if (Logic.isASymbol(element,symbols)) stringSymbols.add(element);
        else numbers.add(new BigDecimal(element));
    }
    static List<BigDecimal> getListNumbers(){
        return numbers;
    }
    static List<String> getListSymbols(){
        return stringSymbols;
    }
}
