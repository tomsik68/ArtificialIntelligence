package sk.tomsik68.ai.nim;

import java.util.ArrayList;
import java.util.Collection;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IGameReactionCreator;
import sk.tomsik68.ai.api.IPlaygroundState;

public class NIMGameReactionCreator implements IGameReactionCreator {
    private final int[] nums;

    public NIMGameReactionCreator(int... numbers) {
        nums = numbers;
    }

    @Override
    public Collection<IGameReaction> getPossibleReactions(IPlaygroundState state) {
        NIMPlaygroundState nim = (NIMPlaygroundState) state;
        ArrayList<IGameReaction> reactions = new ArrayList<IGameReaction>();
        for (int n : nums)
            if (nim.getLeft() >= n)
                reactions.add(new NIMGameReaction(n));
        return reactions;
    }

}
