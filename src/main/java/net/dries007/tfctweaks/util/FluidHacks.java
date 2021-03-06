/*
 * Copyright (c) 2015 Dries007
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted (subject to the limitations in the
 * disclaimer below) provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 *  * Neither the name of Dries007 nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE
 * GRANTED BY THIS LICENSE.  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.dries007.tfctweaks.util;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.google.common.collect.BiMap;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.dries007.tfctweaks.TFCTweaks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * @author Dries007
 */
public class FluidHacks
{
    public static boolean makeAllWaterFTCWater;
    public static boolean makeAllLavaFTCLava;

    public static final Fluid OLD_WATER_FLUID = FluidRegistry.WATER;
    public static final Fluid OLD_LAVA_FLUID = FluidRegistry.LAVA;
    public static final Block OLD_WATER_BLOCK = Blocks.water;
    public static final Block OLD_FLOWING_WATER_BLOCK = Blocks.flowing_water;
    public static final Block OLD_LAVA_BLOCK = Blocks.lava;
    public static final Block OLD_FLOWINING_LAVA_BLOCK = Blocks.flowing_lava;

    private static boolean magic = false;
    private static BiMap<String, Fluid> fluidsMap = ReflectionHelper.getPrivateValue(FluidRegistry.class, null, "fluids");

    private FluidHacks()
    {

    }

    public static void construction()
    {
        // to be called asap, so that the fields are hopefully still intact when we load.
    }

    public static void doTheMagic()
    {
        if (magic) throw new IllegalStateException("You can't magic twice.");
        magic = true;

        // do the hack
        if (makeAllWaterFTCWater)
        {
            Helper.setFinalStatic(ReflectionHelper.findField(FluidRegistry.class, "WATER"), TFCFluids.FRESHWATER);
            Helper.setFinalStatic(ReflectionHelper.findField(Blocks.class, "field_150355_j", "water"), TFCBlocks.freshWaterStationary);
            Helper.setFinalStatic(ReflectionHelper.findField(Blocks.class, "field_150358_i", "flowing_water"), TFCBlocks.freshWater);
        }
        else FluidRegistry.registerFluid(OLD_WATER_FLUID);

        if (makeAllLavaFTCLava)
        {
            Helper.setFinalStatic(ReflectionHelper.findField(FluidRegistry.class, "LAVA"), TFCFluids.LAVA);
            Helper.setFinalStatic(ReflectionHelper.findField(Blocks.class, "field_150353_l", "lava"), TFCBlocks.lavaStationary);
            Helper.setFinalStatic(ReflectionHelper.findField(Blocks.class, "field_150356_k", "flowing_lava"), TFCBlocks.lava);
        }
        else FluidRegistry.registerFluid(OLD_LAVA_FLUID);

        TFCTweaks.log.info(Blocks.lava == TFCBlocks.lavaStationary);
        TFCTweaks.log.info(Blocks.flowing_lava == TFCBlocks.lava);
        TFCTweaks.log.info(Blocks.water == TFCBlocks.freshWaterStationary);
        TFCTweaks.log.info(Blocks.flowing_water == TFCBlocks.freshWater);

        ReflectionHelper.setPrivateValue(FluidRegistry.class, null, null, "fluidBlocks");
    }
}
