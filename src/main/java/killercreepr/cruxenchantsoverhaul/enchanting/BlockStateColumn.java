package killercreepr.cruxenchantsoverhaul.enchanting;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlockStateColumn<T extends BlockState> extends BlockColumn {
    protected final Class<T> type;
    public BlockStateColumn(Class<T> type, Block... objects) {
        super(objects);
        this.type = type;
    }

    public BlockStateColumn(Class<T> type, @NotNull Collection<Block> list) {
        super(list);
        this.type = type;
    }

    public @Nullable T getStateAt(int index){
        Block b = getAt(index);
        if(b == null) return null;
        return getState(b);
    }

    public boolean isValid(){
        return getValidStates().size() >= objects.length;
    }

    public boolean isValid(@NotNull Block b){
        return getState(b) != null;
    }

    public @Nullable T getState(@NotNull Block b){
        try{
            return type.cast(b.getState());
        }catch (ClassCastException ignored){ return null; }
    }

    public @NotNull List<T> getValidStates(){
        List<T> list = new ArrayList<>();
        for(Block b : this){
            T state = getState(b);
            if(state != null) list.add(state);
        }
        return list;
    }
}
