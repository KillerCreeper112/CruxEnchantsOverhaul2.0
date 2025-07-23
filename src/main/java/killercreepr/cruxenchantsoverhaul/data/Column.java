package killercreepr.cruxenchantsoverhaul.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;

public interface Column<T> extends Iterable<T> {
    @NotNull T[] get();
    default @Nullable T getAt(int index){
        return get()[index];
    }

    @NotNull
    @Override
    default Iterator<T> iterator(){
        return Arrays.stream(get()).iterator();
    }
}
