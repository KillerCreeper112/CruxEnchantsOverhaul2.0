package killercreepr.cruxenchantsoverhaul.event;

import killercreepr.cruxenchantsoverhaul.menu.enchanting.EnchantTableMenu;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class EnchantTableGenerateEnchantsEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    protected final @NotNull EnchantTableMenu menu;
    protected final @NotNull Collection<Enchantment> available;
    protected boolean cancel = false;

    public EnchantTableGenerateEnchantsEvent(@NotNull EnchantTableMenu menu, @NotNull Collection<Enchantment> available) {
        this.menu = menu;
        this.available = available;
    }

    public @NotNull EnchantTableMenu getMenu() {
        return menu;
    }

    /**
     * @return A mutable available enchantment list.
     */
    public @NotNull Collection<Enchantment> getAvailable() {
        return available;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
