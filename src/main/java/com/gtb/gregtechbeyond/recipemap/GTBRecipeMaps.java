package com.gtb.gregtechbeyond.recipemap;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;


public class GTBRecipeMaps {

    public static final GTRecipeType PVD_UNIT_RECIPES = GTRecipeTypes.register(
                    "pvd_unit", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(2, 1, 1, 0)   // item in/out, fluid in/out; adjust to taste
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static void init() {}

}
