package com.gtb.gregtechbeyond.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gtb.gregtechbeyond.recipemap.GTBRecipeMaps;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerTieredMachines;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;


@SuppressWarnings("unused")

public class GTBMachines {




    public static MultiblockMachineDefinition PVD_UNIT_MACHINE = REGISTRATE
            .multiblock("pvd_unit", WorkableElectricMultiblockMachine::new)
                    .langValue("Multiblock PVD Unit")
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTBRecipeMaps.PVD_UNIT_RECIPES)
                    .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("~~G~~", "~GGG~", "~CCC~")
                            .aisle("~GGG~", "P~~~P", "P~~~P")
                            .aisle("SCCCC", "CCCCC", "CCCCC")
                            .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                                    .setMinGlobalLimited(7)
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where('S', Predicates.controller(Predicates.blocks(definition.getBlock())))
                            .where('P', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('#', Predicates.any())
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                            GTCEu.id("block/multiblock/processing_array"))
            .hasBER(true)
            .register();

    public static void init() {}



}