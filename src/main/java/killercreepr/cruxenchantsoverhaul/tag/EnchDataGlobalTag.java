package killercreepr.cruxenchantsoverhaul.tag;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import killercreepr.crux.api.text.context.TextParserContext;
import killercreepr.crux.api.text.resolver.StringListResolver;
import killercreepr.crux.core.text.format.FormatArgs;
import killercreepr.cruxenchantsoverhaul.CruxEnchantsOverhaul;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class EnchDataGlobalTag implements StringListResolver {
    @NotNull
    @Override
    public String identifier() {
        return "enchants_overhaul/ench_usage_list";
    }

    @Nullable
    @Override
    public List<String> resolve(@NotNull FormatArgs args, @NotNull TextParserContext ctx) {
        List<EnchDataTag.Data> list = new ArrayList<>();
        for(Enchantment ench : RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT)){
            int value = CruxEnchantsOverhaul.inst().values().MAGIC_CAPACITY_USAGE_PER_LEVEL().valueOr(Map.of())
                .getOrDefault(ench.key(), 1);
            if(value == 1) continue;
            list.add(new EnchDataTag.Data(ench.key()));
        }
        list.sort(Comparator.comparing(EnchDataTag.Data::enchantUsage).reversed());

        List<String> strings = new ArrayList<>();
        for(var data : list){
            strings.add("<yellow>" + data.name() + "<gray>: <gold>" + data.enchantUsage());
        }
        return strings;
    }
}
