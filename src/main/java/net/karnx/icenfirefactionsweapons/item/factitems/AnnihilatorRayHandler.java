package net.karnx.icenfirefactionsweapons.item.factitems;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnnihilatorRayHandler {

    public static final Map<UUID, Integer> FIRING = new HashMap<>();
    public static final Map<UUID, Vec3> LOCKED_POS = new HashMap<>();
    private static final float RANGE = 128f;
    private static final float DAMAGE = 10f;

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Player player = event.player;
        if (player.level().isClientSide) return;

        UUID id = player.getUUID();
        if (!FIRING.containsKey(id)) return;

        ServerLevel serverLevel = (ServerLevel) player.level();
        int ticksLeft = FIRING.get(id);

        Vec3 locked = LOCKED_POS.get(id);
        if (locked != null) {
            player.teleportTo(locked.x, locked.y, locked.z);
            player.setDeltaMovement(Vec3.ZERO);
            player.fallDistance = 0;
            player.hurtMarked = true;
        }

        if (ticksLeft % 2 == 0) {
            fireBeam(player, serverLevel);

            ItemStack held = player.getMainHandItem();
            if (held.getItem() instanceof AnnihilatorRay) {
                held.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            }
        }

        spawnBeamParticles(serverLevel, player);

        if (ticksLeft <= 0) {
            FIRING.remove(id);
            LOCKED_POS.remove(id);
        } else {
            FIRING.put(id, ticksLeft - 1);
        }
    }

    private Vec3 getGunMuzzle(Player player) {
        Vec3 eye = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 right = new Vec3(-look.z, 0, look.x).normalize();
        return eye.add(right.scale(0.6)).add(look.scale(0.8)).add(0, -0.3, 0);
    }

    private void fireBeam(Player player, ServerLevel serverLevel) {
        Vec3 muzzle = getGunMuzzle(player);
        Vec3 look = player.getLookAngle();
        Vec3 end = muzzle.add(look.scale(RANGE));

        HitResult blockHit = serverLevel.clip(new ClipContext(
                player.getEyePosition(), end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));

        Vec3 hitEnd = blockHit.getType() != HitResult.Type.MISS ? blockHit.getLocation() : end;

        LivingEntity target = getTarget(serverLevel, player, muzzle, hitEnd);
        if (target != null) {
            target.hurt(serverLevel.damageSources().playerAttack(player), DAMAGE);
            target.setSecondsOnFire(3);
            serverLevel.sendParticles(ParticleTypes.EXPLOSION,
                    target.getX(), target.getY() + 1, target.getZ(),
                    3, 0.3, 0.4, 0.3, 0.05);
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    target.getX(), target.getY() + 1, target.getZ(),
                    30, 0.4, 0.6, 0.4, 0.08);
            serverLevel.sendParticles(ParticleTypes.LAVA,
                    target.getX(), target.getY() + 1, target.getZ(),
                    10, 0.3, 0.3, 0.3, 0.0);
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    target.getX(), target.getY() + 1, target.getZ(),
                    25, 0.4, 0.5, 0.4, 0.1);

            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.4f, 1.6f);
            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.BLAZE_HURT, SoundSource.PLAYERS, 0.8f, 0.6f);
        }
        serverLevel.sendParticles(ParticleTypes.FLASH,
                muzzle.x, muzzle.y, muzzle.z,
                1, 0.0, 0.0, 0.0, 0.0);
        serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                muzzle.x, muzzle.y, muzzle.z,
                15, 0.1, 0.1, 0.1, 0.15);

        serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.15f, 2.0f);
        serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.6f, 0.4f);
    }

    private void spawnBeamParticles(ServerLevel level, Player player) {
        Vec3 muzzle = getGunMuzzle(player);
        Vec3 look = player.getLookAngle();
        Vec3 end = muzzle.add(look.scale(RANGE));

        HitResult blockHit = level.clip(new ClipContext(
                player.getEyePosition(), end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));

        Vec3 hitEnd = blockHit.getType() != HitResult.Type.MISS ? blockHit.getLocation() : end;
        double beamLength = muzzle.distanceTo(hitEnd);
        int steps = (int) Math.min(beamLength * 2, 128);

        for (int i = 0; i <= steps; i++) {
            double t = (double) i / steps;
            Vec3 point = muzzle.lerp(hitEnd, t);
            level.sendParticles(ParticleTypes.END_ROD,
                    point.x, point.y, point.z,
                    1, 0.0, 0.0, 0.0, 0.0);
            if (i % 3 == 0) {
                level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                        point.x, point.y, point.z,
                        2, 0.04, 0.04, 0.04, 0.02);
            }
            if (i % 4 == 0) {
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
                        point.x, point.y, point.z,
                        1, 0.06, 0.06, 0.06, 0.0);
            }
            if (i % 8 == 0) {
                level.sendParticles(ParticleTypes.DRAGON_BREATH,
                        point.x, point.y, point.z,
                        1, 0.05, 0.05, 0.05, 0.01);
            }
        }
    }

    private LivingEntity getTarget(Level level, Player shooter, Vec3 from, Vec3 to) {
        double closest = Double.MAX_VALUE;
        LivingEntity result = null;

        AABB searchBox = shooter.getBoundingBox().expandTowards(to.subtract(from)).inflate(1.5);
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, searchBox,
                e -> e != shooter && e.isAlive())) {
            var expanded = entity.getBoundingBox().inflate(0.3);
            var hit = expanded.clip(from, to);
            if (hit.isPresent()) {
                double dist = from.distanceToSqr(hit.get());
                if (dist < closest) {
                    closest = dist;
                    result = entity;
                }
            }
        }
        return result;
    }
}