package killercreepr.cruxenchantsoverhaul.item;

import killercreepr.crux.api.text.tags.container.MergedTagContainer;
import killercreepr.cruxitems.api.item.CruxedItem;
import killercreepr.cruxitems.core.item.plugin.GenericPluginItem;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GUIItem extends GenericPluginItem {
    protected final @NotNull Material material;
    protected final @Nullable Key customModelData;

    public GUIItem(@NotNull Key key, @NotNull Material material, @Nullable Key customModelData) {
        super(key);
        this.material = material;
        this.customModelData = customModelData;
    }

    /*@Override
    public @NotNull CruxItem buildGeneric(@Nullable Entity entity, @Nullable MergedTagContainer mergedTagContainer) {
        return new CruxItem(material)
            .customModelData(customModelData)
            .editMeta(meta -> meta.setHideTooltip(true))
            ;
    }*/

    @Override
    public @NotNull CruxedItem build(@Nullable Entity entity, @Nullable MergedTagContainer mergedTagContainer) {
        return (CruxedItem) CruxedItem.cruxed(material)
            .itemModel(customModelData)
            .editMeta(meta -> meta.setHideTooltip(true))
            ;
    }
}
