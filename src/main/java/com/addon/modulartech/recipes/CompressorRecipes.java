package com.addon.modulartech.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.HashMap;
import java.util.Map;

public class CompressorRecipes {
    private static final CompressorRecipes instance = new CompressorRecipes();
    private final Map<ItemStack, ItemStack> recipeList = new HashMap<>();

    public static CompressorRecipes getInstance() {
        return instance;
    }

    private CompressorRecipes() {
        // Add recipes here
        addRecipe(new ItemStack(Items.coal), new ItemStack(Items.diamond));
        addRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.stone));
    }

    public void addRecipe(ItemStack input, ItemStack output) {
        recipeList.put(input, output);
    }

    public ItemStack getRecipeResult(ItemStack input) {
        for (Map.Entry<ItemStack, ItemStack> entry : recipeList.entrySet()) {
            if (entry.getKey().isItemEqual(input)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<ItemStack, ItemStack> getRecipeList() {
        return recipeList;
    }
}
