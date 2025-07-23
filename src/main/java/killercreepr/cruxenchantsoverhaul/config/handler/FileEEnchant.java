package killercreepr.cruxenchantsoverhaul.config.handler;

import killercreepr.crux.api.item.dynamic.DynamicItem;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import killercreepr.crux.core.valueproviders.number.ConstantNumber;
import killercreepr.crux.core.valueproviders.number.EquationNumber;
import killercreepr.cruxconfig.config.common.FileContext;
import killercreepr.cruxconfig.config.common.FileRegistry;
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
    public static NumberProvider defaultRequiredPower;
    public static NumberProvider defaultRequiredLevel;
    public static NumberProvider defaultRequiredExp;
    public static NumberProvider defaultRequiredLapis;
    public static NumberProvider defaultIngredientAmount;

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
            equation("required_power", r, o, defaultRequiredPower),
            equation("required_level", r, o, defaultRequiredLevel),
            equation("required_exp", r, o, defaultRequiredExp),
            equation("required_lapis", r, o, defaultRequiredLapis),
            r.deserializeFromFileOrDefault(Double.class, o.get("quality"), 1D)
        );
    }

    public static NumberProvider equation(String id, FileRegistry r, FileObject o, NumberProvider defaultValue){
        NumberProvider got = r.deserializeFromFile(NumberProvider.class, o.get(id));
        if(got != null) return got;
        String addon = o.getObject(String.class, id + "_addon");
        if(addon == null) return defaultValue;
        if(defaultValue instanceof EquationNumber equationNumber){
            return new EquationNumber(addon + equationNumber.getEquation());
        }
        return defaultValue;
    }
}
