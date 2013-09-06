package sk.tomsik68.ai.nim;

import sk.tomsik68.ai.api.ILogTarget;
import sk.tomsik68.ai.api.IMem;
import sk.tomsik68.ai.impl.AbstractGameManager;
import sk.tomsik68.ai.impl.BasicIntelligence;
import sk.tomsik68.ai.impl.MemImpl;

public class NIMGameManager extends AbstractGameManager {
    private final int startSticks;
    private final ILogTarget logging;

    public NIMGameManager(int initialSticks, ILogTarget log, int... numbersWeCanSubstract) {
        super(new NIMRules(numbersWeCanSubstract));
        IMem memory = new MemImpl();
        p1 = new BasicIntelligence(getRules(), memory, log, new NIMGameReactionCreator(numbersWeCanSubstract), "I1");
        // p1 = new HumanPlayer(getRules());
        p2 = new BasicIntelligence(getRules(), memory, log, new NIMGameReactionCreator(numbersWeCanSubstract), "I2");
        startSticks = initialSticks;
        logging = log;
        newGame();
    }

    public int getStartSticks() {
        return startSticks;
    }

    @Override
    public void newGame() {
        playground = new NIMPlaygroundState(startSticks);
        logging.printLine("------------NOVA HRA-------------");
    }

    public int getSticksLeft() {
        return ((NIMPlaygroundState) playground).getLeft();

    }

    @Override
    public void executeTurn() {
        logging.printLine("Pocet: " + getSticksLeft());
        super.executeTurn();
        logging.printLine("");
    }

}
