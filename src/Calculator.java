package src;

import java.util.ArrayList;
import java.util.List;

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
            count();
            printOutput();
            cleanUp();
        } catch (NumberFormatException exNum) {
            if (inputSys.equals("results")) {}
            else {
                Gui.output.setText("Ошибка при введении числа. Попробуйте снова.");
            }
        } catch (IndexOutOfBoundsException ex) {
            if (inputSys.equals("results")) {}
            else {
                Gui.output.setText("Ошибка. Попробуйте снова.");
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
        inputSys = inputGui;
        result+=inputSys;
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
        Gui.output.setText("= " + output);
        results.add(result);
        Gui.history.append((results.size()) + ") " + result + "\n");
    }
}



















