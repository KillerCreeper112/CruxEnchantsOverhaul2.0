package killercreepr.cruxenchantsoverhaul.registries;

import killercreepr.crux.api.registry.KeyedRegistry;
import killercreepr.cruxenchantsoverhaul.anvil.recipe.AnvilRecipe;
import killercreepr.cruxenchantsoverhaul.api.enchant.EEnchant;

public class EnchantsRegistries {
    public static final KeyedRegistry<AnvilRecipe> ANVIL_RECIPES = KeyedRegistry.keyedRegistry();
    public static final KeyedRegistry<EEnchant> EENCHANT = KeyedRegistry.keyedRegistry();
}
