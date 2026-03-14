package net.karnx.icenfirefactionsweapons.loot;

import com.mojang.serialization.Codec;
import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static  final DeferredRegister<Codec<?extends IGlobalLootModifier>> LOOT_MODIFIER_STABILIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, IceNFireFactionsWeapons.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIER_STABILIZERS.register("add_item", AddItemModifiers.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_STABILIZERS.register(eventBus);
    }
}
