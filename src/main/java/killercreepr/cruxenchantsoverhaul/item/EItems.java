package killercreepr.cruxenchantsoverhaul.item;

import killercreepr.crux.api.text.tags.container.MergedTagContainer;
import killercreepr.crux.core.Crux;
import killercreepr.cruxitems.api.item.CruxedItem;
import killercreepr.cruxitems.api.item.plugin.PluginItem;
import killercreepr.cruxitems.core.item.plugin.GenericPluginItem;
import killercreepr.cruxitems.core.registries.CruxItemRegistries;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EItems {
    private static final String slotSprites = "gui/sprites/container/slot/";
    public static final PluginItem EMPTY_SLOT_PICKAXE = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("empty_slot_pickaxe"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key(slotSprites + "pickaxe")
    ));

    public static final PluginItem MENU_NOTHING = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("nothing"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("nothing")
    ));

    public static final PluginItem MENU_BLANK = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("menu_blank"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/blank")
    ));

    public static final PluginItem EMPTY_SLOT_LAPIS = CruxItemRegistries.ITEMS.register(new GenericPluginItem(
        Crux.key("empty_slot_lapis")
    ) {
        @NotNull
        @Override
        public CruxedItem build(@Nullable Entity entity, @Nullable MergedTagContainer mergedTagContainer) {
            return (CruxedItem) CruxedItem.cruxed(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .itemModel(Crux.key(slotSprites + "lapis"))
                .itemName("<white>Place lapis lazuli to")
                .addLoreFromString(
                    "<white>enchant!",
                    "<white>Or place an enchanted book",
                    "<white>to upgrade!"
                )
                ;
        }
    });

    public static final PluginItem ENCHANT_TABLE_ON_1 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_on_1"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_on_1")
    ));

    public static final PluginItem ENCHANT_TABLE_ON_2 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_on_2"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_on_2")
    ));

    public static final PluginItem ENCHANT_TABLE_ON_3 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_on_3"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_on_3")
    ));

    public static final PluginItem ENCHANT_TABLE_LEVEL_1 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_level_1"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_level_1")
    ));

    public static final PluginItem ENCHANT_TABLE_LEVEL_2 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_level_2"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_level_2")
    ));

    public static final PluginItem ENCHANT_TABLE_LEVEL_3 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_level_3"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_level_3")
    ));

    public static final PluginItem ENCHANT_SELECTOR = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_selector"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchant_selector")
    ));


    public static final PluginItem ARROW_DOWN = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("arrow_down"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/arrow_down")
    ));

    public static final PluginItem BACK_DOWN = CruxItemRegistries.ITEMS.register(new GenericPluginItem(
        Crux.key("back_down")
    ) {
        @Override
        public @NotNull CruxedItem build(@Nullable Entity entity, @Nullable MergedTagContainer mergedTagContainer) {
            return (CruxedItem) CruxedItem.cruxed(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .itemModel(Crux.key("gui/arrow_down"))
                .itemName("<gray>Back");
        }
    });


    public static final PluginItem ENCHANT_TABLE_OFF_1 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_off_1"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_off_1")
    ));

    public static final PluginItem ENCHANT_TABLE_OFF_2 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_off_2"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_off_2")
    ));

    public static final PluginItem ENCHANT_TABLE_OFF_3 = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("enchant_table_off_3"),
        Material.LIGHT_GRAY_STAINED_GLASS_PANE,
        Crux.key("gui/enchanting_table_off_3")
    ));

    public static final PluginItem CHECKMARK = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("checkmark"),
        Material.STRUCTURE_VOID,
        Crux.key("gui/checkmark")
    ));

    public static final PluginItem X = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("x"),
        Material.BARRIER,
        Crux.key("gui/x")
    ));

    public static final PluginItem CHECKMARK_BUTTON = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("checkmark"),
        Material.STRUCTURE_VOID,
        Crux.key("gui/checkmark")
    ));

    public static final PluginItem X_BUTTON = CruxItemRegistries.ITEMS.register(new GUIItem(
        Crux.key("x"),
        Material.BARRIER,
        Crux.key("gui/x_button")
    ));
}
