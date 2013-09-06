package sk.tomsik68.ai.impl;

import sk.tomsik68.ai.api.IGameManager;
import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IGameRules;
import sk.tomsik68.ai.api.IIntelligence;
import sk.tomsik68.ai.api.IPlaygroundState;

public abstract class AbstractGameManager implements IGameManager {
    protected IIntelligence p1, p2;
    protected IPlaygroundState playground;
    protected final IGameRules rules;
    protected boolean p1Turn = true;

    public AbstractGameManager(IGameRules rulez) {
        rules = rulez;
    }

    @Override
    public IGameRules getRules() {
        return rules;
    }

    @Override
    public IIntelligence getPlayer1() {
        return p1;
    }

    @Override
    public IIntelligence getPlayer2() {
        return p2;
    }

    @Override
    public IPlaygroundState getPlayground() {
        return playground;
    }

    @Override
    public void executeTurn() {
        IIntelligence p;
        if (p1Turn) {
            p = p1;
        } else {
            p = p2;
        }
        boolean gameEnded = false;
        IGameReaction react = p.getReaction(playground);
        if (react == null) {
            if (p1Turn) {
                gameEnded = true;
                p1.endGame(false);
                p2.endGame(true);
            } else {
                p2.endGame(false);
                p1.endGame(true);
                gameEnded = true;
            }
            newGame();
            return;
        }
        if (playground.perform(react) && !gameEnded)
            if (p1Turn) {
                p1.endGame(true);
                p2.endGame(false);
                gameEnded = true;
            } else {
                p1.endGame(false);
                p2.endGame(true);
                gameEnded = true;
            }

        p1Turn = !p1Turn;
    }
}
