package net.karnx.icenfirefactionsweapons.item.factitems;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AnnihilatorRay extends Item {

    public AnnihilatorRay() {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(500)
                .rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && !player.getCooldowns().isOnCooldown(this)) {
            Vec3 pos = player.position();
            AnnihilatorRayHandler.FIRING.put(player.getUUID(), 100);
            AnnihilatorRayHandler.LOCKED_POS.put(player.getUUID(), pos);
            player.getCooldowns().addCooldown(this, 160);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}