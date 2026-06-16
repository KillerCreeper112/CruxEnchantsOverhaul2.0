package killercreepr.cruxenchantsoverhaul.config;

import killercreepr.crux.api.data.Holder;
import killercreepr.crux.core.plugin.CruxPlugin;
import killercreepr.cruxconfig.config.bukkit.file.Cfg;
import killercreepr.cruxconfig.config.bukkit.file.CruxConfig;
import killercreepr.cruxconfig.config.bukkit.value.CfgValue;
import killercreepr.cruxconfig.config.bukkit.value.CommonValue;
import killercreepr.cruxconfig.config.bukkit.value.NumCfgValue;
import killercreepr.cruxenchantsoverhaul.values.DefaultValues;
import killercreepr.cruxenchantsoverhaul.values.ValuesProvider;
import net.kyori.adventure.key.Key;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Config extends Cfg implements ValuesProvider {
    public final CfgValue<Map<Key, Integer>> DEFAULT_MAGIC_CAPACITY = new CommonValue<>(DefaultValues.DEFAULT_MAGIC_CAPACITY.value()){};
    public final CfgValue<List<String>> MAGIC_CAPACITY_FORMAT = new CommonValue<>(DefaultValues.MAGIC_CAPACITY_FORMAT.value()){};
    public final CfgValue<Map<Key, Integer>> MAGIC_CAPACITY_USAGE_PER_LEVEL = new CommonValue<>(){};
    public final NumCfgValue MAX_ENCHANTING_TABLE_POWER = new NumCfgValue(300);
    public final NumCfgValue POWER_PER_BOOKSHELF = new NumCfgValue(5);
    public final NumCfgValue POWER_PER_BOOK_IN_CHISELED_BOOKSHELF = new NumCfgValue(1);
    public final NumCfgValue POWER_PER_ENCHANTED_BOOK_IN_CHISELED_BOOKSHELF = new NumCfgValue(2);
    public final NumCfgValue POWER_PER_ENCHANT_ON_ENCHANTED_BOOK_PROGRESS = new NumCfgValue(3);
    public final CfgValue<String> CUSTOM_ENCHANTING_TABLE_PERMISSION = new CommonValue<>(){};

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
    }
}
