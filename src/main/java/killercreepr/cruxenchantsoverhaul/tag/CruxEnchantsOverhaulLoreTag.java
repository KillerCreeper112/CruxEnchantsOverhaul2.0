package killercreepr.cruxenchantsoverhaul.tag;

import killercreepr.crux.api.data.Holder;
import killercreepr.crux.api.text.format.FormatPrefix;
import killercreepr.crux.api.text.hook.ObjectTag;
import killercreepr.crux.api.text.tags.TagParser;
import killercreepr.crux.core.text.container.StringListTagContainer;
import killercreepr.crux.core.text.resolver.Tag;
import killercreepr.cruxenchantsoverhaul.item.EEItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CruxEnchantsOverhaulLoreTag implements ObjectTag<ItemStack> {
    protected final @NotNull Holder<List<String>> loreFormat;
    public CruxEnchantsOverhaulLoreTag(@NotNull Holder<List<String>> loreFormat) {
        this.loreFormat = loreFormat;
    }

    public @NotNull Holder<List<String>> getLoreFormat() {
        return loreFormat;
    }

    @Override
    public @NotNull Class<ItemStack> getObjectType() {
        return ItemStack.class;
    }

    @Override
    public @NotNull FormatPrefix defaultPrefix() {
        return FormatPrefix.simple("cruxenchantsoverhaul_");
    }

    @Override
    public @Nullable StringListTagContainer requestStringLists(@NotNull ItemStack item, @NotNull TagParser tags) {
        return new StringListTagContainer(tags)
            .add(Tag.stringList("blight", (args, ctx) ->{
                List<String> format = loreFormat.value();
                if(format==null) return null;

                EEItem EEItem = new EEItem(item);
                Integer blight = EEItem.getMagicCapacity();

                if(blight==null) return null;
                if(blight == 0){
                    return List.of("<dark_gray><latinfont:unenchantable>");
                }
                List<String> list = new ArrayList<>();
                for(String s : format){
                    list.add(ctx.getFormat().deserializeString(s));
                }
                return list;
            }))
            ;
    }
}
