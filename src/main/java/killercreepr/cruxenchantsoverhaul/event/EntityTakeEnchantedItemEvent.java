package killercreepr.cruxenchantsoverhaul.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EntityTakeEnchantedItemEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Block block;
    private final ItemStack item;
    private boolean cancelled;
    //private final EnchantOffer offer;
    private final Entity enchanter;
    private final boolean selectedEnchants;

    public EntityTakeEnchantedItemEvent(Block block, ItemStack item, Entity enchanter, boolean selectedEnchants) {
        this.block = block;
        this.item = item;
        this.enchanter = enchanter;
        this.selectedEnchants = selectedEnchants;
    }

    /*public EntityTakeEnchantedItemEvent(Block block, ItemStack item, EnchantOffer offer, Entity enchanter, boolean selectedEnchants) {
        this.block = block;
        this.item = item;
        this.offer = offer;
        this.enchanter = enchanter;
        this.selectedEnchants = selectedEnchants;
    }*/

    public boolean isSelectedEnchants() {
        return selectedEnchants;
    }

    public Block getBlock() {
        return block;
    }

    public ItemStack getItem() {
        return item;
    }

    /*public EnchantOffer getOffer() {
        return offer;
    }*/

    public Entity getEnchanter() {
        return enchanter;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
