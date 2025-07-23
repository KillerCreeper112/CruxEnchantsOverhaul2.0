package killercreepr.cruxenchantsoverhaul.values;

import killercreepr.crux.api.data.Holder;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class DefaultValues implements ValuesProvider {
    public static Holder<Map<Key, Integer>> DEFAULT_MAGIC_CAPACITY = Holder.direct(Map.of(
        Material.WOODEN_SWORD.key(), 4,
        Material.STONE_SWORD.key(), 6,
        Material.IRON_SWORD.key(), 8,
        Material.DIAMOND_SWORD.key(), 10,
        Material.NETHERITE_SWORD.key(), 12,
        Material.GOLDEN_SWORD.key(), 20,
        Material.TRIDENT.key(), 16,
        Material.MACE.key(), 14,
        Material.FISHING_ROD.key(), 5
    ));
    public static Holder<List<String>> MAGIC_CAPACITY_FORMAT = Holder.direct(List.of(
        "<gray>Magic Capacity: <#C180FF><item_total_enchants>/<item_magic_capacity>"
    ));
    @NotNull
    @Override
    public Holder<Map<Key, Integer>> DEFAULT_MAGIC_CAPACITY() {
        return DEFAULT_MAGIC_CAPACITY;
    }

    @NotNull
    @Override
    public Holder<List<String>> MAGIC_CAPACITY_FORMAT() {
        return MAGIC_CAPACITY_FORMAT;
    }

    @NotNull
    @Override
    public Holder<Map<Key, Integer>> MAGIC_CAPACITY_USAGE_PER_LEVEL() {
        return Holder.empty();
    }
}
