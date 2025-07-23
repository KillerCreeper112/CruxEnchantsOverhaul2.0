package killercreepr.cruxenchantsoverhaul.core.enchant;

import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllOfEEIngredientCalculator implements EEIngredientCalculator {
    protected final Collection<EEIngredientCalculator> children;

    public AllOfEEIngredientCalculator(Collection<EEIngredientCalculator> children) {
        this.children = children;
    }

    @NotNull
    @Override
    public List<CruxRecipeIngredient> calculateIngredients(Entity entity, int level, float quality) {
        List<CruxRecipeIngredient> list = new ArrayList<>();
        children.forEach(calc ->{
            list.addAll(calc.calculateIngredients(entity, level, quality));
        });
        return list;
    }
}
