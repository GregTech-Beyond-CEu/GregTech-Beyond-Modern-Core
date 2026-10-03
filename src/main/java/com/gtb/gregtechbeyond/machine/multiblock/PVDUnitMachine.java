package com.gtb.gregtechbeyond.machine.multiblock;


import com.gregtechceu.gtceu.api.capability.IEnergyContainer;
import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.transfer.fluid.IFluidHandlerModifiable;

import com.gregtechceu.gtceu.common.data.GTRecipes;
import com.gtb.gregtechbeyond.recipemap.GTBRecipeMaps;
import net.minecraftforge.fluids.FluidStack;

import com.gtb.gregtechbeyond.recipemap.GTBRecipeMaps.*;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class PVDUnitMachine extends WorkableElectricMultiblockMachine {

    protected ConditionalSubscriptionHandler generationSubscription;

    public PVDUnitMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
        this.generationSubscription = new ConditionalSubscriptionHandler(this, this::generateEnergyTick,
                this::isFormed);
    }

    @Override
    protected @NotNull RecipeLogic createRecipeLogic(Object @NotNull... args) {
        return new RecipeLogic(this);
    }

    private void generateEnergyTick() {
        if (isWorkingEnabled() && isFormed()) {
            List<IFluidHandlerModifiable> hatches = getCapabilitiesFlat(IO.IN, FluidRecipeCapability.CAP)
                    .stream()
                    .filter(IFluidHandlerModifiable.class::isInstance)
                    .map(IFluidHandlerModifiable.class::cast)
                    .toList();

            var recipe = GTBRecipeMaps.PVD_UNIT_RECIPES.db().find(this);

            if (recipe == null) {
                voidFluids(hatches);
                return;
            }

            var maxParallels = ParallelLogic.getMaxByInput(this, recipe, Integer.MAX_VALUE, Collections.emptyList());

            double batchBonus = Math.log10(maxParallels) + 1.0;

            assert getCapabilitiesFlat(IO.OUT, EURecipeCapability.CAP).size() ==
                    1 : "There must be exactly 1 dynamo or laser source hatch on the Antimatter Generator";

            getCapabilitiesFlat(IO.OUT, EURecipeCapability.CAP).stream()
                    .filter(IEnergyContainer.class::isInstance)
                    .map(IEnergyContainer.class::cast)
                    .forEach(container -> container.addEnergy(
                            (long) (maxParallels * batchBonus * recipe.getOutputEUt().getTotalEU())));
            voidFluids(hatches);
        }
    }

    private void voidFluids(List<IFluidHandlerModifiable> hatches) {
        for (var hatch : hatches) {
            hatch.setFluidInTank(0, FluidStack.EMPTY);
        }
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        generationSubscription.updateSubscription();
        setRenderState(getRenderState().setValue(RecipeLogic.STATUS_PROPERTY,
                isWorkingEnabled() ? RecipeLogic.Status.WORKING : RecipeLogic.Status.IDLE));
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        generationSubscription.updateSubscription();
        setRenderState(getRenderState().setValue(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE));
    }
}