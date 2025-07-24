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
import java.util.function.Function;

public class AmountEEIngredientCalculator implements EEIngredientCalculator {
    public static int getAmount(CruxRecipeIngredient ingredient){
        if((ingredient instanceof SimpleRecipeIngredient recipe)){
            return recipe.getAmount();
        }
        if((ingredient instanceof SimpleWrappedKeyedRecipeIngredient recipe)){
            return recipe.getAmount();
        }
        if((ingredient instanceof SimpleWrappedRecipeIngredient recipe)){
            return recipe.getAmount();
        }
        return 0;
    }

    public static CruxRecipeIngredient editAmount(CruxRecipeIngredient ingredient, Function<Integer, Integer> amount){
        if((ingredient instanceof SimpleWrappedKeyedRecipeIngredient recipe)){
            return new SimpleWrappedKeyedRecipeIngredient(recipe.getIngredient(), amount.apply(recipe.getAmount()), recipe.key());
        }
        if((ingredient instanceof SimpleWrappedRecipeIngredient recipe)){
            return new SimpleWrappedRecipeIngredient(recipe.getIngredient(), amount.apply(recipe.getAmount()));
        }
        if((ingredient instanceof SimpleRecipeIngredient recipe)){
            List<ItemStack> newList = new ArrayList<>();
            int x = amount.apply(recipe.getAmount());
            recipe.getItemDisplays().forEach(item ->{
                item = item.clone();
                item.setAmount(x);
                newList.add(item);
            });
            return new SimpleRecipeIngredient(recipe.getPredicate(), x, newList);
        }
        Crux.logWarning(ingredient + " not supported");
        return ingredient;
    }

    protected final Collection<CruxRecipeIngredient> ingredients;
    protected final NumberProvider amount;

    public AmountEEIngredientCalculator(Collection<CruxRecipeIngredient> ingredients, NumberProvider amount) {
        this.ingredients = ingredients;
        this.amount = amount;
    }

    @NotNull
    @Override
    public List<CruxRecipeIngredient> calculateIngredients(Entity entity, int level, float quality) {
        List<CruxRecipeIngredient> list = new ArrayList<>();
        int amount = this.amount.sample(InputContext.inputContext(TagContainer.string().hook(entity)
            .add(Tag.parsed("level", level+""))
            .add(Tag.parsed("quality", quality+"")))).intValue();
        for (CruxRecipeIngredient ingredient : ingredients) {
            list.add(editAmount(ingredient, x -> amount));
        }
        return list;
    }
}
