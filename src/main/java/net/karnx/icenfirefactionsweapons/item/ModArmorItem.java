package net.karnx.icenfirefactionsweapons.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>()
                    .put(ModArmorMaterials.ANCIENTITE, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 1, false, false, true))
                    .put(ModArmorMaterials.ANCIENTITE_ACCORD, new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 3, false, false, true))
                    .put(ModArmorMaterials.ANCIENTITE_SANCTUM, new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 3, false, false, true))
                    .put(ModArmorMaterials.ANCIENTITE_SLAYER, new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 3, false, false, true))
                    .put(ModArmorMaterials.ANCIENTITE_TAMER, new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 3, false, false, true))
                    .build();

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide()) {
            if (hasFullSuitOfArmor(player)) {
                evaluateArmorEffects(player);
            }
        }
    }

    private void addStatusForMaterial(Player player, ArmorMaterial material, MobEffectInstance effect) {
        boolean hasPlayerEffect = player.hasEffect(effect.getEffect());

        if (hasCorrectArmorOn(material, player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(effect));
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial material = entry.getKey();
            MobEffectInstance effect = entry.getValue();

            if (hasCorrectArmorOn(material, player)) {
                addStatusForMaterial(player, material, effect);
            }
        }
    }

    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if (!(armorStack.getItem() instanceof ArmorItem armorItem)) {
                return false;
            }

            if (armorItem.getMaterial() != material) {
                return false;
            }
        }
        return true;
    }

    private boolean hasFullSuitOfArmor(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() &&
                !leggings.isEmpty() &&
                !chestplate.isEmpty() &&
                !helmet.isEmpty();
    }
}