package com.upo.createmechanicalconfection.interaction;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.upo.createmechanicalconfection.content.block_entities.MechanicalOvenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation; // 需要这个
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import com.simibubi.create.content.kinetics.mechanicalArm.AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint;


public class MechanicalOvenArmInteraction {

    // 静态内部类 Type，继承 ArmInteractionPointType
    public static class Type extends ArmInteractionPointType {

        public Type(ResourceLocation id) {
            super();
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            // 检查是否是我们的烤箱方块实体
            return level.getBlockEntity(pos) instanceof MechanicalOvenBlockEntity;
        }

        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            // 创建一个标准的 ArmInteractionPoint 实例
            // 这个实例内部已经有了 insert 和 extract 方法，它们会使用 IItemHandler
            ArmInteractionPoint point = new ArmInteractionPoint(this, level, pos, state);

            // 通过覆盖 getInteractionPositionVector() 或直接设置偏移来指定交互位置
            // ArmInteractionPoint 基类有一个 getInteractionPositionVector() 方法，
            // 它默认返回 VecHelper.getCenterOf(pos)。
            // 如果我们想自定义，可以创建一个轻微的子类来覆盖它，或者如果 ArmInteractionPoint 提供了 setOffset 方法。
            // 从你提供的 ArmInteractionPoint 源码看，它没有 setInteractionOffset 方法，
            // 但它调用 this.getInteractionPositionVector()。
            // 所以，我们需要让我们的 Type 创建一个 ArmInteractionPoint 的匿名子类或一个新子类来覆盖 getInteractionPositionVector()。

            // 方案A: 创建一个匿名子类来覆盖交互位置
            return new ArmInteractionPoint(this, level, pos, state) {
                @Override
                protected Vec3 getInteractionPositionVector() {
                    // 交互点在方块的顶部中心
                    // pos 是方块的世界坐标，这个方法应该返回相对于该方块的局部偏移后的世界坐标
                    // 或者，更常见的是返回一个相对于方块模型原点 (0,0,0) 的偏移 Vec3，由 ArmInteractionPoint 内部处理。
                    // 查看 ArmInteractionPoint 源码，getInteractionPositionVector() 默认返回 VecHelper.getCenterOf(pos)，
                    // 并在 getTargetAngles 中使用。
                    // 为了精确控制，我们希望它指向顶部。
                    return Vec3.atCenterOf(pos).add(0, 0.5, 0); // 指向顶部中心 (Y+0.5 from center)
                    // 或者直接用绝对坐标：
                    // return new Vec3(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5);
                }

                // （可选）覆盖 getInteractionDirection 来改变手臂接近的方向
                // @Override
                // protected Direction getInteractionDirection() {
                //     return Direction.UP; // 例如，手臂从上方接近
                // }
            };
        }
        // getPriority() 可以保持默认
    }
}

