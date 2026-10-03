package com.gtb.gregtechbeyond.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gtb.gregtechbeyond.GregTechBeyondMod;
import com.gtb.gregtechbeyond.machine.multiblock.PVDUnitMachine;
import com.gtb.gregtechbeyond.recipemap.GTBRecipeMaps;
import com.mojang.logging.LogUtils;

@SuppressWarnings("unused")

public class GTBMachines {

    public static final MultiblockMachineDefinition PVD_UNIT =
            GregTechBeyondMod.REGISTRATE.multiblock("pvd_unit", PVDUnitMachine::new)
                    .langValue("Multiblock PVD Unit")
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTBRecipeMaps.PVD_UNIT_RECIPES)
                    .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("~~G~~", "~GGG~", "~CCC~")
                            .aisle("~GGG~", "P~~~P", "P~~~P")
                            .aisle("SCCCC", "CCCCC", "CCCCC")
                            .where('C', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                                    .setMinGlobalLimited(10)
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where('S', Predicates.controller(Predicates.blocks(definition.getBlock())))
                            .where('G', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('P', Predicates.blocks(GTBlocks.CASING_STEEL_PIPE.get()))
                            .where('~', Predicates.any())
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                            GTCEu.id("block/machines/assembler")
                    )
                    .register();

    public static void init() {
        LogUtils.getLogger().info("GTBMachines.init() called, PVD Unit registered");
    }
}
