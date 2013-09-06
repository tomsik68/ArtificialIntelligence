package sk.tomsik68.ai.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import sk.tomsik68.ai.api.IGameReaction;
import sk.tomsik68.ai.api.IMem;
import sk.tomsik68.ai.api.IPlaygroundState;

public class MemImpl implements IMem {
    private final HashMap<Long, List<IGameReaction>> data;

    public MemImpl() {
        data = new HashMap<Long, List<IGameReaction>>();
    }

    @Override
    public List<IGameReaction> getPossibleReactions(IPlaygroundState playground) {
        long hash = playground.getHash();
        List<IGameReaction> result = new ArrayList<IGameReaction>();
        if (!data.containsKey(hash))
            return result;
        result.addAll(data.get(hash));
        return result;
    }

    @Override
    public void setIsReactionGood(long playgroundHash, IGameReaction reaction, boolean good) {
        createPlaygroundCaseIfNeccessary(playgroundHash);
        IGameReaction key = null;
        List<IGameReaction> whatICanDo = data.get(playgroundHash);
        if (good)
            whatICanDo.add(reaction);
        else {
            for (IGameReaction r : whatICanDo)
                if (r.equals(reaction))
                    key = r;
            whatICanDo.remove(key);
        }
        data.put(playgroundHash, whatICanDo);
    }

    @Override
    public void dump(String fname) throws Exception {
        File dest = new File(fname);
        if (!dest.exists())
            dest.createNewFile();
        PrintWriter pw = new PrintWriter(new FileWriter(dest));
        for (Entry<Long, List<IGameReaction>> entry : data.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb = sb.append(entry.getKey()).append(": ");
            for (IGameReaction reaction : entry.getValue())
                sb = sb.append(reaction.toString()).append(',');
            pw.println(sb.toString());
        }
        pw.flush();
        pw.close();
    }

    private void createPlaygroundCaseIfNeccessary(long hash) {
        if (!data.containsKey(hash))
            data.put(hash, new ArrayList<IGameReaction>());
    }

    @Override
    public boolean isStateKnown(IPlaygroundState state) {
        return data.containsKey(state.getHash());
    }
}
