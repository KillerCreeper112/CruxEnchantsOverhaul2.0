package killercreepr.cruxenchantsoverhaul.anvil.recipe;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.crux.api.item.predicate.ItemPredicate;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import org.bukkit.inventory.ItemStack;

public interface AnvilIngredient {
    CruxItem applyToResult(CruxItem result);
    ItemPredicate getIngredient();
    NumberProvider getRepairAmount();
    boolean canApplyTo(ItemStack first);
}
