package sk.tomsik68.ai.api;

import java.util.Collection;

public interface IGameReactionCreator {
    public Collection<IGameReaction> getPossibleReactions(IPlaygroundState state);
}
