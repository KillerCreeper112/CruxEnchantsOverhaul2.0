package killercreepr.cruxenchantsoverhaul.anvil.recipe;

import killercreepr.crux.api.item.predicate.ItemPredicate;
import net.kyori.adventure.key.Keyed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface AnvilRecipe extends Keyed {
    @NotNull
    ItemPredicate getFirstIngredient();

    @NotNull
    Collection<AnvilIngredient> getSecondIngredient();

    @Nullable
    AnvilRecipeResult merge(ItemStack first, ItemStack second, AnvilView view);

    AnvilIngredient findIngredient(@NotNull ItemStack input, ItemStack first);
}
