package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = IceNFireFactionsWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        ExistingFileHelper exfh = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookprv = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new ModRecipeProvider(out));
        gen.addProvider(event.includeServer(), ModLootTableProvider.create(out));
        gen.addProvider(event.includeClient(), new ModBlockStateProvider(out, exfh));
        gen.addProvider(event.includeClient(), new ModItemModelProvider(out, exfh));

        ModBlockTagGenerator blocke = gen.addProvider(event.includeServer(), new ModBlockTagGenerator(out, lookprv,exfh));
        gen.addProvider(event.includeServer(), new ModItemTagGenerator(out, lookprv, blocke.contentsGetter(), exfh));
    }
}
