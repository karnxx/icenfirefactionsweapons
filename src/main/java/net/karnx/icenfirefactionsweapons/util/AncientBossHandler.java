package net.karnx.icenfirefactionsweapons.util;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class AncientBossHandler {

    public static final Map<UUID, ServerBossEvent> BOSS_BARS = new HashMap<>();
    public static final Map<UUID, UUID> SUMMONER_MAP = new HashMap<>();
    private static final Map<UUID, Integer> ATTACK_TICKS = new HashMap<>();
    private static final Map<UUID, Set<UUID>> MINIONS = new HashMap<>();
    private static final int MAX_MINIONS = 4;

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof EntityFireDragon dragon)) return;
        if (dragon.level().isClientSide) return;
        if (!BOSS_BARS.containsKey(dragon.getUUID())) return;

        ServerLevel serverLevel = (ServerLevel) dragon.level();
        ServerBossEvent bossBar = BOSS_BARS.get(dragon.getUUID());
        boolean phase2 = dragon.getHealth() < dragon.getMaxHealth() * 0.5f;

        bossBar.setProgress(Math.max(0f, Math.min(1f, dragon.getHealth() / dragon.getMaxHealth())));

        for (ServerPlayer player : serverLevel.players()) {
            if (player.distanceToSqr(dragon) < 300 * 300) {
                bossBar.addPlayer(player);
            } else {
                bossBar.removePlayer(player);
            }
        }

        if (dragon.tickCount % 40 == 0 && dragon.getHealth() < dragon.getMaxHealth()) {
            dragon.setHealth(Math.min(dragon.getMaxHealth(), dragon.getHealth() + (phase2 ? 5f : 12f)));
        }

        if (phase2 && !bossBar.getName().getString().contains("ENRAGED")) {
            bossBar.setColor(BossEvent.BossBarColor.RED);
            bossBar.setName(Component.literal("§4§l✦ The Ancient One [ENRAGED] ✦"));
        }

        if (dragon.getTarget() == null || !dragon.getTarget().isAlive()) {
            LivingEntity nearest = serverLevel.getEntitiesOfClass(
                    LivingEntity.class,
                    dragon.getBoundingBox().inflate(512),
                    e -> e != dragon && !(e instanceof EntityFireDragon) && e.isAlive()
            ).stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(dragon))).orElse(null);
            if (nearest != null) dragon.setTarget(nearest);
        }

        if (dragon.tickCount % 10 == 0) {
            int radius = phase2 ? 20 : 12;
            spawnFireAround(serverLevel, dragon.blockPosition(), radius);
        }

        if (dragon.tickCount % 20 == 0) {
            int radius = phase2 ? 30 : 20;
            List<LivingEntity> nearby = serverLevel.getEntitiesOfClass(
                    LivingEntity.class,
                    dragon.getBoundingBox().inflate(radius),
                    e -> e != dragon && !(e instanceof EntityFireDragon) && e.isAlive()
            );
            for (LivingEntity entity : nearby) {
                entity.setSecondsOnFire(phase2 ? 10 : 5);
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 0));
            }
        }

        int switchInterval = phase2 ? 25 : 50;
        if (dragon.tickCount % switchInterval == 0) {
            ATTACK_TICKS.put(dragon.getUUID(), dragon.level().random.nextInt(4));
        }

        LivingEntity target = dragon.getTarget();
        if (target != null && dragon.tickCount % 20 == 0) {
            int attackType = ATTACK_TICKS.getOrDefault(dragon.getUUID(), 0);
            switch (attackType) {
                case 1 -> {
                    target.clearFire();
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, phase2 ? 3 : 1));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, phase2 ? 1 : 0));
                    target.addEffect(new MobEffectInstance(MobEffects.WITHER, 40, 0));
                    target.hurt(serverLevel.damageSources().freeze(), phase2 ? 14f : 8f);
                    spawnFrostAround(serverLevel, target.blockPosition(), phase2 ? 8 : 5);
                }
                case 2 -> {
                    spawnLightning(serverLevel, target.getX(), target.getY(), target.getZ());
                    target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80, 0));
                    target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
                    if (phase2) {
                        for (int i = 0; i < 3; i++) {
                            double ox = target.getX() + (dragon.getRandom().nextDouble() - 0.5) * 10;
                            double oz = target.getZ() + (dragon.getRandom().nextDouble() - 0.5) * 10;
                            spawnLightning(serverLevel, ox, target.getY(), oz);
                        }
                    }
                }
                case 3 -> {
                    target.setSecondsOnFire(phase2 ? 6 : 4);
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 60, phase2 ? 1 : 0));
                    target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1));
                    target.hurt(serverLevel.damageSources().magic(), phase2 ? 14f : 8f);
                    spawnFireAround(serverLevel, target.blockPosition(), phase2 ? 10 : 6);
                }
            }
        }

        if (phase2 && dragon.tickCount % 40 == 0) {
            for (int i = 0; i < 2; i++) {
                double ox = dragon.getX() + (dragon.getRandom().nextDouble() - 0.5) * 20;
                double oz = dragon.getZ() + (dragon.getRandom().nextDouble() - 0.5) * 20;
                spawnLightning(serverLevel, ox, dragon.getY(), oz);
            }
        }

        if (phase2 && dragon.tickCount % 60 == 0) {
            spawnFrostAround(serverLevel, dragon.blockPosition(), 15);
        }

        int summonInterval = phase2 ? 200 : 400;
        if (dragon.tickCount % summonInterval == 0) {
            Set<UUID> minionIds = MINIONS.computeIfAbsent(dragon.getUUID(), k -> new HashSet<>());
            minionIds.removeIf(id -> serverLevel.getEntity(id) == null || !serverLevel.getEntity(id).isAlive());

            if (minionIds.size() < MAX_MINIONS) {
                int toSummon = phase2 ? 2 : 1;
                for (int i = 0; i < toSummon && minionIds.size() < MAX_MINIONS; i++) {
                    EntityFireDragon minion = spawnMinion(serverLevel, dragon, phase2);
                    if (minion != null) {
                        minionIds.add(minion.getUUID());
                        serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                                Component.literal("§c[ANCIENT EVENT] The Ancient One calls forth a guardian!"), false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof EntityFireDragon dragon)) return;
        UUID id = dragon.getUUID();

        if (BOSS_BARS.containsKey(id)) {
            BOSS_BARS.get(id).removeAllPlayers();
            BOSS_BARS.remove(id);
            ATTACK_TICKS.remove(id);
            SUMMONER_MAP.remove(id);

            Set<UUID> minionIds = MINIONS.remove(id);
            if (minionIds != null && dragon.level() instanceof ServerLevel serverLevel) {
                for (UUID minionId : minionIds) {
                    var entity = serverLevel.getEntity(minionId);
                    if (entity != null && entity.isAlive()) entity.kill();
                }
            }

            if (dragon.level() instanceof ServerLevel serverLevel) {
                serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                        Component.literal("§6§l[ANCIENT EVENT] The Ancient One has been defeated. The world is saved... for now."), false);

                Advancement adv = serverLevel.getServer()
                        .getAdvancements()
                        .getAdvancement(new ResourceLocation("icenfirefactionsweapons", "the_one_who_felled_the_ancient"));


                dragon.spawnAtLocation(ModItems.ANCIENT_EYE.get(), 2);
                dragon.spawnAtLocation(ModItems.ANCIENT_HORN.get(),4);
                dragon.spawnAtLocation(ModItems.ANCIENT_BONE.get(), 10);
                dragon.spawnAtLocation(ModItems.ANCIENT_SCALE.get   (), 10);
                dragon.spawnAtLocation(ModItems.ANCIENT_FANG.get(),4);


                if (adv == null) {
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                            Component.literal("file path wrongjfhhfhfhfhfhfgaao"), false);
                } else {
                    for (ServerPlayer player : serverLevel.players()) {
                        if (player.distanceToSqr(dragon) < 300 * 300) {
                            AdvancementProgress prog = player.getAdvancements().getOrStartProgress(adv);
                            for (String crit : prog.getRemainingCriteria()) {
                                player.getAdvancements().award(adv, crit);
                            }
                        }
                    }
                }
            }
            return;
        }

        for (Map.Entry<UUID, Set<UUID>> entry : MINIONS.entrySet()) {
            if (entry.getValue().remove(id)) {
                if (dragon.level() instanceof ServerLevel serverLevel) {
                    serverLevel.getServer().getPlayerList().broadcastSystemMessage(
                            Component.literal("§e[ANCIENT EVENT] A guardian falls!"), false);
                }
                break;
            }
        }
    }

    private EntityFireDragon spawnMinion(ServerLevel level, EntityFireDragon boss, boolean phase2) {
        EntityFireDragon minion = new EntityFireDragon(IafEntityRegistry.FIRE_DRAGON.get(), level);

        double angle = level.random.nextDouble() * Math.PI * 2;
        double dist = 15 + level.random.nextDouble() * 10;
        double mx = boss.getX() + Math.cos(angle) * dist;
        double mz = boss.getZ() + Math.sin(angle) * dist;

        minion.setPos(mx, boss.getY() + 10, mz);
        level.addFreshEntity(minion);

        minion.getAttribute(Attributes.MAX_HEALTH).setBaseValue(phase2 ? 200.0 : 120.0);
        minion.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(phase2 ? 22.0 : 14.0);
        minion.getAttribute(Attributes.ARMOR).setBaseValue(10.0);
        minion.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(128.0);
        minion.setHealth(phase2 ? 200f : 120f);

        LivingEntity minionTarget = level.getEntitiesOfClass(
                LivingEntity.class,
                minion.getBoundingBox().inflate(512),
                e -> e != minion && !(e instanceof EntityFireDragon) && e.isAlive()
        ).stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(minion))).orElse(null);
        if (minionTarget != null) minion.setTarget(minionTarget);

        return minion;
    }

    private void spawnFireAround(ServerLevel level, BlockPos center, int radius) {
        int attempts = 30;
        for (int i = 0; i < attempts; i++) {
            int dx = level.random.nextInt(radius * 2) - radius;
            int dz = level.random.nextInt(radius * 2) - radius;
            BlockPos pos = center.offset(dx, 0, dz);

            for (int dy = 5; dy >= -5; dy--) {
                BlockPos check = pos.offset(0, dy, 0);
                BlockPos above = check.above();
                BlockState stateAbove = level.getBlockState(above);
                BlockState stateBelow = level.getBlockState(check);

                if (!stateBelow.isAir() && stateAbove.isAir()) {
                    if (BaseFireBlock.canBePlacedAt(level, above, level.random.nextBoolean() ?
                            net.minecraft.core.Direction.NORTH : net.minecraft.core.Direction.SOUTH)) {
                        level.setBlock(above, Blocks.FIRE.defaultBlockState(), 3);
                    }
                    break;
                }
            }
        }
    }

    private void spawnFrostAround(ServerLevel level, BlockPos center, int radius) {
        int attempts = 25;
        for (int i = 0; i < attempts; i++) {
            int dx = level.random.nextInt(radius * 2) - radius;
            int dz = level.random.nextInt(radius * 2) - radius;
            BlockPos pos = center.offset(dx, 0, dz);

            for (int dy = 5; dy >= -5; dy--) {
                BlockPos check = pos.offset(0, dy, 0);
                BlockPos above = check.above();
                BlockState stateBelow = level.getBlockState(check);
                BlockState stateAbove = level.getBlockState(above);

                if (!stateBelow.isAir() && stateAbove.isAir()) {
                    if (stateBelow.getBlock() == Blocks.GRASS_BLOCK || stateBelow.getBlock() == Blocks.DIRT) {
                        level.setBlock(check, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
                    }
                    if (level.getBlockState(above).isAir()) {
                        level.setBlock(above, Blocks.SNOW.defaultBlockState(), 3);
                    }
                    break;
                }
            }
        }

        List<LivingEntity> nearby = level.getEntitiesOfClass(
                LivingEntity.class,
                new net.minecraft.world.phys.AABB(center).inflate(radius),
                e -> !(e instanceof EntityFireDragon) && e.isAlive()
        );
        for (LivingEntity entity : nearby) {
            entity.clearFire();
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
            entity.hurt(level.damageSources().freeze(), 3f);
        }
    }

    private void spawnLightning(ServerLevel level, double x, double y, double z) {
        var bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.setPos(x, y, z);
            bolt.setCause(null);
            level.addFreshEntity(bolt);
        }
    }
}