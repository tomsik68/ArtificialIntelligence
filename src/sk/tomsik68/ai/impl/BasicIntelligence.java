package sk.tomsik68.ai.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IGameReactionCreator;
import sk.tomsik68.ai.api.IGameRules;
import sk.tomsik68.ai.api.IIntelligence;
import sk.tomsik68.ai.api.ILogTarget;
import sk.tomsik68.ai.api.IMem;
import sk.tomsik68.ai.api.IPlaygroundState;
import sk.tomsik68.ai.nim.NIMGameReaction;

public class BasicIntelligence implements IIntelligence {
    private final IMem memory;
    private IGameReaction lastReaction = null;
    private long lastState = -1;
    private final IGameRules rules;
    private final Random brain = new Random();
    private final ILogTarget logging;
    private final IGameReactionCreator gameReactionCreator;
    private final String name;

    public BasicIntelligence(IGameRules r, IMem mem, ILogTarget log, IGameReactionCreator grc, String name) {
        rules = r;
        memory = mem;
        logging = log;
        gameReactionCreator = grc;
        this.name = name;
    }

    private List<IGameReaction> rulesFilter(List<IGameReaction> reactions, IPlaygroundState state) {
        List<IGameReaction> result = new ArrayList<IGameReaction>();
        for (IGameReaction reaction : reactions)
            if (rules.isAllowed(state, reaction)) {
                result.add(reaction);
            }
        return result;
    }

    @Override
    public IGameReaction getReaction(IPlaygroundState state) {
        List<IGameReaction> possibilities = memory.getPossibleReactions(state);
        if (!memory.isStateKnown(state) && possibilities.isEmpty()) {
            possibilities.addAll(gameReactionCreator.getPossibleReactions(state));
            for (IGameReaction possibility : possibilities) {
                // never lost with this move, because never played with this
                // move :)
                memory.setIsReactionGood(state.getHash(), possibility, true);
            }
        }
        possibilities = rulesFilter(possibilities, state);

        StringBuilder sb = new StringBuilder();
        for (IGameReaction possible : possibilities) {
            sb = sb.append(possible.toString()).append(',');
        }
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        logging.printLine(name + ": Moznosti(" + possibilities.size() + "): " + sb.toString());
        if (possibilities.size() == 1) {
            lastState = state.getHash();
            lastReaction = possibilities.get(0);
            logging.printLine(name + ":Taham " + ((NIMGameReaction) lastReaction).getTake());
            return lastReaction;
        } else if (possibilities.size() > 1) {
            lastState = state.getHash();
            lastReaction = possibilities.get(brain.nextInt(possibilities.size()));
            logging.printLine(name + ":Taham " + ((NIMGameReaction) lastReaction).getTake());
            return lastReaction;
        } else {
            // resign
            logging.printLine(name + ":Nemam co tahat, vzdavam sa");
            return null;
        }
    }

    @Override
    public void startGame() {
        lastState = -1;
        lastReaction = null;
    }

    @Override
    public void endGame(boolean won) {
        if (!won) {
            if (lastReaction == null)
                return;
            logging.printLine("");
            logging.printLine(name + ":Prehral som. Poucenie z hry:");
            logging.printLine(name + ":Tahat " + ((NIMGameReaction) lastReaction).getTake() + "  pri " + lastState + " je blbost.");
            memory.setIsReactionGood(lastState, lastReaction, false);
        }
        try {
            memory.dump("brain.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastState = -1;
        lastReaction = null;

    }

    public IGameReactionCreator getGameReactor() {
        return gameReactionCreator;
    }
}
