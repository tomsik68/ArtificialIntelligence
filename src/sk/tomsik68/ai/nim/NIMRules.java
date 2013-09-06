package sk.tomsik68.ai.nim;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IGameRules;
import sk.tomsik68.ai.api.IPlaygroundState;

public class NIMRules implements IGameRules {
    private final int[] numbersWeCanSubstract;

    public NIMRules(int[] numbersWeCanSubstract) {
        this.numbersWeCanSubstract = numbersWeCanSubstract;
    }

    public int[] getSubstractionPossibilities() {
        return numbersWeCanSubstract;
    }

    @Override
    public boolean isAllowed(IPlaygroundState state, IGameReaction reaction) {
        if (!(state instanceof NIMPlaygroundState) || !(reaction instanceof NIMGameReaction))
            throw new IllegalArgumentException("Some of arguments is invalid");
        NIMPlaygroundState playground = (NIMPlaygroundState) state;
        NIMGameReaction react = (NIMGameReaction) reaction;
        if (react.getTake() > playground.getLeft()) {
            return false;
        }
        boolean allow = false;
        for (int i = 0; i < numbersWeCanSubstract.length; ++i) {
            allow = allow || (numbersWeCanSubstract[i] == react.getTake());
            if (allow)
                break;
        }
        return allow;
    }

}
