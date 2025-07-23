package killercreepr.cruxenchantsoverhaul.anvil;

import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipeResult;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;

public interface CruxedAnvil {
    static CruxedAnvil simple(){
        return new SimpleCruxedAnvil();
    }

    AnvilRecipeResult merge(AnvilView view);

    ItemStack getFirst();

    void setFirst(ItemStack first);

    ItemStack getSecond();

    void setSecond(ItemStack second);
}
