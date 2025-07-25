package killercreepr.cruxenchantsoverhaul.core.enchant;

import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class LevelBasedEEIngredientCalculator implements EEIngredientCalculator {
    protected final EEIngredientCalculator fallback;
    protected final Map<Integer, EEIngredientCalculator> levels;

    public LevelBasedEEIngredientCalculator(EEIngredientCalculator fallback, Map<Integer, EEIngredientCalculator> levels) {
        this.fallback = fallback;
        this.levels = levels;
    }

    @NotNull
    @Override
    public List<CruxRecipeIngredient> calculateIngredients(Entity entity, int level, float quality) {
        EEIngredientCalculator calc = levels.getOrDefault(level, fallback);
        return calc == null ? List.of() : calc.calculateIngredients(entity, level, quality);
    }
}
