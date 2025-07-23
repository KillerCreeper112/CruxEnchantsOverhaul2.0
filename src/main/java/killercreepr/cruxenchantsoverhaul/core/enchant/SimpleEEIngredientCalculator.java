package killercreepr.cruxenchantsoverhaul.core.enchant;

import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class SimpleEEIngredientCalculator implements EEIngredientCalculator {
    protected final List<CruxRecipeIngredient> ingredients;

    public SimpleEEIngredientCalculator(List<CruxRecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NotNull
    @Override
    public List<CruxRecipeIngredient> calculateIngredients(Entity entity, int level, float quality) {
        return ingredients;
    }
}
