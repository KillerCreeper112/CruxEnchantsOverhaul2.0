package killercreepr.cruxenchantsoverhaul.enchanting;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.crux.core.util.CruxEntityUtil;
import killercreepr.cruxcrafting.api.crafting.context.CruxIngredientContext;
import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.core.enchant.AmountEEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.menu.enchanting.EnchantTableMenu;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantRequirements {
    protected final EnchantTableMenu menu;
    public List<CruxRecipeIngredient> ingredients;
    public int exp;
    public int lapis;
    public int requiredLevel;
    public int requiredPower;

    public EnchantRequirements(EnchantTableMenu menu) {
        this.menu = menu;
    }

    public List<CruxRecipeIngredient> addIngredientAmount(List<CruxRecipeIngredient> add){
        if(ingredients == null || add == null) return null;
        List<CruxRecipeIngredient> list = new ArrayList<>();
        int index = -1;
        for (CruxRecipeIngredient current : ingredients) {
            index++;
            if(index >= add.size()) break;
            CruxRecipeIngredient addon = add.get(index);
            CruxRecipeIngredient newIngredient = AmountEEIngredientCalculator.editAmount(current, currentAmount ->{
                return currentAmount + AmountEEIngredientCalculator.getAmount(addon);
            });
            list.add(newIngredient);
        }
        ingredients = list;
        return list;
    }

    public void removeCosts(Entity e){
        if(lapis > 0){
            ItemStack item = menu.LAPIS.getItem();
            if(!menu.LAPIS.isBlank(item)){
                item.setAmount(item.getAmount()-lapis);
                if(CruxItem.isEmpty(item)){
                    menu.LAPIS.setItem(menu.LAPIS.getSlottedItemReplacement(), true);
                }
            }
        }
        if(exp > 0 && e instanceof Player p){
            p.setExperienceLevelAndProgress(Math.max(
                p.calculateTotalExperiencePoints() - exp, 0
            ));
        }
        if(ingredients != null) removeIngredients(ingredients);
    }

    public RequirementResult hasRequirements(Entity e){
        if(CruxEntityUtil.isNonSurvival(e)) return RequirementResult.SUCCESS;
        if(!hasPower(e, requiredPower)) return RequirementResult.NOT_ENOUGH_POWER;
        if(!hasRequiredLevel(e, requiredLevel)) return RequirementResult.NOT_REQUIRED_LEVEL;
        if(!hasExperience(e, exp)) return RequirementResult.NOT_ENOUGH_EXPERIENCE_POINTS;
        if(!hasLapis(e, lapis)) return RequirementResult.NOT_ENOUGH_LAPIS_LAZULI;
        if(ingredients != null && !hasIngredients(ingredients)) return RequirementResult.NOT_ENOUGH_INGREDIENTS;
        return RequirementResult.SUCCESS;
    }

    public boolean hasPower(Entity e, int power){
        return menu.getBlock().getPower() >= power;
    }

    public boolean hasLapis(Entity e, int lapis){
        ItemStack item = menu.LAPIS.getItem();
        if(menu.LAPIS.isBlank(item) && lapis > 0) return false;
        return item.getAmount() >= lapis;
    }

    public boolean hasExperience(Entity e, int exp){
        if(e instanceof Player p){
            return p.calculateTotalExperiencePoints() >= exp;
        }
        return true;
    }

    public boolean hasRequiredLevel(Entity e, int level){
        if(e instanceof Player p){
            return p.getLevel() >= level;
        }
        return true;
    }

    public boolean hasIngredients(List<CruxRecipeIngredient> ingredients){
        List<Integer> list = new ArrayList<>();
        for (CruxRecipeIngredient ingredient : ingredients) {
            for (Integer slot : menu.getIngredientInputSlots()) {
                if(list.contains(slot)) continue;

                ItemStack item = menu.getInventory().getItem(slot);
                if(CruxItem.isEmpty(item)) continue;
                var ctx = CruxIngredientContext.ingredientContext(item);
                if(!ingredient.test(ctx)) continue;
                list.add(slot);
            }
        }
        return list.size() == ingredients.size();
    }

    public void removeIngredients(List<CruxRecipeIngredient> ingredients){
        for (CruxRecipeIngredient ingredient : ingredients) {
            for (Integer slot : menu.getIngredientInputSlots()) {
                ItemStack item = menu.getInventory().getItem(slot);
                if(CruxItem.isEmpty(item)) continue;
                var ctx = CruxIngredientContext.ingredientContext(item);
                if(!ingredient.test(ctx)) continue;
                ingredient.removeItem(ctx);
            }
        }
    }

    public enum RequirementResult{
        NOT_ENOUGH_EXPERIENCE_POINTS,
        NOT_ENOUGH_LAPIS_LAZULI,
        NOT_ENOUGH_INGREDIENTS,
        NOT_REQUIRED_LEVEL,
        NOT_ENOUGH_POWER,
        SUCCESS
    }
}
