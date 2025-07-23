package killercreepr.cruxenchantsoverhaul.loot;

import killercreepr.crux.api.data.DataExchange;
import killercreepr.crux.api.loot.LootContext;
import killercreepr.cruxenchantsoverhaul.event.EntityTakeEnchantedItemEvent;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class EnchantOverhaulEventLootContexts {
    public static LootContext.Builder builder(@NotNull EntityTakeEnchantedItemEvent event){
        Entity enchanter = event.getEnchanter();
        return builder()
            .info(
                DataExchange.builder()
                    .putAll(event.getItem(), "item")
                    .putAll(event.getBlock(), "block")
                    //.putAll(event.getOffer(), "enchant_offer", "offer")
                    .putAll(event.getEnchanter(), "enchanter")
                    .putAll(event.isSelectedEnchants(), "selected_enchants")
                    .build()
            )
            .location(event.getBlock().getLocation())
            .looter(enchanter)
            .looted(event.getItem())
            ;
    }

    private static LootContext.Builder builder(){
        return LootContext.builder();
    }
}
