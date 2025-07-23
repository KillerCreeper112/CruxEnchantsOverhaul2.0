package killercreepr.cruxenchantsoverhaul.core.enchant;

import killercreepr.crux.api.text.context.InputContext;
import killercreepr.crux.api.text.tags.container.TagContainer;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.text.resolver.Tag;
import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxcrafting.core.crafting.ingredient.SimpleRecipeIngredient;
import killercreepr.cruxcrafting.core.crafting.ingredient.SimpleWrappedKeyedRecipeIngredient;
import killercreepr.cruxcrafting.core.crafting.ingredient.SimpleWrappedRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AmountEEIngredientCalculator implements EEIngredientCalculator {
    protected final Collection<CruxRecipeIngredient> ingredients;
    protected final NumberProvider amount;

    public AmountEEIngredientCalculator(Collection<CruxRecipeIngredient> ingredients, NumberProvider amount) {
        this.ingredients = ingredients;
        this.amount = amount;
    }

    @NotNull
    @Override
    public List<CruxRecipeIngredient> calculateIngredients(Entity entity, int level) {
        List<CruxRecipeIngredient> list = new ArrayList<>();
        int amount = this.amount.sample(InputContext.inputContext(TagContainer.string().hook(entity)
            .add(Tag.parsed("level", level+"")))).intValue();
        for (CruxRecipeIngredient ingredient : ingredients) {
            if((ingredient instanceof SimpleWrappedKeyedRecipeIngredient recipe)){
                recipe = new SimpleWrappedKeyedRecipeIngredient(recipe.getIngredient(), amount, recipe.key());
                list.add(recipe);
                continue;
            }
            if((ingredient instanceof SimpleWrappedRecipeIngredient recipe)){
                recipe = new SimpleWrappedRecipeIngredient(recipe.getIngredient(), amount);
                list.add(recipe);
                continue;
            }
            if((ingredient instanceof SimpleRecipeIngredient recipe)){
                List<ItemStack> newList = new ArrayList<>();
                recipe.getItemDisplays().forEach(item ->{
                    item = item.clone();
                    item.setAmount(amount);
                    newList.add(item);
                });
                recipe = new SimpleRecipeIngredient(recipe.getPredicate(), amount, newList);
                list.add(recipe);
                continue;
            }
            Crux.logWarning(ingredient + " not supported");
        }
        return list;
    }
}
