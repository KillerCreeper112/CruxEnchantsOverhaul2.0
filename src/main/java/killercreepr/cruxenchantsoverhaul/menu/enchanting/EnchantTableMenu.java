package killercreepr.cruxenchantsoverhaul.menu.enchanting;

import killercreepr.crux.api.data.DataExchange;
import killercreepr.crux.api.item.CruxItem;
import killercreepr.crux.api.text.tags.container.MergedTagContainer;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.util.CruxMath;
import killercreepr.cruxcrafting.api.crafting.context.CruxIngredientContext;
import killercreepr.cruxcrafting.api.crafting.ingredient.CruxRecipeIngredient;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEIngredientCalculator;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEnchant;
import killercreepr.cruxenchantsoverhaul.block.active.EnchantTableBlock;
import killercreepr.cruxenchantsoverhaul.enchanting.EnchantData;
import killercreepr.cruxenchantsoverhaul.enchanting.Enchanter;
import killercreepr.cruxenchantsoverhaul.item.EItems;
import killercreepr.cruxenchantsoverhaul.registries.EnchantsRegistries;
import killercreepr.cruxmenus.api.menu.container.MenuContainer;
import killercreepr.cruxmenus.api.menu.holder.MenuHolder;
import killercreepr.cruxmenus.api.menu.slot.Slot;
import killercreepr.cruxmenus.api.menu.slot.TempSlotted;
import killercreepr.cruxmenus.core.menu.ConfigMenu;
import killercreepr.cruxmenus.core.menu.slot.SimpleFixedSlot;
import killercreepr.cruxmenus.core.menu.slot.SimpleTempStoredSlot;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class EnchantTableMenu extends ConfigMenu implements EnchantingMenu, TempSlotted {
    protected final MenuContainer container = MenuContainer.createNew();
    public final Slot INPUT = new SimpleTempStoredSlot(this, 10) {
        @Override
        public @NotNull Integer getMaxStackSize() {
            return 1;
        }
    };

    public final Slot LAPIS = new SimpleTempStoredSlot(this, 11) {
        @Override
        public boolean mayPlace(@NotNull HumanEntity p, @Nullable ItemStack item) {
            return item==null || item.getType()==Material.LAPIS_LAZULI;
        }
        @Override
        public @NotNull ItemStack getSlottedItemReplacement() {
            return EItems.EMPTY_SLOT_LAPIS.buildItem();
        }
    };

    public final Slot BACK_PAGE = new SimpleFixedSlot(this, 52) {
        @Override
        public void onClick(@NotNull HumanEntity p, @NotNull InventoryClickEvent event) {
            super.onClick(p, event);
            if(page < 1) return;
            page = CruxMath.wrap(page-1, 0, getMaxPage());
            updateEnchantList();
        }
    };

    public final Slot NEXT_PAGE = new SimpleFixedSlot(this, 52) {
        @Override
        public void onClick(@NotNull HumanEntity p, @NotNull InventoryClickEvent event) {
            super.onClick(p, event);
            if(page >= getMaxPage()) return;
            page = CruxMath.wrap(page+1, 0, getMaxPage());
            updateEnchantList();
        }
    };

    public Slot buildIngredientSlot(int slot){
        return new SimpleTempStoredSlot(this, slot){
            @Override
            public boolean mayPlace(@NotNull HumanEntity p, @Nullable ItemStack item) {
                if(CruxItem.isEmpty(item)) return false;
                if(selectedEnchant == null) return false;
                if(selectedIngredients == null) return false;
                var ctx = CruxIngredientContext.ingredientContext(item);
                for(var ingredient : selectedIngredients){
                    if(ingredient.test(ctx)) return true;
                }
                return false;
            }
        };
    }

    protected final @NotNull EnchantTableBlock block;
    protected final @NotNull EnchantData enchantData;
    protected final @NotNull Enchanter enchanter;
    protected int page = 0;
    public EnchantTableMenu(@NotNull MenuHolder holder, @NotNull DataExchange info) {
        this(holder, info, null);
    }

    public EnchantTableMenu(@NotNull MenuHolder holder, @NotNull DataExchange info, @Nullable MergedTagContainer tags) {
        super(holder, info, tags);
        this.block = info().getOrThrow(EnchantTableBlock.class);
        this.enchantData = block.getEnchantData();
        this.enchanter = info().getOrThrow(Enchanter.class);
        addSlot(LAPIS);
        addSlot(INPUT);
        addSlot(BACK_PAGE);
        addSlot(NEXT_PAGE);

        getIngredientInputSlots().forEach(slot ->{
            addSlot(buildIngredientSlot(slot));
        });
        //update();

        //setGeneralInvClickAction((p,e) -> e.setCancelled(false));

        container.addOpenedMenu(this);
    }

    public int getMaxPage(){
        return Math.max((int)Math.ceil((double)currentEnchantList.size() / (double)getEnchantmentListSlots().size()) - 1, 0);
    }

    @Override
    public void onClose(@NotNull HumanEntity p) {
        super.onClose(p);
        container.onClosed(p, this);
    }

    @Override
    public void onRefresh() {
        this.refreshReconstruct();
        this.modules.refresh();
        this.setItems(this.holder);
        Crux.getServer().getScheduler().runTask(Crux.getMainPlugin(), task -> update());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        update();
    }

    public List<Integer> getEnchantmentListSlots(){
        return holder.info().getOrThrow("enchant_list_slots", List.class);
    }
    public List<Integer> getIngredientInputSlots(){
        return holder.info().getOrThrow("ingredient_input_slots", List.class);
    }

    public List<Integer> getSelectIngredientSlots(){
        return holder.info().getOrThrow("select_ingredients_indexes", List.class);
    }
    public Integer getSelectIconSlot(){
        return holder.info().getOrThrow("select_icon_index", Number.class).intValue();
    }

    public List<EEnchant> getAvailableEnchants(ItemStack item){
        List<EEnchant> list = new ArrayList<>();
        for (EEnchant ench : EnchantsRegistries.EENCHANT) {
            if(!ench.canEnchantItem(item)) continue;
            list.add(ench);
        }
        list.sort(Comparator.comparing(e -> e.key().value()));
        return list;
    }

    public ItemStack buildEnchantItem(EEnchant enchant){
        return CruxItem.wrap(enchant.getIcon())
            .editThis(crux ->{
                crux.addLoreFromString(
                    "",
                    "test"
                );
            })
            .item();
    }

    public Entity getViewer(){
        return info.getOrThrow("viewer", Entity.class);
    }

    public int getNextEnchantLevel(EEnchant enchant){
        ItemStack input = INPUT.getItem();
        if(input == null) return 0;
        int level = input.getEnchantmentLevel(enchant.enchantment());
        return level + 1;
    }

    public int getMaxEnchantLevel(EEnchant enchant){
        return enchant.maxLevel();
    }

    public void setSelectedEnchant(EEnchant selectedEnchant) {
        this.selectedEnchant = selectedEnchant;
        if(selectedEnchant == null){
            this.selectedIngredients = null;
            return;
        }
        this.selectedIngredients = selectedEnchant.ingredientCalculator().calculateIngredients(getViewer(), getNextEnchantLevel(selectedEnchant));
    }

    protected final List<EEnchant> currentEnchantList = new ArrayList<>();
    protected EEnchant selectedEnchant;
    protected List<CruxRecipeIngredient> selectedIngredients;
    public void updateEnchantList(){
        if(currentEnchantList.isEmpty()){
            if(selectedEnchant != null){
                setSelectedEnchant(null);
                refreshReconstruct();
            }
            for(Integer slot : getEnchantmentListSlots()) {
                slots.remove(slot);
                setItem(slot, null, true);
            }
            return;
        }

        if(selectedEnchant != null){
            reconstruct(buildSize(), holder.getRegistry().getFormat()
                    .deserialize(holder.info().getOrThrow("select_title", String.class)),
                true, true);
            for(Integer slot : getEnchantmentListSlots()){
                slots.remove(slot);
                setItem(slot, null, true);
            }

            EEnchant eEnchant = selectedEnchant;
            int selectIconSlot = getSelectIconSlot();
            setItem(selectIconSlot, buildEnchantItem(eEnchant), new SimpleFixedSlot(this, selectIconSlot){
                @Override
                public void onClick(@NotNull HumanEntity p, @NotNull InventoryClickEvent event) {
                    super.onClick(p, event);
                    setSelectedEnchant(null);
                    update();
                    CLICK.playFor(p);
                }
            }, true);

            int level = getNextEnchantLevel(selectedEnchant);
            List<CruxRecipeIngredient> ingredients = eEnchant.ingredientCalculator().calculateIngredients(
                getViewer(), level
            );
            int index = -1;
            for(Integer slot : getSelectIngredientSlots()){
                index++;
                if(index >= ingredients.size()) break;
                CruxRecipeIngredient ingredient = ingredients.get(index);
                setItem(slot, ingredient.getItemDisplay().getFirst(), true);
            }
            return;
        }
        refreshReconstruct();

        int index = -1;
        for(Integer slot : getEnchantmentListSlots()){
            slots.remove(slot);
            index++;
            if(index >= currentEnchantList.size()){
                setItem(slot, null, true);
                continue;
            }
            EEnchant eEnchant = currentEnchantList.get(index);
            setItem(slot, buildEnchantItem(eEnchant), new SimpleFixedSlot(this, slot){
                @Override
                public void onClick(@NotNull HumanEntity p, @NotNull InventoryClickEvent event) {
                    super.onClick(p, event);
                    setSelectedEnchant(eEnchant);
                    update();
                    CLICK.playFor(p);
                }
            }, true);
        }
    }

    public void updateInputData(){
        currentEnchantList.clear();
        ItemStack input = INPUT.getItem();
        if(!CruxItem.isEmpty(input)){
            currentEnchantList.addAll(getAvailableEnchants(input));
        }
    }

    public void update(){
        updateInputData();

        updateEnchantList();
    }

    @Override
    public @NotNull EnchantTableBlock getBlock() {
        return block;
    }

    @Override
    public boolean giveItemUponClose() {
        return !container.isOpening();
    }
}
