package sk.tomsik68.ai.api;

public interface IPlaygroundState extends IHashable {
    /**
     * Performs a playground change using given reaction
     * 
     * @param react
     * @return If the game ended.
     */
    public boolean perform(IGameReaction react);
}
