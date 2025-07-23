package killercreepr.cruxenchantsoverhaul.config;

import killercreepr.crux.api.data.Holder;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.plugin.CruxPlugin;
import killercreepr.cruxconfig.config.bukkit.file.Cfg;
import killercreepr.cruxconfig.config.bukkit.file.CruxConfig;
import killercreepr.cruxconfig.config.bukkit.value.CfgValue;
import killercreepr.cruxconfig.config.bukkit.value.CommonValue;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipe;
import killercreepr.cruxenchantsoverhaul.registries.EnchantsRegistries;
import killercreepr.cruxenchantsoverhaul.values.DefaultValues;
import killercreepr.cruxenchantsoverhaul.values.ValuesProvider;
import net.kyori.adventure.key.Key;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class Config extends Cfg implements ValuesProvider {
    public final CfgValue<Map<Key, Integer>> DEFAULT_MAGIC_CAPACITY = new CommonValue<>(DefaultValues.DEFAULT_MAGIC_CAPACITY.value()){};
    public final CfgValue<List<String>> MAGIC_CAPACITY_FORMAT = new CommonValue<>(DefaultValues.MAGIC_CAPACITY_FORMAT.value()){};
    public final CfgValue<Collection<AnvilRecipe>> ANVIL_REPAIR_RECIPES = new CommonValue<>(){};
    public final CfgValue<Map<Key, Integer>> MAGIC_CAPACITY_USAGE_PER_LEVEL = new CommonValue<>(){};
    public Config(@NotNull Plugin plugin, @NotNull String path) {
        super(plugin, path);
    }

    public Config(@NotNull File file) {
        super(file);
    }

    public Config(@NotNull CruxConfig cfg) {
        super(cfg);
    }

    @NotNull
    @Override
    public Holder<Map<Key, Integer>> DEFAULT_MAGIC_CAPACITY() {
        return DEFAULT_MAGIC_CAPACITY;
    }

    @NotNull
    @Override
    public Holder<List<String>> MAGIC_CAPACITY_FORMAT() {
        return MAGIC_CAPACITY_FORMAT;
    }

    @NotNull
    @Override
    public Holder<Map<Key, Integer>> MAGIC_CAPACITY_USAGE_PER_LEVEL() {
        return MAGIC_CAPACITY_USAGE_PER_LEVEL;
    }

    @Override
    public void reload(@NotNull CruxPlugin plugin) {
        setup();
        ANVIL_REPAIR_RECIPES.valueOr(Set.of()).forEach(recipe ->{
            EnchantsRegistries.ANVIL_RECIPES.register(recipe);
            Crux.log(Level.INFO, "Registered anvil repair recipe: " + recipe.key());
        });
    }
}
