package net.karnx.icenfirefactionsweapons.datagen;

import net.karnx.icenfirefactionsweapons.IceNFireFactionsWeapons;
import net.karnx.icenfirefactionsweapons.block.ModBlocks;
import net.karnx.icenfirefactionsweapons.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> p_250172_, RecipeCategory p_250588_, ItemLike p_251868_, float p_250789_, int p_252144_, String p_251687_) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, p_250172_, p_250588_, p_251868_, p_250789_, p_252144_, p_251687_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> p_251504_, RecipeCategory p_248846_, ItemLike p_249735_, float p_248783_, int p_250303_, String p_251984_) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, p_251504_, p_248846_, p_249735_, p_248783_, p_250303_, p_251984_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> p_251817_, List<ItemLike> p_249619_, RecipeCategory p_251154_, ItemLike p_250066_, float p_251871_, int p_251316_, String p_251450_, String p_249236_) {
        for(ItemLike itemlike : p_249619_) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), p_251154_, p_250066_, p_251871_, p_251316_, p_251817_).group(p_251450_).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(p_250791_, IceNFireFactionsWeapons.MODID + ":" + getItemName(p_250066_) + p_249236_ + "_" + getItemName(itemlike));
        }

    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ANCIENTITE_BLOCK.get())
                .pattern("sss")
                .pattern("sss")
                .pattern("sss")
                .define('s', ModItems.ANCIENTITE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_BOOTS.get())
                .pattern("   ")
                .pattern("s s")
                .pattern("s s")
                .define('s', ModItems.ANCIENTITE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_LEGGINGS.get())
                .pattern("sss")
                .pattern("s s")
                .pattern("s s")
                .define('s', ModItems.ANCIENTITE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_CHESTPLATE.get())
                .pattern("s s")
                .pattern("sss")
                .pattern("sss")
                .define('s', ModItems.ANCIENTITE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_HELMET.get())
                .pattern("sss")
                .pattern("s s")
                .pattern("   ")
                .define('s', ModItems.ANCIENTITE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
//wlsyaer
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SLAYER_BOOTS.get())
                .pattern("   ")
                .pattern("sfs")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_FANG.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SLAYER_LEGGINGS.get())
                .pattern("sss")
                .pattern("sfs")
                .pattern("fff")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_FANG.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SLAYER_CHESTPLATE.get())
                .pattern("f f")
                .pattern("sss")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_FANG.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SLAYER_HELMET.get())
                .pattern("sfs")
                .pattern("sfs")
                .pattern(" f ")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_FANG.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        //tamer
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_TAMER_BOOTS.get())
                .pattern("   ")
                .pattern("sfs")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_SCALE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_TAMER_LEGGINGS.get())
                .pattern("sss")
                .pattern("sfs")
                .pattern("fff")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_SCALE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_TAMER_CHESTPLATE.get())
                .pattern("f f")
                .pattern("sss")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_SCALE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_TAMER_HELMET.get())
                .pattern("sfs")
                .pattern("sfs")
                .pattern(" f ")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_SCALE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);

        //sanctum
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SANCTUM_BOOTS.get())
                .pattern("   ")
                .pattern("sfs")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_BONE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SANCTUM_LEGGINGS.get())
                .pattern("sss")
                .pattern("sfs")
                .pattern("fff")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_BONE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SANCTUM_CHESTPLATE.get())
                .pattern("f f")
                .pattern("sss")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_BONE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_SANCTUM_HELMET.get())
                .pattern("sfs")
                .pattern("sfs")
                .pattern(" f ")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_BONE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);

        //ACCORD
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_ACCORD_BOOTS.get())
                .pattern("   ")
                .pattern("sfs")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_EYE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_ACCORD_LEGGINGS.get())
                .pattern("sss")
                .pattern("sfs")
                .pattern("fff")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_EYE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_ACCORD_CHESTPLATE.get())
                .pattern("f f")
                .pattern("sss")
                .pattern("sfs")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_EYE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.ANCIENTITE_ACCORD_HELMET.get())
                .pattern("sfs")
                .pattern("sfs")
                .pattern(" f ")
                .define('s', ModItems.ANCIENTITE.get()).define('f', ModItems.ANCIENT_EYE.get())
                .unlockedBy(getHasName(ModItems.ANCIENTITE.get()), has(ModItems.ANCIENTITE.get()))
                .save(p_251297_);



    }
}
