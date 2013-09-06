package sk.tomsik68.ai.nim;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.impl.AbstractHashable;

public class NIMGameReaction extends AbstractHashable implements IGameReaction {
    private final int sub;

    public NIMGameReaction(int take) {
        sub = take;
    }

    public int getTake() {
        return sub;
    }

    @Override
    public long getHash() {
        return sub;
    }

    @Override
    public String toString() {
        return sub + "";
    }
}
