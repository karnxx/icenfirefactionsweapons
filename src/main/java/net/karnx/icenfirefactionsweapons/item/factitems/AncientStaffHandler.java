package net.karnx.icenfirefactionsweapons.item.factitems;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;

public class AncientStaffHandler {

    private static final Map<UUID, StrikeSequence> ACTIVE = new HashMap<>();

    public static void trigger(Player player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return;

        LivingEntity target = serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(48),
                e -> e != player && e.isAlive()
        ).stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(player))).orElse(null);

        if (target == null) return;

        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                Component.literal("§5§l[STRIKE] The elements converge..."), false);

        ACTIVE.put(player.getUUID(), new StrikeSequence(target.getUUID(), 0));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Iterator<Map.Entry<UUID, StrikeSequence>> iter = ACTIVE.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<UUID, StrikeSequence> entry = iter.next();
            StrikeSequence seq = entry.getValue();
            seq.tick++;

            ServerLevel level = null;
            for (var serverLevel : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
                if (serverLevel.getEntity(seq.targetId) instanceof LivingEntity) {
                    level = (ServerLevel) serverLevel;
                    break;
                }
            }

            if (level == null) { iter.remove(); continue; }
            if (!(level.getEntity(seq.targetId) instanceof LivingEntity target) || !target.isAlive()) {
                iter.remove();
                continue;
            }

            if (seq.tick >= 1 && seq.tick <= 40) {
                spawnFireBreath(level, target);
                if (seq.tick % 4 == 0) {
                    target.setSecondsOnFire(6);
                    target.hurt(level.damageSources().inFire(), 3f);
                }
            }

            if (seq.tick >= 41 && seq.tick <= 80) {
                spawnIceBreath(level, target);
                if (seq.tick % 4 == 0) {
                    target.clearFire();
                    target.hurt(level.damageSources().freeze(), 3f);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
                    freezeGroundUnder(level, target);
                }
            }

            if (seq.tick >= 81 && seq.tick <= 120) {
                spawnLightningBreath(level, target);
                if (seq.tick % 6 == 0) {
                    spawnLightning(level, target.getX(), target.getY(), target.getZ());
                    double ox1 = target.getX() + (level.random.nextDouble() - 0.5) * 8;
                    double oz1 = target.getZ() + (level.random.nextDouble() - 0.5) * 8;
                    double ox2 = target.getX() + (level.random.nextDouble() - 0.5) * 8;
                    double oz2 = target.getZ() + (level.random.nextDouble() - 0.5) * 8;
                    spawnLightning(level, ox1, target.getY(), oz1);
                    spawnLightning(level, ox2, target.getY(), oz2);
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0));
                    target.hurt(level.damageSources().lightningBolt(), 6f);
                }
            }

            if (seq.tick >= 121 && seq.tick <= 140) {
                spawnFireBreath(level, target);
                spawnIceBreath(level, target);
                spawnLightningBreath(level, target);
                if (seq.tick % 4 == 0) {
                    spawnLightning(level, target.getX(), target.getY(), target.getZ());
                    target.setSecondsOnFire(5);
                    target.hurt(level.damageSources().magic(), 8f);
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 80, 2));
                }
            }

            if (seq.tick > 140) {
                level.sendParticles(ParticleTypes.EXPLOSION_EMITTER,
                        target.getX(), target.getY() + 1, target.getZ(),
                        3, 0.5, 0.5, 0.5, 0.1);
                level.playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.5f, 0.6f);
                iter.remove();
            }
        }
    }

    private static void spawnFireBreath(ServerLevel level, LivingEntity target) {
        level.sendParticles(ParticleTypes.FLAME,
                target.getX(), target.getY() + 1, target.getZ(),
                12, 0.5, 0.6, 0.5, 0.12);
        level.sendParticles(ParticleTypes.LAVA,
                target.getX(), target.getY() + 1, target.getZ(),
                5, 0.4, 0.4, 0.4, 0.0);
        level.sendParticles(ParticleTypes.LARGE_SMOKE,
                target.getX(), target.getY() + 1, target.getZ(),
                6, 0.4, 0.5, 0.4, 0.03);
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.2f, 0.8f);
    }

    private static void spawnIceBreath(ServerLevel level, LivingEntity target) {
        level.sendParticles(ParticleTypes.SNOWFLAKE,
                target.getX(), target.getY() + 1, target.getZ(),
                20, 0.6, 0.6, 0.6, 0.08);
        level.sendParticles(ParticleTypes.ITEM_SNOWBALL,
                target.getX(), target.getY() + 1, target.getZ(),
                10, 0.5, 0.5, 0.5, 0.15);
        level.sendParticles(ParticleTypes.WHITE_ASH,
                target.getX(), target.getY() + 1, target.getZ(),
                15, 0.5, 0.6, 0.5, 0.05);
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.POWDER_SNOW_STEP, SoundSource.BLOCKS, 1.4f, 0.5f);
    }

    private static void spawnLightningBreath(ServerLevel level, LivingEntity target) {
        level.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                target.getX(), target.getY() + 1, target.getZ(),
                25, 0.5, 0.6, 0.5, 0.15);
        level.sendParticles(ParticleTypes.END_ROD,
                target.getX(), target.getY() + 1, target.getZ(),
                10, 0.4, 0.5, 0.4, 0.1);
        level.sendParticles(ParticleTypes.DRAGON_BREATH,
                target.getX(), target.getY() + 1, target.getZ(),
                8, 0.4, 0.5, 0.4, 0.04);
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 0.6f, 1.8f);
    }

    private static void freezeGroundUnder(ServerLevel level, LivingEntity target) {
        int radius = 3;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                var pos = target.blockPosition().offset(dx, -1, dz);
                var state = level.getBlockState(pos);
                if (state.getBlock() == Blocks.GRASS_BLOCK || state.getBlock() == Blocks.DIRT) {
                    level.setBlock(pos, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
                }
                var above = pos.above();
                if (level.getBlockState(above).isAir()) {
                    level.setBlock(above, Blocks.SNOW.defaultBlockState(), 3);
                }
            }
        }
    }

    private static void spawnLightning(ServerLevel level, double x, double y, double z) {
        var bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.setPos(x, y, z);
            bolt.setCause(null);
            level.addFreshEntity(bolt);
        }
    }

    private static class StrikeSequence {
        UUID targetId;
        int tick;
        StrikeSequence(UUID targetId, int tick) {
            this.targetId = targetId;
            this.tick = tick;
        }
    }
}