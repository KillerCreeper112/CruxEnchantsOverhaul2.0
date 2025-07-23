package killercreepr.cruxenchantsoverhaul.enchanting;

import killercreepr.crux.api.math.Pos2D;
import killercreepr.cruxenchantsoverhaul.data.Column;
import killercreepr.cruxenchantsoverhaul.data.ColumnGrid;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BlockGrid implements ColumnGrid<Block> {
    public static Builder builder(){
        return new Builder();
    }
    protected final Map<Pos2D, Column<Block>> columns;
    public BlockGrid(Map<Pos2D, ? extends Column<Block>> columns) {
        this.columns = (Map<Pos2D, Column<Block>>) columns;
    }

    @Override
    public @NotNull Map<Pos2D, Column<Block>> getColumns() {
        return columns;
    }

    public static final class Builder {
        private Map<Pos2D, Column<Block>> columns = new HashMap<>();
        public Builder columns(Map<Pos2D, Column<Block>> columns) {
            this.columns = columns;
            return this;
        }

        public Builder put(Pos2D pos, Column<Block> column){
            columns.put(pos, column);
            return this;
        }

        public BlockGrid build() {
            return new BlockGrid(columns);
        }
    }
}
