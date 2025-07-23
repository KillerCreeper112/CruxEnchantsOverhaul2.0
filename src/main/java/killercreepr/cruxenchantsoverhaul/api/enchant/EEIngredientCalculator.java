package killercreepr.cruxenchantsoverhaul.api.enchant;

import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface EEIngredientCalculator {
    @NotNull List<CruxRecipeIngredient> calculateIngredients(Entity entity, int level);
}
