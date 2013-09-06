package sk.tomsik68.ai.api;

public interface IGameRules {
    public boolean isAllowed(IPlaygroundState state, IGameReaction reaction);
}
