package net.karnx.icenfirefactionsweapons.item.factitems;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.EntityLightningDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CommanderStaff extends Item {

    private static final int COOLDOWN_TICKS = 1200;
    private static final int DESPAWN_TICKS = 1200;

    public CommanderStaff(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;

            int type = level.random.nextInt(3);
            EntityDragonBase dragon;

            if (type == 0) {
                dragon = new EntityFireDragon(IafEntityRegistry.FIRE_DRAGON.get(), serverLevel);
            } else if (type == 1) {
                dragon = new EntityIceDragon(IafEntityRegistry.ICE_DRAGON.get(), serverLevel);
            } else {
                dragon = new EntityLightningDragon(IafEntityRegistry.LIGHTNING_DRAGON.get(), serverLevel);
            }

            dragon.setPos(player.getX() + 3, player.getY(), player.getZ() + 3);

            CompoundTag nbt = new CompoundTag();
            dragon.save(nbt);
            nbt.putInt("AgeTicks", 100 * 24000);
            nbt.putBoolean("TamedDragon", true);
            nbt.putBoolean("AgingDisabled", true);
            nbt.putUUID("Owner", player.getUUID());
            dragon.load(nbt);

            dragon.getPersistentData().putInt("CommanderDespawnTick", DESPAWN_TICKS);
            dragon.getPersistentData().putString("CommanderOwner", player.getUUID().toString());

            serverLevel.addFreshEntity(dragon);

            LivingEntity initialTarget = serverLevel.getEntitiesOfClass(
                    net.minecraft.world.entity.Mob.class,
                    dragon.getBoundingBox().inflate(30),
                    e -> e.getTarget() != null && e.getTarget().getUUID().equals(player.getUUID())
            ).stream().findFirst().orElse(null);

            if (initialTarget != null) {
                dragon.setTarget(initialTarget);
            }

            serverLevel.playSound(null, player.blockPosition(),
                    SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0f, 1.0f);

            FoodData food = player.getFoodData();
            food.setFoodLevel(Math.max(0, food.getFoodLevel() - 10));

            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        lines.add(Component.literal("§bSummons a random dragon to fight for you"));
        lines.add(Component.literal("§3Dragon despawns after 60 seconds"));
        lines.add(Component.literal("§4Costs half your hunger"));
        lines.add(Component.literal("§860s cooldown"));
        super.appendHoverText(stack, level, lines, flag);
    }
}