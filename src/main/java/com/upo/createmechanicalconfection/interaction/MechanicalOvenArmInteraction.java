package com.upo.createmechanicalconfection.interaction;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.upo.createmechanicalconfection.content.block_entities.MechanicalOvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


public class MechanicalOvenArmInteraction {

    public static class Type extends ArmInteractionPointType {

        public Type(ResourceLocation id) {
            super();
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof MechanicalOvenBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {

            ArmInteractionPoint point = new ArmInteractionPoint(this, level, pos, state);
            return new ArmInteractionPoint(this, level, pos, state) {
                @Override
                protected Vec3 getInteractionPositionVector() {

                    return Vec3.atCenterOf(pos).add(0, 0.5, 0);

                }
            };
        }
    }
}

