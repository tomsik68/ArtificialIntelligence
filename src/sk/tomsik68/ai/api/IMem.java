package sk.tomsik68.ai.api;

import java.util.List;

public interface IMem {
    public List<IGameReaction> getPossibleReactions(IPlaygroundState playground);

    public void setIsReactionGood(long playgroundHash, IGameReaction reaction, boolean good);

    public void dump(String fname) throws Exception;

    public boolean isStateKnown(IPlaygroundState state);

}
