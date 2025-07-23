package killercreepr.cruxenchantsoverhaul.values;

import killercreepr.crux.api.data.Holder;
import killercreepr.crux.api.data.Reloadable;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface ValuesProvider extends Reloadable {
    @NotNull
    Holder<Map<Key, Integer>> DEFAULT_MAGIC_CAPACITY();
    @NotNull
    Holder<List<String>> MAGIC_CAPACITY_FORMAT();
    @NotNull Holder<Map<Key, Integer>> MAGIC_CAPACITY_USAGE_PER_LEVEL();
}
