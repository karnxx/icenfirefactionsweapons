package net.karnx.icenfirefactionsweapons.item.factitems;

import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.karnx.icenfirefactionsweapons.util.AncientEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public class SanctumKey extends Item {
    public SanctumKey(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext p_41427_) {
        if(!p_41427_.getLevel().isClientSide){
            BlockPos position = p_41427_.getClickedPos();
            BlockState state = p_41427_.getLevel().getBlockState(position);

            if(state.getBlock() == ModBlocks.ANCIENT_VAULT.get()) {
                if(!p_41427_.getLevel().isClientSide()) {
                    p_41427_.getLevel().playSound(null, position, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.MASTER, 1f, 1f);
                    AncientEvent.trigger(p_41427_.getPlayer());
                    p_41427_.getItemInHand().setCount(0);

                }
            }


        }

        return super.useOn(p_41427_);
    }
}
