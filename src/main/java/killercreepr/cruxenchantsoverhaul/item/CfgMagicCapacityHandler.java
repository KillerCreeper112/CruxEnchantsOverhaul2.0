package killercreepr.cruxenchantsoverhaul.item;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.cruxenchantsoverhaul.component.EnchantComponents;
import killercreepr.cruxenchantsoverhaul.values.ValuesProvider;
import killercreepr.cruxitems.api.item.CruxedItem;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CfgMagicCapacityHandler implements MagicCapacityHandler {
    protected final @NotNull ValuesProvider cfg;
    public CfgMagicCapacityHandler(@NotNull ValuesProvider cfg) {
        this.cfg = cfg;
    }

    @Nullable
    @Override
    public Integer getDefaultMagicCapacity(@NotNull Key material) {
        return cfg.DEFAULT_MAGIC_CAPACITY().valueOr(Map.of()).get(material);
    }

    @Override
    public @Nullable Integer getMagicCapacity(@NotNull ItemStack item) {
        Key key = CruxedItem.cruxed(item).getPluginItemKey();
        if(key == null) key = item.getType().getKey();
        return CruxItem.create(item).getOrDefault(EnchantComponents.MAGIC_CAPACITY, getDefaultMagicCapacity(key));
    }

    @Override
    public void setMagicCapacity(@NotNull ItemStack item, @Nullable Integer value) {
        CruxItem.create(item).set(EnchantComponents.MAGIC_CAPACITY, value);
    }

    @Override
    public int getMagicUsage(@NotNull Enchantment enchant, int level) {
        return cfg.MAGIC_CAPACITY_USAGE_PER_LEVEL().valueOr(Map.of())
            .getOrDefault(enchant.key(), 1) * level;
    }
}
