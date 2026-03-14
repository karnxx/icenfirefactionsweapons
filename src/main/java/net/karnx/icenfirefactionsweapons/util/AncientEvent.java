package net.karnx.icenfirefactionsweapons.util;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class AncientEvent {

    public static void trigger(Player player) {
        if (player.level().isClientSide) return;
        ServerLevel serverLevel = (ServerLevel) player.level();

        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                Component.literal("§4§l[ANCIENT EVENT] The Ancient One awakens... Run..."), false);

        EntityFireDragon dragon = new EntityFireDragon(
                IafEntityRegistry.FIRE_DRAGON.get(), serverLevel);
        dragon.setPos(player.getX(), player.getY() + 20, player.getZ());
        CompoundTag nbt = new CompoundTag();
        dragon.save(nbt);
        nbt.putInt("AgeTicks", 100 * 540000);
        nbt.putBoolean("TamedDragon", false);
        nbt.putBoolean("AgingDisabled", true);
        dragon.load(nbt);
        serverLevel.addFreshEntity(dragon);
        dragon.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1200.0);
        dragon.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50.0);
        dragon.getAttribute(Attributes.ARMOR).setBaseValue(20.0);
        dragon.getAttribute(Attributes.ARMOR_TOUGHNESS).setBaseValue(8.0);
        dragon.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.6);
        dragon.setHealth(1200f);

        dragon.setTarget(player);
        ServerBossEvent bossBar = new ServerBossEvent(
                Component.literal("§4§l✦ The Ancient One ✦"),
                BossEvent.BossBarColor.PURPLE,
                BossEvent.BossBarOverlay.NOTCHED_10
        );
        AncientBossHandler.BOSS_BARS.put(dragon.getUUID(), bossBar);
        AncientBossHandler.SUMMONER_MAP.put(dragon.getUUID(), player.getUUID());
    }
}