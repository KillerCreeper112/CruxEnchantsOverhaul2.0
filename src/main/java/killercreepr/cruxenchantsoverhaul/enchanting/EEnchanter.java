package killercreepr.cruxenchantsoverhaul.enchanting;

import killercreepr.crux.core.data.util.EnchantPair;
import killercreepr.cruxenchantsoverhaul.item.EEItem;
import killercreepr.cruxenchantsoverhaul.item.MagicCapacityHandler;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class EEnchanter implements Enchanter {
    protected final MagicCapacityHandler magicCapacityHandler;
    public EEnchanter(MagicCapacityHandler magicCapacityHandler) {
        this.magicCapacityHandler = magicCapacityHandler;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item, @NotNull Map<Enchantment, Integer> enchants) {
        int addedLevels = 0;
        for(var entry : enchants.entrySet()){
            addedLevels += magicCapacityHandler.getMagicUsage(entry.getKey(), entry.getValue());
        }
        return !new EEItem(item).wouldExceedMagicCapacity(addedLevels);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item, @NotNull Collection<EnchantPair> enchants) {
        int addedLevels = 0;
        for(EnchantPair pair : enchants){
            addedLevels += magicCapacityHandler.getMagicUsage(pair.getEnchant(), pair.getLevel());
        }
        return !new EEItem(item).wouldExceedMagicCapacity(addedLevels);
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item, @NotNull EnchantPair[] enchants) {
        return canEnchantItem(item, Arrays.asList(enchants));
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item, @NotNull Enchantment enchant, int level) {
        EEItem i = new EEItem(item);
        return !i.wouldExceedMagicCapacity(magicCapacityHandler.getMagicUsage(enchant, level));
    }

    @Override
    public boolean canSetEnchantments(@NotNull ItemStack item, @NotNull Map<Enchantment, Integer> enchants) {
        int addedLevels = 0;
        for(var entry : enchants.entrySet()){
            addedLevels += magicCapacityHandler.getMagicUsage(entry.getKey(), entry.getValue());
        }
        return !new EEItem(item).wouldTotalExceedMagicCapacity(addedLevels);
    }
}
