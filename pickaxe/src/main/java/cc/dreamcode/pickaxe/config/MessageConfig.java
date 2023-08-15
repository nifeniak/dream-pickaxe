package cc.dreamcode.pickaxe.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Pickaxe (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie posiadasz uprawnień.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie jesteś graczem.");

    public BukkitNotice setFirstCorner = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyślnie ustawiono pierwszy róg regionu.");
    public BukkitNotice setSecondCorner = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyślnie ustawiono drugi róg regionu.");
    public BukkitNotice inOtherRegion = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie możesz ustawić regionu tam, gdzie jest już jeden.");
    public BukkitNotice setRegionName = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyślnie ustawiłeś nazwę regionu.");
    public BukkitNotice regionWithThisNameAlreadyExist = new BukkitNotice(MinecraftNoticeType.CHAT, "&cRegion o tej nazwie już istnieje.");
    public BukkitNotice youForgotToSelectCorners = new BukkitNotice(MinecraftNoticeType.CHAT, "&cZapomniałeś ustawić rogi.");
    public BukkitNotice correctUsage = new BukkitNotice(MinecraftNoticeType.CHAT, "&cPoprawne użycie: &4/pickaxe (wand/set/delete) <nazwa_regionu>");
    public BukkitNotice regionWithThisNameWasNotFound = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie znaleziono regionu o tej nazwie.");
    public BukkitNotice deletedRegion = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyślnie usunięto region.");
    public BukkitNotice youAreNotAllowedToMineThisMaterialInThisRegion = new BukkitNotice(MinecraftNoticeType.CHAT, "&cNie możesz wykopać tego bloku.");
    public BukkitNotice youHaveToHavePickaxeInYourHand = new BukkitNotice(MinecraftNoticeType.CHAT, "&cMusisz trzymać kilof w ręce.");
    public BukkitNotice toMineInThisRegionYouHaveToHaveHigherEfficiencyLevel = new BukkitNotice(MinecraftNoticeType.CHAT,
            "&cAby kopać w tym regionie, musisz mieć co najmniej poziom szybkości {level}.");

}
