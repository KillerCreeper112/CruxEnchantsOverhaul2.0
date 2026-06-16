package killercreepr.cruxenchantsoverhaul.listener;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.cruxenchantsoverhaul.anvil.CruxedAnvil;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipeResult;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.view.AnvilView;

public class AnvilListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onPrepareAnvil(PrepareAnvilEvent event){
        AnvilView view = event.getView();
        AnvilInventory inv = event.getInventory();
        view.setMaximumRepairCost(Integer.MAX_VALUE); //Remove too expensive

        ItemStack first = inv.getFirstItem();
        ItemStack second = inv.getSecondItem();

        if(CruxItem.isEmpty(second)){
            view.setRepairCost(1);
            return;
        }

        CruxedAnvil anvil = CruxedAnvil.simple();
        anvil.setFirst(first);
        anvil.setSecond(second);
        AnvilRecipeResult result = anvil.merge(view);
        if(result==null) return;
        event.setResult(
            first != null && first.equals(result.getResultItem()) ? null : result.getResultItem()
        );
        if(result.getExperienceCost() != null) view.setRepairCost(result.getExperienceCost());
        if(result.getItemAmountCost() != null) view.setRepairItemCountCost(result.getItemAmountCost());

        ItemMeta meta = first.getItemMeta();
        if(meta==null) return;
        String name = view.getRenameText();
        if(meta.displayName() == null){
            if(name != null && !name.isBlank()) view.setRepairCost(view.getRepairCost()+1);
        }else{
            if(name == null || name.isBlank() || !name.equals(MiniMessage.miniMessage().serialize(meta.displayName()))){
                view.setRepairCost(view.getRepairCost()+1);
            }
        }
    }


}
