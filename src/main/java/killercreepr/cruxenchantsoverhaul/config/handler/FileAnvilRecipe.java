package killercreepr.cruxenchantsoverhaul.config.handler;

import com.google.common.reflect.TypeToken;
import killercreepr.crux.api.item.predicate.ItemPredicate;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import killercreepr.cruxconfig.config.common.FileContext;
import killercreepr.cruxconfig.config.common.FileRegistry;
import killercreepr.cruxconfig.config.common.element.FileElement;
import killercreepr.cruxconfig.config.common.element.FileObject;
import killercreepr.cruxconfig.config.common.handler.FileObjectHandler;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilIngredient;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipe;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRepairRecipe;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class FileAnvilRecipe implements FileObjectHandler<AnvilRecipe> {
    @Override
    public @NotNull FileElement serializeToFile(@NotNull FileContext<?> ctx, @NotNull AnvilRecipe recipe) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable AnvilRecipe deserializeFromFile(@NotNull FileContext<?> ctx, @NotNull FileElement e) {
        if(!(e instanceof FileObject o)) return null;
        Key key = ctx.getRegistry().deserializeFromFile(Key.class, o.get("key"));
        if(key==null) return null;
        return deserializeFromFile(ctx, e, key);
    }

    public @Nullable AnvilRecipe deserializeFromFile(@NotNull FileContext<?> ctx, @NotNull FileElement e, @NotNull Key key) {
        if(!(e instanceof FileObject o)) return null;
        FileRegistry registry = ctx.getRegistry();
        ItemPredicate first = registry.deserializeFromFile(ItemPredicate.class, o.get("first_ingredient"));
        if(first==null) return null;
        Collection<AnvilIngredient> second = registry.deserializeFromFile(
            new TypeToken<Collection<AnvilIngredient>>(){}.getType(), o.get("second_ingredient")
        );
        if(second==null) return null;
        return new AnvilRepairRecipe(key, first, second,
            registry.deserializeFromFile(NumberProvider.class, o.get("experience_cost")),
            registry.deserializeFromFile(NumberProvider.class, o.get("item_amount_cost")));
    }
}
