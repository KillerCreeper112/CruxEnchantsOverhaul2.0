package killercreepr.cruxenchantsoverhaul.enchanting;

import killercreepr.crux.api.math.Pos2D;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BlockStateGrid<T extends BlockState> extends BlockGrid {
    public static <T extends BlockState> Builder<T> stateBuilder(@NotNull Class<T> type){
        return new Builder<>(type);
    }
    protected final @NotNull Class<T> type;
    protected final Map<Pos2D, BlockStateColumn<T>> columns;
    public BlockStateGrid(@NotNull Class<T> type, Map<Pos2D, BlockStateColumn<T>> columns) {
        super(columns);
        this.type = type;
        this.columns = columns;
    }

    public @NotNull Map<Pos2D, BlockStateColumn<T>> getStateColumns() {
        return columns;
    }

    public static final class Builder<T extends BlockState> {
        private Class<T> type;

        public Builder(Class<T> type) {
            this.type = type;
        }

        public Builder() {
        }

        private Map<Pos2D, BlockStateColumn<T>> columns = new HashMap<>();
        public Builder<T> columns(Map<Pos2D, BlockStateColumn<T>> columns) {
            this.columns = columns;
            return this;
        }

        public Builder<T> put(Pos2D pos, BlockStateColumn<T> column){
            columns.put(pos, column);
            return this;
        }

        public Builder<T> setType(Class<T> type) {
            this.type = type;
            return this;
        }

        public BlockStateGrid<T> build() {
            return new BlockStateGrid<>(type, columns);
        }
    }
}
