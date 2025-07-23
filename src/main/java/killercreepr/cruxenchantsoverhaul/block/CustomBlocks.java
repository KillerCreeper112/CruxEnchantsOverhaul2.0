package killercreepr.cruxenchantsoverhaul.block;

import killercreepr.crux.api.component.DataComponentHandler;
import killercreepr.crux.core.Crux;
import killercreepr.cruxblocks.api.block.active.ActiveCruxBlock;
import killercreepr.cruxblocks.api.block.group.CruxBlockGroup;
import killercreepr.cruxblocks.core.block.SimpleBlock;
import killercreepr.cruxblocks.core.block.group.SingularBlockGroup;
import killercreepr.cruxblocks.core.block.texture.MaterialTextureData;
import killercreepr.cruxblocks.core.registries.CruxBlocksRegistries;
import killercreepr.cruxenchantsoverhaul.block.active.EnchantTableBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class CustomBlocks {
    public static void register(){}
    public static final CruxBlockGroup ENCHANT_TABLE = CruxBlocksRegistries.BLOCK.registerGroup(new SingularBlockGroup(
        new SimpleBlock(Crux.key("enchant_table"), new MaterialTextureData(Material.ENCHANTING_TABLE), null){
            @Override
            public @NotNull ActiveCruxBlock createActive(@NotNull Block block) {
                return new EnchantTableBlock(block, this);
            }
        }, DataComponentHandler.simple()
    ));
}
