package cc.dreamcode.pickaxe.region.controller;

import cc.dreamcode.pickaxe.config.MessageConfig;
import cc.dreamcode.pickaxe.config.PluginConfig;
import cc.dreamcode.pickaxe.region.Region;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class RegionMineController implements Listener {

    private final Tasker tasker;
    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(BlockBreakEvent event) {
        Player source = event.getPlayer();
        Block block = event.getBlock();
        ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();

        for (Region region : this.pluginConfig.regions) {
            if (region.isIn(block.getLocation())) {
                event.setCancelled(true);

                if (!itemInHand.getType().name().toUpperCase().contains("PICKAXE")) {
                    this.messageConfig.youHaveToHavePickaxeInYourHand.send(source);
                    return;
                }
                if (itemInHand.getEnchantmentLevel(Enchantment.DIG_SPEED) < region.getMinEfficiencyLevel()) {
                    this.messageConfig.toMineInThisRegionYouHaveToHaveHigherEfficiencyLevel.send(source, new MapBuilder<String, Object>()
                            .put("level", region.getMinEfficiencyLevel())
                            .build());
                    return;
                }
                if (!region.getAllowedMaterials().isEmpty() && !region.getAllowedMaterials().contains(block.getType().name())) {
                    this.messageConfig.youAreNotAllowedToMineThisMaterialInThisRegion.send(source);
                    return;
                }

                block.getDrops(itemInHand).forEach(itemStack -> source.getInventory().addItem(itemStack));

                if (this.pluginConfig.setBedrock) {

                    Material type = block.getType();
                    block.setType(Material.BEDROCK);

                    this.tasker.newDelayer(Duration.ofSeconds(this.pluginConfig.delayInSeconds))
                            .delayed(() -> block.setType(type))
                            .executeSync();
                } else {
                    block.setType(Material.AIR);
                }
                return;
            }
        }
    }
}
