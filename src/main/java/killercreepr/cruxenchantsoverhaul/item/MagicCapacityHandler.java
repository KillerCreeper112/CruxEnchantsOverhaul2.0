package killercreepr.cruxenchantsoverhaul.item;

import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MagicCapacityHandler {
    @Nullable Integer getDefaultMagicCapacity(@NotNull Key material);
    @Nullable Integer getMagicCapacity(@NotNull ItemStack item);
    void setMagicCapacity(@NotNull ItemStack item, @Nullable Integer value);
    int getMagicUsage(@NotNull Enchantment enchant, int level);
}
