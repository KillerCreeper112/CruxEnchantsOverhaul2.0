package killercreepr.cruxenchantsoverhaul.config.handler;

import killercreepr.crux.api.item.dynamic.DynamicItem;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import killercreepr.cruxconfig.config.common.FileContext;
import killercreepr.cruxconfig.config.common.element.FileElement;
import killercreepr.cruxconfig.config.common.element.FileObject;
import killercreepr.cruxconfig.config.common.handler.FileObjectHandler;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEnchant;
import killercreepr.cruxenchantsoverhaul.core.enchant.SimpleEEnchant;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileEEnchant implements FileObjectHandler<EEnchant> {
    @Override
    public @NotNull FileElement serializeToFile(@NotNull FileContext<?> ctx, @NotNull EEnchant recipe) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable EEnchant deserializeFromFile(@NotNull FileContext<?> ctx, @NotNull FileElement e) {
        if(!(e instanceof FileObject o)) return null;
        var r = ctx.getRegistry();
        Key enchant = r.deserializeFromFile(Key.class, o.get("enchant"));
        DynamicItem icon = r.deserializeFromFile(DynamicItem.class, o.get("icon"));
        EEIngredientCalculator ingredientCalculator = r.deserializeFromFile(EEIngredientCalculator.class, o.get("ingredients"));
        String description = r.deserializeFromFile(String.class, o.get("description"));
        return new SimpleEEnchant(
            enchant, description, null, icon, ingredientCalculator,
            r.deserializeFromFile(NumberProvider.class, o.get("required_power")),
            r.deserializeFromFile(NumberProvider.class, o.get("required_level")),
            r.deserializeFromFile(NumberProvider.class, o.get("required_exp")),
            r.deserializeFromFile(NumberProvider.class, o.get("required_lapis"))
        );
    }
}
