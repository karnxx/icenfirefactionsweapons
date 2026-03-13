package net.karnx.icenfirefactionsweapons.item;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    ANCIENTITE("ancientite", 50, new int[]{5,9,11,5}, 30, SoundEvents.ARMOR_EQUIP_NETHERITE, 5f, 0.3f, () -> Ingredient.of(ModItems.ANCIENTITE.get())),
    ANCIENTITE_SLAYER("ancientite_slayer", 50, new int[]{6,10,12,6}, 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 6f, 0.5f, () -> Ingredient.of(ModItems.ANCIENTITE.get())),
    ANCIENTITE_TAMER("ancientite_tamer", 50, new int[]{4,8,10,4}, 40, SoundEvents.ARMOR_EQUIP_NETHERITE, 4f, 0.2f, () -> Ingredient.of(ModItems.ANCIENTITE.get())),
    ANCIENTITE_SANCTUM("ancientite_sanctum", 50, new int[]{3,7,9,3}, 59, SoundEvents.ARMOR_EQUIP_NETHERITE, 3f, 0.1f, () -> Ingredient.of(ModItems.ANCIENTITE.get())),
    ANCIENTITE_ACCORD("ancientite_accord", 50, new int[]{5,9,11,5}, 35, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.5f, 0.35f, () -> Ingredient.of(ModItems.ANCIENTITE.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {13, 15, 16, 11};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue,
                      SoundEvent equipSound, float toughness, float knockbackResistance,
                      Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionAmounts[type.ordinal()];
    }

    @Override
    public int getEnchantmentValue() { return enchantmentValue; }

    @Override
    public SoundEvent getEquipSound() { return equipSound; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient.get(); }

    @Override
    public String getName() { return IceNFireFactionsWeapons.MODID + ":" + name; }

    @Override
    public float getToughness() { return toughness; }

    @Override
    public float getKnockbackResistance() { return knockbackResistance; }
}