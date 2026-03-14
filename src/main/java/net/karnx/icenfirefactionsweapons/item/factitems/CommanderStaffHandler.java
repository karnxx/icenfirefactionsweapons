package net.karnx.icenfirefactionsweapons.item.factitems;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = IceNFireFactionsWeapons.MODID)
public class CommanderStaffHandler {

    @SubscribeEvent
    public static void onDragonTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof EntityDragonBase dragon)) return;
        if (dragon.level().isClientSide) return;
        if (!dragon.getPersistentData().contains("CommanderDespawnTick")) return;

        int ticksLeft = dragon.getPersistentData().getInt("CommanderDespawnTick");
        if (ticksLeft <= 0) {
            dragon.discard();
            return;
        }
        dragon.getPersistentData().putInt("CommanderDespawnTick", ticksLeft - 1);
    }

    @SubscribeEvent
    public static void onTargetChange(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof EntityDragonBase dragon)) return;
        if (!dragon.getPersistentData().contains("CommanderOwner")) return;

        Entity newTarget = event.getNewTarget();
        if (!(newTarget instanceof Player player)) return;

        String ownerStr = dragon.getPersistentData().getString("CommanderOwner");
        try {
            UUID ownerUUID = UUID.fromString(ownerStr);
            if (player.getUUID().equals(ownerUUID)) {
                event.setCanceled(true);
            }
        } catch (IllegalArgumentException ignored) {}
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        Player player = event.getEntity();
        if (!(event.getTarget() instanceof LivingEntity target)) return;
        if (player.level().isClientSide) return;

        player.level().getEntitiesOfClass(
                EntityDragonBase.class,
                player.getBoundingBox().inflate(30),
                e -> e.getPersistentData().contains("CommanderOwner") &&
                        e.getPersistentData().getString("CommanderOwner").equals(player.getUUID().toString())
        ).forEach(d -> d.setTarget(target));
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (player.level().isClientSide) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;

        player.level().getEntitiesOfClass(
                EntityDragonBase.class,
                player.getBoundingBox().inflate(30),
                e -> e.getPersistentData().contains("CommanderOwner") &&
                        e.getPersistentData().getString("CommanderOwner").equals(player.getUUID().toString())
        ).forEach(d -> d.setTarget(attacker));
    }
}