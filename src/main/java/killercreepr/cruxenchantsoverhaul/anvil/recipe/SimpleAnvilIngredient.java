package killercreepr.cruxenchantsoverhaul.anvil.recipe;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.crux.api.item.predicate.ItemPredicate;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SimpleAnvilIngredient implements AnvilIngredient {
    protected final @NotNull ItemPredicate ingredient;
    protected final @NotNull NumberProvider repairAmount;

    public SimpleAnvilIngredient(@NotNull ItemPredicate ingredient, @NotNull NumberProvider repairAmount) {
        this.ingredient = ingredient;
        this.repairAmount = repairAmount;
    }

    public CruxItem applyToResult(CruxItem result){
        return result;
    }

    @NotNull
    public ItemPredicate getIngredient() {
        return ingredient;
    }

    @NotNull
    public NumberProvider getRepairAmount() {
        return repairAmount;
    }

    @Override
    public boolean canApplyTo(ItemStack first) {
        return true;
    }
}
