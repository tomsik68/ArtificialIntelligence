package sk.tomsik68.ai.nim;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IPlaygroundState;
import sk.tomsik68.ai.impl.AbstractHashable;

public class NIMPlaygroundState extends AbstractHashable implements IPlaygroundState {
    private int sticks;

    public NIMPlaygroundState(int startSticks) {
        sticks = startSticks;
    }

    @Override
    public long getHash() {
        return sticks;
    }

    public int getLeft() {
        return sticks;
    }

    @Override
    public boolean perform(IGameReaction r) {
        if (!(r instanceof NIMGameReaction))
            throw new IllegalArgumentException("Invalid reaction");
        NIMGameReaction reaction = (NIMGameReaction) r;
        sticks -= reaction.getTake();
        return sticks == 0;
    }

}
