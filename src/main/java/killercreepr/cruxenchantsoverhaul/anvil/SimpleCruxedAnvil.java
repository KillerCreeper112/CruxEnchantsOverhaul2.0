package killercreepr.cruxenchantsoverhaul.anvil;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.crux.core.Crux;
import killercreepr.cruxenchantsoverhaul.CruxEnchantsOverhaul;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipe;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipeResult;
import killercreepr.cruxenchantsoverhaul.component.EnchantComponents;
import killercreepr.cruxenchantsoverhaul.enchanting.Enchanter;
import killercreepr.cruxenchantsoverhaul.item.MagicCapacityHandler;
import killercreepr.cruxenchantsoverhaul.registries.EnchantsRegistries;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleCruxedAnvil implements CruxedAnvil{
    protected ItemStack first;
    protected ItemStack second;
    protected Enchanter enchanter = CruxEnchantsOverhaul.inst().getEnchanter();
    protected MagicCapacityHandler magicHandler = CruxEnchantsOverhaul.inst().getMagicCapacityHandler();

    public Enchanter getEnchanter() {
        return enchanter;
    }

    public void setEnchanter(Enchanter enchanter) {
        this.enchanter = enchanter;
    }

    public AnvilRecipe findRecipe(){
        for(AnvilRecipe recipe : EnchantsRegistries.ANVIL_RECIPES){
            if(!recipe.getFirstIngredient().test(first)) continue;
            if(recipe.findIngredient(second, first) == null) continue;
            return recipe;
        }
        return null;
    }

    public AnvilRecipeResult merge(AnvilView view){
        if(CruxItem.isEmpty(first) || CruxItem.isEmpty(second)) return null;

        AnvilRecipe recipe = findRecipe();
        if(recipe != null){
            return recipe.merge(first, second, view);
        }

        Key firstType = Crux.handlers().item().getType(first);
        Key secondType = Crux.handlers().item().getType(second);

        if(!secondType.equals(Key.key("enchanted_book")) && !firstType.equals(secondType)) return new AnvilRecipeResult(null, null, null);

        if(firstType.equals(secondType)){
            return handleSameTypes();
        }
        return handleDifferentTypes();
    }

    public int calculateCost(ItemStack first, ItemStack result){
        Map<Enchantment, Integer> previousEnchants = first.getEnchantments();
        Map<Enchantment, Integer> newEnchants = result.getEnchantments();

        int cost = 1 + calculateEnchantsCost(previousEnchants, newEnchants);

        if(first.getItemMeta() instanceof EnchantmentStorageMeta m && result.getItemMeta() instanceof EnchantmentStorageMeta mm){
            cost += calculateEnchantsCost(m.getStoredEnchants(), mm.getStoredEnchants());
        }
        return cost;
    }

    public int calculateEnchantsCost(Map<Enchantment, Integer> previousEnchants, Map<Enchantment, Integer> newEnchants){
        int cost = 0;
        for(Map.Entry<Enchantment, Integer> entry : newEnchants.entrySet()){
            Enchantment ench = entry.getKey();
            Integer level = entry.getValue();

            Integer previousLevel = previousEnchants.get(ench);
            if(previousLevel==null){
                cost += level + 1;
                continue;
            }
            int difference = level - previousLevel;
            cost += difference;
        }
        return cost;
    }

    private AnvilRecipeResult handleDifferentTypes(){
        ItemStack result = mergeEnchants(first, second);
        int experienceCost = calculateCost(first, result);
        return new AnvilRecipeResult(result, experienceCost, null);
    }

    private AnvilRecipeResult handleSameTypes(){
        ItemStack result = mergeEnchants(first, second);

        int experienceCost = calculateCost(first, result);
        Crux.handlers().item().update(result);
        return new AnvilRecipeResult(result, experienceCost, null);
    }

    public ItemStack mergeEnchants(@NotNull ItemStack first, @NotNull ItemStack second){
        if(first.getItemMeta() == null || second.getItemMeta() == null) return null;
        ItemStack result = first.clone();

        result.editMeta(meta ->{
            if(second.getItemMeta() instanceof EnchantmentStorageMeta mSecond){
                if(meta instanceof EnchantmentStorageMeta m){
                    mergeEnchants(result, m.getStoredEnchants(), mSecond.getStoredEnchants()).forEach((ench, level) ->{
                        m.addStoredEnchant(ench, level, true);
                    });
                }else{
                    mergeEnchants(result, meta.getEnchants(), mSecond.getStoredEnchants()).forEach((ench, level) ->{
                        meta.addEnchant(ench, level, true);
                    });
                }
            }

            mergeEnchants(result, meta.getEnchants(), second.getItemMeta().getEnchants()).forEach((ench, level) ->{
                meta.addEnchant(ench, level, true);
            });
        });

        return Crux.handlers().item().update(result);
    }

    public boolean conflictsWith(@NotNull Collection<Enchantment> enchants, @NotNull Enchantment check){
        for(Enchantment e : enchants){
            if(e.conflictsWith(check)) return true;
        }
        return false;
    }

    public int getMagicUsage(Map<Enchantment, Integer> map){
        int x = 0;
        for (Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            x += magicHandler.getMagicUsage(entry.getKey(), entry.getValue());
        }
        return x;
    }

    public Map<Enchantment, Integer> mergeEnchants(@NotNull ItemStack item,
                                                   @NotNull Map<Enchantment, Integer> first,
                                                   @NotNull Map<Enchantment, Integer> second){
        Integer capacity = magicHandler.getMagicCapacity(item);
        int total = getMagicUsage(first);
        if(capacity != null && total >= capacity) return new HashMap<>(first);

        CruxItem crux = CruxItem.wrap(item);
        Integer maxAddon = crux.getOrDefaultData(EnchantComponents.ENCHANTS_MAX_LEVEL_ADDON);

        ItemMeta meta = item.getItemMeta();

        Map<Enchantment, Integer> map = new HashMap<>(first);
        for (Map.Entry<Enchantment, Integer> entry : second.entrySet()) {
            Enchantment ench = entry.getKey();
            Integer level = entry.getValue();
            if(!(meta instanceof EnchantmentStorageMeta) && !ench.canEnchantItem(item)) continue;

            Integer firstLevel = first.get(ench);
            if(firstLevel == null){
                if(conflictsWith(first.keySet(), ench) && !(meta instanceof EnchantmentStorageMeta)) continue;

                if(capacity != null){
                    while (level > 0 && magicHandler.getMagicUsage(ench, level) + total > capacity) {
                        level--;
                    }
                    if (level < 1) continue;
                    if(total + magicHandler.getMagicUsage(ench, level) > capacity) continue;
                }

                map.put(ench, level);
                total += magicHandler.getMagicUsage(ench, level);
                if(capacity != null && total >= capacity) break;
                continue;
            }
            if(Objects.equals(level, firstLevel)){
                int max = ench.getMaxLevel();
                if(maxAddon != null && max > 1) max += maxAddon;
                int newLevel = Math.min(firstLevel + 1, max);
                if(newLevel == firstLevel) continue;
                if(capacity != null){
                    int testTotal = total;
                    testTotal -= magicHandler.getMagicUsage(ench, firstLevel);
                    testTotal += magicHandler.getMagicUsage(ench, newLevel);

                    if(testTotal > capacity) continue;
                }

                map.put(ench, newLevel);
                total -= magicHandler.getMagicUsage(ench, firstLevel);
                total += magicHandler.getMagicUsage(ench, newLevel);
                if(capacity != null && total >= capacity) break;
                continue;
            }
            if(firstLevel > level) continue;

            if(capacity != null){
                while (level >= firstLevel && magicHandler.getMagicUsage(ench, level) + total > capacity) {
                    level--;
                }
                if (level <= firstLevel) continue;

                int testTotal = total;
                testTotal -= magicHandler.getMagicUsage(ench, firstLevel);
                testTotal += magicHandler.getMagicUsage(ench, level);

                if(testTotal > capacity) continue;
            }

            map.put(ench, level);
            total -= magicHandler.getMagicUsage(ench, firstLevel);
            total += magicHandler.getMagicUsage(ench, level);
            if(capacity != null && total >= capacity) break;
        }
        /*second.forEach((ench, level) ->{
            if(!(item.getItemMeta() instanceof EnchantmentStorageMeta) && !ench.canEnchantItem(item)) return;
            Integer firstLevel = first.get(ench);
            if(firstLevel == null){
                if(conflictsWith(first.keySet(), ench) && !(item.getItemMeta() instanceof EnchantmentStorageMeta)) return;
                map.put(ench, level);
                return;
            }
            if(Objects.equals(level, firstLevel)){
                map.put(ench, Math.min(firstLevel + 1, ench.getMaxLevel()));
                return;
            }
            if(firstLevel > level) return;
            map.put(ench, level);
        });*/
        return map;
    }

    public ItemStack getFirst() {
        return first;
    }

    public void setFirst(ItemStack first) {
        this.first = first;
    }

    public ItemStack getSecond() {
        return second;
    }

    public void setSecond(ItemStack second) {
        this.second = second;
    }
}
