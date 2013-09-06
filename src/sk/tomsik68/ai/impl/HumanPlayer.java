package sk.tomsik68.ai.impl;

import javax.swing.JOptionPane;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IGameRules;
import sk.tomsik68.ai.api.IIntelligence;
import sk.tomsik68.ai.api.IPlaygroundState;
import sk.tomsik68.ai.nim.NIMGameReaction;

public class HumanPlayer implements IIntelligence {
    private final IGameRules rules;

    public HumanPlayer(IGameRules rules) {
        this.rules = rules;
    }

    @Override
    public IGameReaction getReaction(IPlaygroundState state) {
        String input = JOptionPane.showInputDialog("Si na tahu! Kolko tyciek si vezmes?");

        if (!isInt(input)) {
            JOptionPane.showMessageDialog(null, "Mozes napisat len cisla!");
            return getReaction(state);
        }
        NIMGameReaction reaction = new NIMGameReaction(Integer.parseInt(input));
        if (!rules.isAllowed(state, reaction)) {
            JOptionPane.showMessageDialog(null, "Nepovoleny pocet!");
        }
        if (reaction.getTake() <= 0)
            return null;
        return reaction;
    }

    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void startGame() {
    }

    @Override
    public void endGame(boolean won) {
        JOptionPane.showMessageDialog(null, (won ? "Vy" : "Pre") + "hral si!");
    }

}
