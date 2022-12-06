package tests;

import java.util.*;

public class TestCalculator {
    public static void main(String[] args) {
    }
}

class Calculator {
    static final String[] symbols = {"(",")","log","^","/","*","-", "+"};
    private List<String> inputList = new ArrayList<>();
    static List<String> results = new ArrayList<>();
    String inputGui;
    private String inputSys;
    private String output, result;
    void go() {
        try {
            result = "";
            getInput();
            useConverter();
            System.out.println("Calc Input List: " + inputList);
            count();
            System.out.println("Calc Input List: " + inputList);
            printOutput();
            cleanUp();
        } catch (NumberFormatException exNum) {
            if (inputSys.equals("results")) {}
            else {
                Gui.output.setText("Ошибка при введении числа. Попробуйте снова.");
                System.out.println("Ошибка при введении числа. Попробуйте снова.");
            }
        } catch (IndexOutOfBoundsException ex) {
            if (inputSys.equals("results")) {}
            else {
                Gui.output.setText("Ошибка при введении числа. Попробуйте снова.");
                System.out.println("Ошибка. Попробуйте снова.");
            }
        }
    }
    private void cleanUp(){
        inputList.clear();
        inputSys = "";
        inputGui = "";
        output = "";
        result = "";
    }
    void getInput(){
        System.out.println("Введите выражение: ");
        inputSys = inputGui;
        System.out.println("input " + inputSys);
        checkForKeywords();
        result+=inputSys;
    }
    private void checkForKeywords(){
        if (inputSys.equals("exit")) {
            Gui.output.setText("Выход из программы.");
            System.out.println("Выход из программы.");
            System.exit(155);
        }
        if (inputSys.equals("results")) {
            Gui.output.setText("");
            Gui.output.append("История:" + "\n");
            for (int i = 0; i < results.size(); i++) {
                Gui.output.append((i + 1) + ") " + results.get(i)+"\n");
                System.out.println((i + 1) + ") " + results.get(i));
            }
        }
    }
    void useConverter(){
        Converter.convertString(inputSys);
        inputList = Converter.getListString();
    }
    private void count(){
        Logic.calculate(inputList);
    }
    void printOutput(){
        output = inputList.get(0);
        result+=" = " + output;
        System.out.println("= " + output);
        Gui.output.setText("= " + output);
        results.add(result);
        Gui.history.append((results.size()) + ") " + result + "\n");
    }
}



















