package killercreepr.cruxenchantsoverhaul.tag;

import killercreepr.crux.api.text.format.FormatPrefix;
import killercreepr.crux.api.text.hook.ObjectTag;
import killercreepr.crux.api.text.tags.TagParser;
import killercreepr.crux.core.text.container.StringTagContainer;
import killercreepr.crux.core.text.resolver.Tag;
import killercreepr.cruxenchantsoverhaul.item.EEItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemStackTags implements ObjectTag<ItemStack> {
    @Override
    public @NotNull Class<ItemStack> getObjectType() {
        return ItemStack.class;
    }

    @Override
    public @NotNull FormatPrefix defaultPrefix() {
        return FormatPrefix.simple("item_");
    }

    @Override
    public @Nullable StringTagContainer requestStrings(@NotNull ItemStack item, @NotNull TagParser tags) {
        EEItem EEItem = new EEItem(item);
        return new StringTagContainer(tags)
            .add(Tag.string("blight", (args, ctx) ->{
                return EEItem.getMagicCapacity() + "";
            }))
            .add(Tag.string("total_enchants", (args, ctx) ->{
                return EEItem.getMagicUsage() + "";
            }))
            ;
    }
}
