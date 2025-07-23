package killercreepr.cruxenchantsoverhaul.menu.enchanting;

import killercreepr.crux.api.communication.CreateSound;
import killercreepr.cruxenchantsoverhaul.block.active.EnchantTableBlock;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;

public interface EnchantingMenu {
    @NotNull
    CreateSound CLICK = CreateSound.sound(Sound.UI_BUTTON_CLICK,0.2f, 1f);
    @NotNull
    EnchantTableBlock getBlock();
}
