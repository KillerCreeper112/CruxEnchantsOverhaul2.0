package killercreepr.cruxenchantsoverhaul.data.holder;

import killercreepr.crux.api.entity.memory.PlayerMemory;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.entity.memory.PlayerDataHolder;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class EnchanterHolder extends PlayerDataHolder {
    public static final Key KEY = Crux.key("enchanter");
    public EnchanterHolder(@NotNull PlayerMemory parent){
        this(KEY, parent);
    }
    public EnchanterHolder(@NotNull Key key, @NotNull PlayerMemory parent) {
        super(key, parent);
    }
}
