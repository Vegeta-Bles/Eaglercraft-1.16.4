package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.enums.JigsawOrientation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class JigsawBlock extends Block implements BlockEntityProvider {
   public static final EnumProperty<JigsawOrientation> ORIENTATION = Properties.ORIENTATION;

   protected JigsawBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(this.stateManager.getDefaultState().with(ORIENTATION, JigsawOrientation.NORTH_UP));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(ORIENTATION);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(ORIENTATION, rotation.getDirectionTransformation().mapJigsawOrientation(state.get(ORIENTATION)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.with(ORIENTATION, mirror.getDirectionTransformation().mapJigsawOrientation(state.get(ORIENTATION)));
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      Direction lv = ctx.getSide();
      Direction lv2;
      if (lv.getAxis() == Direction.Axis.Y) {
         lv2 = ctx.getPlayerFacing().getOpposite();
      } else {
         lv2 = Direction.UP;
      }

      return this.getDefaultState().with(ORIENTATION, JigsawOrientation.byDirections(lv, lv2));
   }

   @Nullable
   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new JigsawBlockEntity();
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      BlockEntity lv = world.getBlockEntity(pos);
      if (lv instanceof JigsawBlockEntity && player.isCreativeLevelTwoOp()) {
         player.openJigsawScreen((JigsawBlockEntity)lv);
         return ActionResult.success(world.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   public static boolean attachmentMatches(Structure.StructureBlockInfo info1, Structure.StructureBlockInfo info2) {
      Direction lv = getFacing(info1.state);
      Direction lv2 = getFacing(info2.state);
      Direction lv3 = getRotation(info1.state);
      Direction lv4 = getRotation(info2.state);
      JigsawBlockEntity.Joint lv5 = JigsawBlockEntity.Joint.byName(info1.tag.getString("joint"))
         .orElseGet(() -> lv.getAxis().isHorizontal() ? JigsawBlockEntity.Joint.ALIGNED : JigsawBlockEntity.Joint.ROLLABLE);
      boolean bl = lv5 == JigsawBlockEntity.Joint.ROLLABLE;
      return lv == lv2.getOpposite() && (bl || lv3 == lv4) && info1.tag.getString("target").equals(info2.tag.getString("name"));
   }

   public static Direction getFacing(BlockState arg) {
      return arg.get(ORIENTATION).getFacing();
   }

   public static Direction getRotation(BlockState arg) {
      return arg.get(ORIENTATION).getRotation();
   }
}
