package com.gtb.gregtechbeyond.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.util.TooltipHelper;
import com.gregtechceu.gtceu.common.data.GCYMBlocks;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.multiblock.part.EnergyHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.LaserHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ParallelHatchPartMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import appeng.core.definitions.AEBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.capability.recipe.IO.IN;
import static com.gregtechceu.gtceu.api.capability.recipe.IO.OUT;
import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerTieredMachines;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableTieredHullMachineModel;

import static com.gtb.gregtechbeyond.block*
import static com.gregtechceu.gtceu.common.data;


@SuppressWarnings("unused")

public class GTBMachines {



    public static MultiblockMachineDefinition PRISMATIC_CRUCIBLE = REGISTRATE
            .multiblock("prismatic_crucible", PrismaticCrucibleMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeTypes(MoniRecipeTypes.CHROMATIC_PROCESSING, MoniRecipeTypes.CHROMATIC_TRANSCENDENCE)
            .recipeModifiers(GTRecipeModifiers.OC_NON_PERFECT)
            .appearanceBlock(MoniBlocks.DIMENSIONAL_STABILIZATION_NETHERITE_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    // spotless:off
                    .aisle("~~G~~", "~GGG~", "~CCC~")
                    .aisle("~GGG~", "P~~~P", "P~~~P")
                    .aisle("SCCCC", "CCCCC", "CCCCC")
                    // spotless:on
                    .where("C",
                            Predicates.blocks(MetaBlocks.CASING_STAINLESS_CLEAN)
                                    .setMinGlobalLimited(5)
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(1)
                                            .setMinGlobalLimited(1)
                                            .setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                    .where("l", Predicates.blocks(MoniBlocks.DIMENSIONAL_STABILIZATION_NETHERITE_CASING.get()))
                    .where("C", Predicates.blocks(MoniBlocks.CHROMODYNAMIC_CONDUCTION_CASING.get()))
                    .where("S", Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where("P", Predicates.blocks(MoniBlocks.PRISMATIC_FOCUS.get()))
                    .where("F", Predicates.frames(GTMaterials.Neutronium))
                    .where("#", Predicates.any())
                    .build())
            .tooltipBuilder(PRISMATIC_TOOLTIPS)
            .additionalDisplay(MoniMachines.currentColorDisplayInfo())
            .modelProperty(RecipeLogic.STATUS_PROPERTY, RecipeLogic.Status.IDLE)
            .model(GTMachineModels
                    .createWorkableCasingMachineModel(MoniLabs.id("block/casing/netherite"),
                            GTCEu.id("block/multiblock/processing_array"))
                    .andThen(b -> b.addDynamicRenderer(MoniDynamicRenderHelper::createPrismacLaserRender)))
            .hasBER(true)
            .register();



}