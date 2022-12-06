package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Gui {
    private final Calculator calculator = new Calculator();
    private JFrame frame;
    private final Dimension std = new Dimension(500,340);
    private final Dimension ext = new Dimension(793,340);
    private JPanel panelHistory;
    private JTextArea userInput;
    static JTextArea output;
    static JTextArea history;

    void build(){
        frame = new JFrame("Текстовый Калькулятор");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(std);
        frame.setResizable(false);

        Font myFont = new Font("Trebuchet",Font.PLAIN,15);
        Dimension forRigidArea = new Dimension(10, 10);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
        mainPanel.add(Box.createRigidArea(forRigidArea));

        JPanel panelInNOut = new JPanel();
        panelInNOut.setLayout(new BoxLayout(panelInNOut,BoxLayout.Y_AXIS));
        panelInNOut.add(Box.createRigidArea(forRigidArea));

        userInput = new JTextArea(10,20);
        userInput.setLineWrap(true);
        userInput.setWrapStyleWord(true);
        userInput.setFont(myFont);
        userInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case 10:
                        calculator.inputGui = userInput.getText();
                        calculator.go();
                        break;
                  }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case 10:
                        userInput.setText(null);
                        break;
                }
            }
        });

        userInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(userInput.getText().equals("Введите выражение")) {
                    userInput.setText(null);
                    userInput.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(userInput.getText().equals("")) {
                    userInput.setText("Введите выражение");
                    userInput.setForeground(Color.GRAY);
                }
            }
        });


        JScrollPane inputScroller = new JScrollPane(userInput);
        inputScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelInNOut.add(inputScroller);

        panelInNOut.add(Box.createRigidArea(forRigidArea));

        output = new JTextArea(10,20);
        output.setText("Для вычисления введите выражение в верхнюю область и нажмите клавишу Enter.\n" +
                "Возможные математические операции:\n" +
                " '+' - сложение\n" +
                " '-' - вычитание\n" +
                " '*' - умножение\n" +
                " '/' - деление\n" +
                " '^' - возведение в степень\n" +
                " 'log' - натуральный логарифм");
        output.setLineWrap(true);
        output.setEditable(false);
        output.setWrapStyleWord(true);
        output.setFont(myFont);

        JScrollPane outputScroller = new JScrollPane(output);
        outputScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelInNOut.add(outputScroller);
        panelInNOut.add(Box.createRigidArea(forRigidArea));

        mainPanel.add(panelInNOut);

        JPanel buttonsBorder = new JPanel();
        buttonsBorder.setLayout(new BorderLayout());

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(6,1,10,10));

        JPanel upperButtons = new JPanel();
        upperButtons.setLayout(new BoxLayout(upperButtons,BoxLayout.Y_AXIS));

        JButton invB = new JButton();
        invB.setVisible(false);

        JButton showHistory = new JButton("История");
        showHistory.addActionListener(hs -> showPanelHistory());
        showHistory.setAlignmentX(Box.CENTER_ALIGNMENT);

        JPanel lowerButtons = new JPanel();
        lowerButtons.setLayout(new BoxLayout(lowerButtons,BoxLayout.Y_AXIS));

        JButton clearHistory = new JButton("Очистить историю");
        clearHistory.addActionListener(ch -> clearHistory());
        clearHistory.setAlignmentX(Box.CENTER_ALIGNMENT);

        panelButtons.add(invB);
        upperButtons.add(showHistory);
        lowerButtons.add(clearHistory);

        panelButtons.add(upperButtons);
        panelButtons.add(lowerButtons);

        mainPanel.add(panelButtons);

        panelHistory = new JPanel();
        panelHistory.setLayout(new BoxLayout(panelHistory,BoxLayout.Y_AXIS));
        panelHistory.add(Box.createRigidArea(forRigidArea));

        history = new JTextArea(10,20);
        history.setLineWrap(true);
        history.setWrapStyleWord(true);
        history.setEditable(false);
        history.setFont(myFont);
        history.append("Последние вычисления:\n");

        JScrollPane historyScroller = new JScrollPane(history);
        historyScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        historyScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panelHistory.add(historyScroller);
        panelHistory.add(Box.createRigidArea(forRigidArea));
        panelHistory.setVisible(false);

        mainPanel.add(panelHistory);
        mainPanel.add(Box.createRigidArea(forRigidArea));

        frame.add(mainPanel);
        frame.setVisible(true);

    }

    private void showPanelHistory(){
        if (panelHistory.isVisible()) {
            frame.setSize(std);
            panelHistory.setVisible(false);
        }
        else {
            frame.setSize(ext);
            panelHistory.setVisible(true);
        }
    }
    private void clearHistory(){
        history.setText(null);
        Calculator.results.clear();
        history.append("Последние вычисления:\n");
    }
}

