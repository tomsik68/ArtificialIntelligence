package sk.tomsik68.ai.api;

public interface IIntelligence {
    public IGameReaction getReaction(IPlaygroundState state);

    public void startGame();

    public void endGame(boolean won);
}
