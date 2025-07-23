package killercreepr.cruxenchantsoverhaul.enchanting;

import killercreepr.cruxenchantsoverhaul.data.Column;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class BlockColumn implements Column<Block> {
    protected final Block[] objects;
    public BlockColumn(Block... objects) {
        this.objects = objects;
    }

    public BlockColumn(@NotNull Collection<Block> list){
        this(list.toArray(new Block[0]));
    }

    @Override
    public @NotNull Block[] get() {
        return objects;
    }
}
