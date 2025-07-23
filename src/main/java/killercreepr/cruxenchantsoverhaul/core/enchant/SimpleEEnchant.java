package killercreepr.cruxenchantsoverhaul.core.enchant;

import killercreepr.crux.api.item.dynamic.DynamicItem;
import killercreepr.crux.api.text.context.TextParserContext;
import killercreepr.crux.core.util.CruxString;
import killercreepr.cruxenchants.api.enchant.ApplicableItemGroup;
import killercreepr.cruxenchants.api.enchant.CruxEnchant;
import killercreepr.cruxenchants.core.enchant.SimpleCruxEnchant;
import killercreepr.cruxenchants.core.registries.CruxEnchantRegistries;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEnchant;
import killercreepr.cruxenchantsoverhaul.registries.EnchantsRegistries;
import net.kyori.adventure.key.Key;
import org.bukkit.inventory.ItemStack;

public class SimpleEEnchant extends SimpleCruxEnchant implements EEnchant {
    protected final DynamicItem icon;
    protected final EEIngredientCalculator ingredientCalculator;
    public SimpleEEnchant(Key key, String description, ApplicableItemGroup applicableItemGroup, DynamicItem icon, EEIngredientCalculator ingredientCalculator) {
        super(key, description, applicableItemGroup);
        this.icon = icon;
        this.ingredientCalculator = ingredientCalculator;
    }

    @Override
    public String description() {
        if(description == null){
            CruxEnchant cruxEnchant = CruxEnchantRegistries.CRUX_ENCHANT.get(key);
            if(cruxEnchant != null && cruxEnchant != this) return cruxEnchant.description();
        }
        return super.description();
    }

    @Override
    public ItemStack getIcon() {
        return icon.build(TextParserContext.empty())
            .customName("<!i><white>" + displayName())
            .editThis(crux ->{
                crux.addLoreFromString(
                    "<white><latinfont:Enchant Usage>: <gold>" + enchantUsage(),
                    "<white><latinfont:Max Level>: <gold>" + maxLevel(),
                    ""
                );
                if(description() != null){
                    crux.addLoreFromString(CruxString.buildDescription(
                        description(), CruxString.DEFAULT_DESCRIPTION_MAX_LENGTH,
                        s -> "<gray>" + s
                    ));
                }
            })
            .item();
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return enchantment().canEnchantItem(item);
    }

    @Override
    public boolean conflictsWith(EEnchant enchant) {
        return enchantment().conflictsWith(enchant.enchantment());
    }

    @Override
    public EEIngredientCalculator ingredientCalculator() {
        return ingredientCalculator;
    }
}
