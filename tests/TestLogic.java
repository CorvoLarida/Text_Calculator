package tests;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TestLogic {

    public static void main(String[] args) {
        List<String> cList = new ArrayList<>();
//        cList.add("(");
        cList.add("62");
        cList.add("*");
        cList.add("8.5");
//        cList.add(")");
//        cList.add("+");
//        cList.add("(");
//        cList.add("2");
//        cList.add("/");
//        cList.add("8");
//        cList.add(")");

        System.out.println("cList Original: " + cList);
        Logic.calculate(cList);
        System.out.println("cList: " + cList);
        }
    }

class Logic{
    private static Block b;
    static void calculate(List<String> list){
        list.add(0,"(");
        list.add(list.size(),")" );
        List<Integer> leftBracket = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("(")) {
                leftBracket.add(i);
            }
        }
        List<Integer> rightBracket = new ArrayList<>();
        List<Integer> pair = new ArrayList<>();

        for (int l = leftBracket.size()-1; l >= 0; l--) {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(")")) {
                    rightBracket.add(i);
                }
            }

            Integer left = leftBracket.get(l);
            Integer right = 0;
            Optional<Integer> optionalright = rightBracket.stream().filter(num -> num > left).min(Integer::compareTo);
            if (optionalright.isPresent()) right = optionalright.get();
            else System.out.println("error");

            pair.add(left);
            pair.add(right);
            System.out.println(pair);

            b = new Block(list, pair.get(0), pair.get(1));
            b.calculateBlock();
            System.out.println(l+ " Input List After Block: " + list);

            System.out.println(list);

            leftBracket.remove(left);
            rightBracket.clear();
            pair.clear();
        }
    }

    static boolean isASymbol(String sm, String[] elements){
        for (String element : elements) {
            if (sm.equals(element)) return true;
        }
        return false;
    }

    // +
    static BigDecimal sum(BigDecimal firstNumber, BigDecimal secondNumber) {
        return firstNumber.add(secondNumber);
    }
    // -
    static BigDecimal subtract(BigDecimal firstNumber, BigDecimal secondNumber) {
        return firstNumber.subtract(secondNumber);
    }
    // *
    static BigDecimal multiply(BigDecimal firstNumber, BigDecimal secondNumber) {
        return firstNumber.multiply(secondNumber);
    }
    // /
    static BigDecimal divide(BigDecimal firstNumber, BigDecimal secondNumber){
        return BigDecimal.valueOf(firstNumber.doubleValue()/ secondNumber.doubleValue());
    }
    // log
    static BigDecimal logarithm(BigDecimal firstNumber){
        return BigDecimal.valueOf(Math.log(firstNumber.doubleValue()));
    }
    // ^
    static BigDecimal exponentiation(BigDecimal firstNumber, BigDecimal secondNumber){
        return BigDecimal.valueOf(Math.pow(firstNumber.doubleValue(),secondNumber.doubleValue()));
    }
}



class Block{
    private int startOfBlock;
    private int endOfBlock;
    private List<String> listToCopyFrom;
    private List<String> block = new ArrayList<>();
    private static final String[] mathSymbols = Calculator.symbols;
    private static final String[] twoNumberSymbols = {"^","/","*","-", "+"};

    private static BigDecimal n1 = new BigDecimal(0);
    private static BigDecimal n2 = new BigDecimal(0);
    private static BigDecimal n3 = new BigDecimal(0);
    private static String mathSymbol;
    Block(List<String> list, int start, int end){
        listToCopyFrom = list;
        startOfBlock = start;
        endOfBlock = end;
    }

    void calculateBlock() {
        block = new ArrayList<>();
        for (int i = startOfBlock; i <= endOfBlock; i++){
            block.add(listToCopyFrom.get(i));
        }
        System.out.println("Test Block: " + block);

        List<BigDecimal> blockOfNumbers;
        List<String> blockOfSymbols;
        Converter.convertToNumbersAndSymbols(block);
        blockOfNumbers = Converter.getListNumbers();
        blockOfSymbols = Converter.getListSymbols();

        blockOfSymbols.remove("(");
        blockOfSymbols.remove(")");

        for (int s = 0; s < mathSymbols.length; s++){
            calculateBySign(blockOfNumbers, blockOfSymbols, mathSymbols[s]);
        }

        for (int i = 0; i < block.size();i++){
            listToCopyFrom.remove(startOfBlock);
        }

        block.clear();
        block.add(blockOfNumbers.get(0).toString());
        blockOfNumbers.clear();
        blockOfSymbols.clear();
        listToCopyFrom.add(startOfBlock,block.get(0));
        block.clear();
    }
    private void calculateBySign(List<BigDecimal> numbers, List<String> symbols, String smbl){
        while(symbols.contains(smbl)){
            int i = symbols.indexOf(smbl);
            mathSymbol = symbols.get(i);
            n1 = numbers.get(i);
            if (Logic.isASymbol(mathSymbol,twoNumberSymbols)) {
                n2 = numbers.get(i+1);
            }
            calculateLocal();
            symbols.remove(i);
            numbers.remove(i);
            if(Logic.isASymbol(mathSymbol,twoNumberSymbols)) {
                numbers.remove(i);
            }
            numbers.add(i, n3);
            System.out.println("Numbers After: " + numbers);
            System.out.println("Symbols After: " + symbols);
        }
    }
    private void calculateLocal(){
        String output = "";
        switch (mathSymbol){
            case "+":
                n3 = Logic.sum(n1,n2);
                output = n1 + " + " + n2 + " = " + n3;
                break;
            case "-":
                n3 = Logic.subtract(n1,n2);
                output = n1 + " - " + n2 + " = " + n3;
                break;
            case "*":
                n3 = Logic.multiply(n1,n2);
                output = n1 + " * " + n2 + " = " + n3;
                break;
            case "/":
                n3 = Logic.divide(n1,n2);
                output = n1 + " / " + n2 + " = " + n3;
                break;
            case "^":
                n3 = Logic.exponentiation(n1,n2);
                output = n1 +" ^ " + n2 + " = " + n3;
                break;
            case "log":
                n3 = Logic.logarithm(n1);
                output = "log " + n1 + " = " + n3;
                break;
        }
        System.out.println("Output: " + output);
    }

}