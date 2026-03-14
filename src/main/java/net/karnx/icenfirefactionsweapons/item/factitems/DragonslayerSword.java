package net.karnx.icenfirefactionsweapons.item.factitems;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragonslayerSword extends SwordItem {

    public DragonslayerSword(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target instanceof EntityDragonBase dragon && attacker instanceof Player player) {
            float bonusDamage = dragon.getMaxHealth() * 0.15f;
            dragon.hurt(dragon.damageSources().playerAttack(player), bonusDamage);
            float heal = bonusDamage * 0.30f;
            player.heal(heal);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
        lines.add(Component.literal("§cDeals 15% of dragon's max health as bonus damage"));
        lines.add(Component.literal("§4Heals 30% of bonus damage dealt"));
        super.appendHoverText(stack, level, lines, flag);
    }
}