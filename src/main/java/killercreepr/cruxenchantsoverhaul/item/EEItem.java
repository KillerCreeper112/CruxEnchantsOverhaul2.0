package killercreepr.cruxenchantsoverhaul.item;

import killercreepr.crux.api.text.format.FormatSerializer;
import killercreepr.cruxenchantsoverhaul.CruxEnchantsOverhaul;
import killercreepr.cruxitems.core.item.SimpleCruxedItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class EEItem extends SimpleCruxedItem {
    public EEItem(@NotNull Material material) {
        super(material);
    }

    public EEItem(@NotNull ItemStack item) {
        super(item);
    }

    public EEItem(@NotNull FormatSerializer format, @NotNull Material material) {
        super(format, material);
    }

    public EEItem(@NotNull FormatSerializer format, @NotNull ItemStack item) {
        super(format, item);
    }

    public EEItem enchant(@NotNull Enchantment enchant, int level){
        enchant(enchant, level, true);
        return this;
    }

    public EEItem storageEnchant(@NotNull Enchantment enchant, int level){
        editMeta(EnchantmentStorageMeta.class, meta ->{
            meta.addStoredEnchant(enchant, level, true);
        });
        return this;
    }

    public boolean hasMagicCapacity(){
        return getMagicCapacity() != null;
    }

    public boolean wouldExceedMagicCapacity(int level){
        return wouldTotalExceedMagicCapacity(getMagicUsage() + level);
    }

    public boolean wouldTotalExceedMagicCapacity(int totalLevels){
        Integer blight = getMagicCapacity();
        if(blight==null) return false;
        return totalLevels > blight;
    }

    public boolean hasAvailableEnchantmentStorage(){
        Integer blight = getMagicCapacity();
        if(blight == null) return true;
        return getMagicUsage() < blight;
    }

    public @Nullable Integer getMagicCapacity(){
        return CruxEnchantsOverhaul.inst().getMagicCapacityHandler().getMagicCapacity(item);
    }

    public EEItem setMagicCapacity(@Nullable Integer amount){
        CruxEnchantsOverhaul.inst().getMagicCapacityHandler().setMagicCapacity(item, amount);
        return this;
    }

    public int getMagicUsage(){
        int x = 0;
        var magicHandler = CruxEnchantsOverhaul.inst().getMagicCapacityHandler();
        for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
            x += magicHandler.getMagicUsage(entry.getKey(), entry.getValue());
        }
        return x;
    }

    public int getTotalEnchantLevels(){
        int total = 0;
        for(int i : item.getEnchantments().values()){
            total += i;
        }
        return total;
    }
}
