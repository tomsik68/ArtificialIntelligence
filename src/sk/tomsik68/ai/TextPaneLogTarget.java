package sk.tomsik68.ai;

import javax.swing.JTextPane;

import sk.tomsik68.ai.api.ILogTarget;

public class TextPaneLogTarget implements ILogTarget {
    private final JTextPane textPane;
    public TextPaneLogTarget(JTextPane tpOutput) {
        textPane = tpOutput;
    }

    @Override
    public void printLine(String line) {
        textPane.setText(textPane.getText()+line+"\n");
    }

}
