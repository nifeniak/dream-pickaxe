package cc.dreamcode.pickaxe.region.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.pickaxe.config.MessageConfig;
import cc.dreamcode.pickaxe.config.PluginConfig;
import cc.dreamcode.pickaxe.region.Region;
import cc.dreamcode.pickaxe.user.User;
import cc.dreamcode.pickaxe.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RequiredPermission(permission = "dream.pickaxe")
public class PickaxeCommand extends BukkitCommand {

    private @Inject PluginConfig pluginConfig;
    private @Inject MessageConfig messageConfig;
    private @Inject UserRepository userRepository;

    public PickaxeCommand() {
        super("pickaxe", "dreampickaxe");
    }

    @Override
    public void content(@NonNull CommandSender sender, @NonNull String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        Player source = (Player) sender;
        User user = this.userRepository.findOrCreateByHumanEntity(source);

        if (args.length == 0) {
            this.messageConfig.correctUsage.send(sender);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "wand": {
                source.getInventory().addItem(this.pluginConfig.regionWand);
                break;
            }
            case "set": {
                if (!user.isRegionReady()) {
                    this.messageConfig.youForgotToSelectCorners.send(sender);
                    return;
                }

                String regionName = args[0];
                for (Region region : this.pluginConfig.regions) {
                    if (region.getRegion().equalsIgnoreCase(regionName)) {
                        this.messageConfig.regionWithThisNameAlreadyExist.send(sender);
                        return;
                    }
                }

                user.setRegionName(regionName);
                this.messageConfig.setRegionName.send(sender);

                this.pluginConfig.regions.add(Region.fromCorners(user.getRegionName(), user.getFirstCorner(), user.getSecondCorner()));
                this.pluginConfig.save();

                user.setRegionName(null);
                user.setFirstCorner(null);
                user.setSecondCorner(null);

                user.save();
                break;
            }
            case "delete": {
                String regionName = args[0];
                for (Region region : new ArrayList<>(this.pluginConfig.regions)) {
                    if (region.getRegion().equalsIgnoreCase(regionName)) {
                        this.pluginConfig.regions.remove(region);
                        this.pluginConfig.save();
                        this.messageConfig.deletedRegion.send(sender);
                        return;
                    }
                }
                this.messageConfig.regionWithThisNameWasNotFound.send(sender);
                break;
            }
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender sender, @NonNull String[] args) {
        return null;
    }
}
