package killercreepr.cruxenchantsoverhaul.util;

import killercreepr.crux.core.data.util.Pair;
import killercreepr.crux.core.util.CruxMath;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

public class EnchantUtils {
    public static @NotNull String format(@NotNull Enchantment ench){
        return PlainTextComponentSerializer.plainText().serialize(ench.description());
    }

    public static @NotNull String format(@NotNull Enchantment ench, int level){
        String lvl = level > 1 || ench.getMaxLevel() > 1 ? (" " + CruxMath.numeral(level)) : "";
        return format(ench) + lvl;
    }

    public static @NotNull String format(@NotNull Pair<Enchantment, Integer> pair){
        return format(pair.getFirst(), pair.getSecond());
    }
}
