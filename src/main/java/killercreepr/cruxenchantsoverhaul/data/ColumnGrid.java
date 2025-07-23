package killercreepr.cruxenchantsoverhaul.data;

import killercreepr.crux.api.math.Pos2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;

public interface ColumnGrid<T> extends Iterable<Column<T>> {
    default @Nullable Column<T> getAt(int x, int z){
        return getColumns().get(Pos2D.at(x, z));
    }
    @NotNull Map<Pos2D, Column<T>> getColumns();

    @NotNull
    @Override
    default Iterator<Column<T>> iterator(){
        return getColumns().values().iterator();
    }
}
