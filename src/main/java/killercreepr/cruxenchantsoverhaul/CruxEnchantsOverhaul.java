package killercreepr.cruxenchantsoverhaul;

import com.google.common.reflect.TypeToken;
import killercreepr.crux.api.data.DataExchange;
import killercreepr.crux.api.text.tags.container.MergedTagContainer;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.plugin.CruxPlugin;
import killercreepr.crux.core.plugin.module.StandardModules;
import killercreepr.crux.core.registries.CruxRegistries;
import killercreepr.cruxconfig.config.bukkit.file.BukkitDataFile;
import killercreepr.cruxconfig.config.bukkit.file.CruxFolder;
import killercreepr.cruxconfig.config.common.FileContext;
import killercreepr.cruxconfig.config.common.base.parsed.FileParsedObjectHandler;
import killercreepr.cruxconfig.config.common.element.FileElement;
import killercreepr.cruxconfig.config.common.file.DataFile;
import killercreepr.cruxconfig.config.registry.CfgRegistries;
import killercreepr.cruxcore.CruxCore;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipe;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEnchant;
import killercreepr.cruxenchantsoverhaul.block.CustomBlocks;
import killercreepr.cruxenchantsoverhaul.component.EnchantComponents;
import killercreepr.cruxenchantsoverhaul.config.CfgHook;
import killercreepr.cruxenchantsoverhaul.config.Config;
import killercreepr.cruxenchantsoverhaul.config.handler.FileAnvilRecipe;
import killercreepr.cruxenchantsoverhaul.config.handler.FileAnvilRepairIngredient;
import killercreepr.cruxenchantsoverhaul.config.handler.FileEEnchant;
import killercreepr.cruxenchantsoverhaul.enchanting.EEnchanter;
import killercreepr.cruxenchantsoverhaul.enchanting.Enchanter;
import killercreepr.cruxenchantsoverhaul.item.CfgMagicCapacityHandler;
import killercreepr.cruxenchantsoverhaul.item.MagicCapacityHandler;
import killercreepr.cruxenchantsoverhaul.listener.AnvilListener;
import killercreepr.cruxenchantsoverhaul.listener.CustomObjectiveListener;
import killercreepr.cruxenchantsoverhaul.menu.enchanting.EnchantTableMenu;
import killercreepr.cruxenchantsoverhaul.registries.EnchantsRegistries;
import killercreepr.cruxenchantsoverhaul.tag.CruxEnchantsOverhaulLoreTag;
import killercreepr.cruxenchantsoverhaul.tag.EnchDataGlobalTag;
import killercreepr.cruxenchantsoverhaul.tag.EnchDataTag;
import killercreepr.cruxenchantsoverhaul.tag.ItemStackTags;
import killercreepr.cruxenchantsoverhaul.values.DefaultValues;
import killercreepr.cruxenchantsoverhaul.values.ValuesProvider;
import killercreepr.cruxmenus.api.menu.holder.MenuHolder;
import killercreepr.cruxmenus.core.menu.ConfigMenu;
import killercreepr.cruxmenus.core.menu.holder.SimpleMenuHolder;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CruxEnchantsOverhaul extends CruxPlugin {
    private static CruxEnchantsOverhaul instance;
    public static CruxEnchantsOverhaul inst(){ return instance; }

    protected ValuesProvider values;

    public ValuesProvider values() {
        return values;
    }

    public void values(@NotNull ValuesProvider values) {
        this.values = values;
    }

    protected Enchanter enchanter;
    protected MagicCapacityHandler magicCapacityHandler;

    public MagicCapacityHandler getMagicCapacityHandler() {
        return magicCapacityHandler;
    }

    public void setBlightHandler(MagicCapacityHandler magicCapacityHandler) {
        this.magicCapacityHandler = magicCapacityHandler;
    }

    public Enchanter getEnchanter() {
        return enchanter;
    }

    public void setEnchanter(Enchanter enchanter) {
        this.enchanter = enchanter;
    }

    @Override
    public void onLoad() {
        instance = this;
        super.onLoad();
        Crux.tags().register(
            new ItemStackTags(),
            new EnchDataTag()
        );
        Crux.format().globalStringListResolvers().register(new EnchDataGlobalTag());
        EnchantComponents.register();
        if(CruxRegistries.MODULES.containsKey(StandardModules.CRUX_CONFIGS)){
            CfgRegistries.SIMPLE_REGISTRY.forEach(registry ->{
                registry.registerFileHandler(AnvilRecipe.class, new FileAnvilRecipe());
                registry.registerFileHandler(AnvilIngredient.class, new FileAnvilRepairIngredient());
            });
        }

        CfgRegistries.SIMPLE_REGISTRY.forEach(registry ->{
            registry.getParsedObjectRegistry().register(new FileParsedObjectHandler<MenuHolder>() {
                @Override
                public @NotNull Key key() {
                    return Crux.key("enchanting_table_menu");
                }

                @Override
                public int getPriority() {
                    return 0;
                }

                @Override
                public @NotNull Class<MenuHolder> getTargetType() {
                    return MenuHolder.class;
                }

                @Override
                public @Nullable MenuHolder parse(@NotNull FileElement element, @NotNull FileContext<?> ctx,
                                                  @NotNull MenuHolder original, @Nullable MenuHolder current) {
                    if(current==null || !current.key().namespace().equals(Crux.NAMESPACE)) return null;

                    switch (current.key().value()){
                        case "enchanting_table" ->{
                            return new SimpleMenuHolder(current.key(), current.getTitle(), current.getSize(), current.getItems(), current.info(), current.getModules()){
                                @Override
                                public @NotNull ConfigMenu createMenu(@NotNull DataExchange data, @Nullable MergedTagContainer tags) {
                                    return new EnchantTableMenu(this, data, tags);
                                }
                            };
                        }
                    }
                    return current;
                }
            });
        });
        if(CruxRegistries.MODULES.containsKey(StandardModules.CRUX_CONFIGS)){
            CfgHook.load();
        }

        CustomBlocks.register();

        Crux.tags().register(
            new CruxEnchantsOverhaulLoreTag(() -> values.MAGIC_CAPACITY_FORMAT().value())
        );
    }

    @Override
    public void enabled() {

        registerListeners(
            new AnvilListener()
        );
        if(CruxRegistries.MODULES.containsKey(StandardModules.CRUX_ADVANCEMENTS)){
            registerListeners(
                new CustomObjectiveListener()
            );
        }

        if(CruxRegistries.MODULES.containsKey(StandardModules.CRUX_CONFIGS)){
            values(new Config(this, "config"));
        }else{
            values(new DefaultValues());
        }
        setBlightHandler(new CfgMagicCapacityHandler(values));
        setEnchanter(new EEnchanter(magicCapacityHandler));

        super.enabled();
    }

    @Override
    public void reload() {
        super.reload();
        values.reload(this);
        CruxCore.inst().cruxMenus().menuRegistry().loadConfiguration(
            new CruxFolder(this, "menus").file()
        );

        DataFile eeCfg = BukkitDataFile.parseFromGeneralPath(CruxFolder.file(this, "eenchants"));
        if(eeCfg != null){
            FileEEnchant.defaultRequiredLevel = eeCfg.deserialize("default_required_level", NumberProvider.class);
            FileEEnchant.defaultRequiredPower = eeCfg.deserialize("default_required_power", NumberProvider.class);
            FileEEnchant.defaultRequiredExp = eeCfg.deserialize("default_required_exp", NumberProvider.class);
            FileEEnchant.defaultRequiredLapis = eeCfg.deserialize("default_required_lapis", NumberProvider.class);
            FileEEnchant.defaultIngredientAmount = eeCfg.deserialize("default_ingredient_amount", NumberProvider.class);

            Collection<EEnchant> list = eeCfg.deserialize("values", new TypeToken<Collection<EEnchant>>(){}.getType());
            if(list != null){
                list.forEach(ee ->{
                    EnchantsRegistries.EENCHANT.register(ee);
                    Crux.logInfo("Registered EEnchant: " + ee.key());
                });
            }
            eeCfg.close();
        }
    }
}
