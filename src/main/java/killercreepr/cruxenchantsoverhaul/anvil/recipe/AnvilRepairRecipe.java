package killercreepr.cruxenchantsoverhaul.anvil.recipe;

import killercreepr.crux.api.item.CruxItem;
import killercreepr.crux.api.item.predicate.ItemPredicate;
import killercreepr.crux.api.text.context.InputContext;
import killercreepr.crux.api.text.provider.StringTagProvider;
import killercreepr.crux.api.text.resolver.StringResolver;
import killercreepr.crux.api.text.tags.TagsPrefixBuilder;
import killercreepr.crux.api.text.tags.container.TagContainer;
import killercreepr.crux.api.valueproviders.number.NumberProvider;
import killercreepr.crux.core.Crux;
import killercreepr.crux.core.text.resolver.Tag;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.view.AnvilView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class AnvilRepairRecipe implements AnvilRecipe{
    protected final @NotNull Key key;
    protected final @NotNull ItemPredicate firstIngredient;
    protected final @NotNull Collection<AnvilIngredient> secondIngredient;
    protected final @Nullable NumberProvider experienceCost;
    protected final @Nullable NumberProvider itemAmountCost;

    public AnvilRepairRecipe(@NotNull Key key, @NotNull ItemPredicate firstIngredient, @NotNull Collection<AnvilIngredient> secondIngredient, @Nullable NumberProvider experienceCost, @Nullable NumberProvider itemAmountCost) {
        this.key = key;
        this.firstIngredient = firstIngredient;
        this.secondIngredient = secondIngredient;
        this.experienceCost = experienceCost;
        this.itemAmountCost = itemAmountCost;
    }

    @NotNull
    @Override
    public ItemPredicate getFirstIngredient() {
        return firstIngredient;
    }

    @NotNull
    @Override
    public Collection<AnvilIngredient> getSecondIngredient() {
        return secondIngredient;
    }

    public AnvilIngredient findIngredient(@NotNull ItemStack input, ItemStack first){
        for(AnvilIngredient ingredient : secondIngredient){
            if(ingredient.getIngredient().test(input) && ingredient.canApplyTo(first)) return ingredient;
        }
        return null;
    }

    @Nullable
    @Override
    public AnvilRecipeResult merge(ItemStack first, ItemStack second, AnvilView view) {
        if(first == null) return null;
        if(second == null) return null;
        AnvilIngredient ingredient = findIngredient(second, first);
        if(ingredient==null) return null;

        NumberProvider repairAmount = ingredient.getRepairAmount();
        TagContainer<StringResolver> tagContainer = TagContainer.string()
            .hook(first, TagsPrefixBuilder.addon("first_"))
            .hook(second, TagsPrefixBuilder.addon("second_"));
        InputContext repairContext = InputContext.simple(StringTagProvider.build(tagContainer));
        int repair = repairAmount.sample(repairContext).intValue();

        ItemStack result = first.clone();

        int oldDamage = 0;
        if(result.getItemMeta() instanceof Damageable meta){
            oldDamage = meta.getDamage();
            if(repair > 0){
                if(meta.getDamage() < 1) return new AnvilRecipeResult(null, null, null);

                meta.setDamage(Math.max(oldDamage - repair, 0));
                result.setItemMeta(meta);
            }
        }

        result.editMeta(meta ->{
            String rename = view.getRenameText();
            meta.displayName(rename == null || rename.isBlank() ? null : MiniMessage.miniMessage().deserialize(rename));
        });

        InputContext ctx = InputContext.simple(StringTagProvider.build(
            TagContainer.string()
                .hook(first, TagsPrefixBuilder.addon("first_"))
                .hook(second, TagsPrefixBuilder.addon("second_"))
                .add(Tag.parsed("repair_amount", repair + ""))
        ));

        Integer experienceCost = this.experienceCost == null ? null : this.experienceCost.sample(ctx).intValue();
        Integer itemAmountCost = this.itemAmountCost == null ? null : this.itemAmountCost.sample(ctx).intValue();
        if(itemAmountCost == null){
            int count = 1;
            int maxSecond = second.getAmount();
            while(count < maxSecond){
                tagContainer.add(Tag.parsed("second_item_amount", count+""));
                int repairX = repairAmount.sample(repairContext).intValue();
                if(oldDamage - repairX < 1) break;
                count++;
            }
            itemAmountCost = count;
        }

        CruxItem completeResult = ingredient.applyToResult(CruxItem.wrap(result));

        Crux.handlers().item().update(completeResult.item());
        return new AnvilRecipeResult(completeResult.item(), experienceCost, itemAmountCost);
    }

    @Override
    public @NotNull Key key() {
        return key;
    }
}
