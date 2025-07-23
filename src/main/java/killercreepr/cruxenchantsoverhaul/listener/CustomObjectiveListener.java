package killercreepr.cruxenchantsoverhaul.listener;

import killercreepr.crux.api.entity.memory.EntityMemory;
import killercreepr.cruxadvancements.core.entity.memory.AdvancementHolder;
import killercreepr.cruxenchantsoverhaul.advancement.objective.TakeEnchantedItemObjective;
import killercreepr.cruxenchantsoverhaul.event.EntityTakeEnchantedItemEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class CustomObjectiveListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityTakeEnchantedItem(EntityTakeEnchantedItemEvent event) {
        Entity p = event.getEnchanter();
        AdvancementHolder holder = holder(p);
        if(holder==null) return;
        holder.getAdvancementTracker().apply(TakeEnchantedItemObjective.class, (manager, advancement, objective) -> {
            objective.trigger(p.getUniqueId(), manager, advancement, event);
        });
    }

    private AdvancementHolder holder(@NotNull Entity p){
        return EntityMemory.getDataHolder(p, AdvancementHolder.class);
    }

}
