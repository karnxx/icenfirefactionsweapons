package net.karnx.icenfirefactionsweapons.item.factitems;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class AncientStaff extends Item {

    public AncientStaff() {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && !player.getCooldowns().isOnCooldown(this)) {
            AncientStaffHandler.trigger(player);
            player.getCooldowns().addCooldown(this, 200);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}