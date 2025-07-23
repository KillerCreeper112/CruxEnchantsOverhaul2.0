package killercreepr.cruxenchantsoverhaul.anvil.recipe;

import org.bukkit.inventory.ItemStack;

public class AnvilRecipeResult {
    protected final ItemStack resultItem;
    protected final Integer experienceCost;
    protected final Integer itemAmountCost;

    public AnvilRecipeResult(ItemStack resultItem, Integer experienceCost, Integer itemAmountCost) {
        this.resultItem = resultItem;
        this.experienceCost = experienceCost;
        this.itemAmountCost = itemAmountCost;
    }

    public ItemStack getResultItem() {
        return resultItem;
    }

    public Integer getExperienceCost() {
        return experienceCost;
    }

    public Integer getItemAmountCost() {
        return itemAmountCost;
    }
}
