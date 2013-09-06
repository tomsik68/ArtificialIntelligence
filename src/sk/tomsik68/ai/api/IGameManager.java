package sk.tomsik68.ai.api;

public interface IGameManager {
    public IGameRules getRules();

    public IIntelligence getPlayer1();

    public IIntelligence getPlayer2();

    public void executeTurn();

    public IPlaygroundState getPlayground();

    public void newGame();
}
