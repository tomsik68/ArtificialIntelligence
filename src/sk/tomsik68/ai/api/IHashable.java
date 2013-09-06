package sk.tomsik68.ai.api;

public interface IHashable {
    /**
     * 
     * @return Long that uniquely identifies this object. No other object of the
     *         same type can have the same hash.
     */
    public long getHash();

    public boolean equals(IHashable other);
}
