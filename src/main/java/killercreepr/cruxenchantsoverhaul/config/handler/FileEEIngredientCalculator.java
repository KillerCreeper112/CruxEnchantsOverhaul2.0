package killercreepr.cruxenchantsoverhaul.config.handler;

import com.google.common.reflect.TypeToken;
import killercreepr.cruxconfig.config.common.FileContext;
import killercreepr.cruxconfig.config.common.element.FileElement;
import killercreepr.cruxconfig.config.common.element.FileObject;
import killercreepr.cruxconfig.config.common.handler.FileObjectHandler;
import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.core.enchant.AllOfEEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.core.enchant.AmountEEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.core.enchant.LevelBasedEEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.core.enchant.SimpleEEIngredientCalculator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FileEEIngredientCalculator implements FileObjectHandler<EEIngredientCalculator> {
    @Override
    public @NotNull FileElement serializeToFile(@NotNull FileContext<?> ctx, @NotNull EEIngredientCalculator recipe) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable EEIngredientCalculator deserializeFromFile(@NotNull FileContext<?> ctx, @NotNull FileElement e) {
        if(!(e instanceof FileObject o)) return null;
        var r = ctx.getRegistry();
        String type = r.deserializeFromFile(String.class, o.get("type"));

        if(type == null){
            if(o.has("children")) type = "all_of";
            else if(o.has("amount")) type = "amount";
            else type = "simple";
        }

        switch (type.toLowerCase()){
            case "all_of" ->{
                return new AllOfEEIngredientCalculator(
                    r.deserializeFromFile(
                        new TypeToken<Collection<EEIngredientCalculator>>(){}.getType(),
                        o.get("children")
                    )
                );
            }
            case "level_based" ->{
                return new LevelBasedEEIngredientCalculator(
                    r.deserializeFromFile(EEIngredientCalculator.class, o.get("default")),
                    r.deserializeFromFile(
                        new TypeToken<Map<Integer,EEIngredientCalculator>>(){}.getType(),
                        o.get("levels")
                    )
                );
            }
            case "amount" ->{
                return new AmountEEIngredientCalculator(
                    r.deserializeFromFile(
                        new TypeToken<Collection<CruxRecipeIngredient>>(){}.getType(),
                        o.get("ingredients")
                    ),
                    FileEEnchant.equation("amount", r, o, FileEEnchant.defaultIngredientAmount)
                );
            }
            case "simple" ->{
                return new SimpleEEIngredientCalculator(
                    r.deserializeFromFile(
                        new TypeToken<List<CruxRecipeIngredient>>(){}.getType(),
                        o.get("ingredients")
                    )
                );
            }
        }
        return null;
    }
}
