package killercreepr.cruxenchantsoverhaul.enchanting;

import killercreepr.crux.core.data.util.EnchantPair;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public interface Enchanter {
    boolean canEnchantItem(@NotNull ItemStack item, @NotNull Map<Enchantment,  Integer> enchants);
    boolean canEnchantItem(@NotNull ItemStack item, @NotNull Collection<EnchantPair> enchants);
    boolean canEnchantItem(@NotNull ItemStack item, @NotNull EnchantPair[] enchants);
    boolean canEnchantItem(@NotNull ItemStack item, @NotNull Enchantment enchant, int level);

    boolean canSetEnchantments(@NotNull ItemStack item, @NotNull Map<Enchantment, Integer> enchants);
}
