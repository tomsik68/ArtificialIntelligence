package sk.tomsik68.ai.impl;

import sk.tomsik68.ai.api.IHashable;

public abstract class AbstractHashable implements IHashable {
    @Override
    public boolean equals(IHashable other) {
        return other.getHash() == getHash() && other.getClass().equals(getClass());
    }
}
