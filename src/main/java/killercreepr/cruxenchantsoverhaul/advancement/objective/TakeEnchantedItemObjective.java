package killercreepr.cruxenchantsoverhaul.advancement.objective;

import killercreepr.crux.api.loot.LootContext;
import killercreepr.cruxadvancements.api.advancement.ObjectiveAdvancement;
import killercreepr.cruxadvancements.api.advancement.manager.CruxAdvancementManager;
import killercreepr.cruxadvancements.core.advancement.objective.NumberObjective;
import killercreepr.cruxadvancements.core.advancement.objective.ObjectiveCommonData;
import killercreepr.cruxenchantsoverhaul.event.EntityTakeEnchantedItemEvent;
import killercreepr.cruxenchantsoverhaul.loot.EnchantOverhaulEventLootContexts;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TakeEnchantedItemObjective extends NumberObjective {
    public TakeEnchantedItemObjective(@NotNull ObjectiveCommonData data, int maxProgress) {
        super(data, maxProgress);
    }

    public boolean trigger(@NotNull UUID who,
                           @NotNull CruxAdvancementManager manager,
                           @NotNull ObjectiveAdvancement advancement,
                           @NotNull EntityTakeEnchantedItemEvent event){
        LootContext ctx = EnchantOverhaulEventLootContexts.builder(event).build();
        return trigger(
            who, manager, advancement, ctx
        );
    }
}
