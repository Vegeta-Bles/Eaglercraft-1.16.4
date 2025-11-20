package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.JigsawBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class JigsawBlockEntity extends BlockEntity {
   private Identifier name = new Identifier("empty");
   private Identifier target = new Identifier("empty");
   private Identifier pool = new Identifier("empty");
   private JigsawBlockEntity.Joint joint = JigsawBlockEntity.Joint.ROLLABLE;
   private String finalState = "minecraft:air";

   public JigsawBlockEntity(BlockEntityType<?> _snowman) {
      super(_snowman);
   }

   public JigsawBlockEntity() {
      this(BlockEntityType.JIGSAW);
   }

   public Identifier getName() {
      return this.name;
   }

   public Identifier getTarget() {
      return this.target;
   }

   public Identifier getPool() {
      return this.pool;
   }

   public String getFinalState() {
      return this.finalState;
   }

   public JigsawBlockEntity.Joint getJoint() {
      return this.joint;
   }

   public void setAttachmentType(Identifier value) {
      this.name = value;
   }

   public void setTargetPool(Identifier target) {
      this.target = target;
   }

   public void setPool(Identifier pool) {
      this.pool = pool;
   }

   public void setFinalState(String finalState) {
      this.finalState = finalState;
   }

   public void setJoint(JigsawBlockEntity.Joint joint) {
      this.joint = joint;
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.putString("name", this.name.toString());
      tag.putString("target", this.target.toString());
      tag.putString("pool", this.pool.toString());
      tag.putString("final_state", this.finalState);
      tag.putString("joint", this.joint.asString());
      return tag;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.name = new Identifier(tag.getString("name"));
      this.target = new Identifier(tag.getString("target"));
      this.pool = new Identifier(tag.getString("pool"));
      this.finalState = tag.getString("final_state");
      this.joint = JigsawBlockEntity.Joint.byName(tag.getString("joint"))
         .orElseGet(() -> JigsawBlock.getFacing(state).getAxis().isHorizontal() ? JigsawBlockEntity.Joint.ALIGNED : JigsawBlockEntity.Joint.ROLLABLE);
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 12, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   public void generate(ServerWorld world, int maxDepth, boolean keepJigsaws) {
      ChunkGenerator _snowman = world.getChunkManager().getChunkGenerator();
      StructureManager _snowmanx = world.getStructureManager();
      StructureAccessor _snowmanxx = world.getStructureAccessor();
      Random _snowmanxxx = world.getRandom();
      BlockPos _snowmanxxxx = this.getPos();
      List<PoolStructurePiece> _snowmanxxxxx = Lists.newArrayList();
      Structure _snowmanxxxxxx = new Structure();
      _snowmanxxxxxx.saveFromWorld(world, _snowmanxxxx, new BlockPos(1, 1, 1), false, null);
      StructurePoolElement _snowmanxxxxxxx = new SinglePoolElement(_snowmanxxxxxx);
      PoolStructurePiece _snowmanxxxxxxxx = new PoolStructurePiece(_snowmanx, _snowmanxxxxxxx, _snowmanxxxx, 1, BlockRotation.NONE, new BlockBox(_snowmanxxxx, _snowmanxxxx));
      StructurePoolBasedGenerator.method_27230(world.getRegistryManager(), _snowmanxxxxxxxx, maxDepth, PoolStructurePiece::new, _snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx);

      for (PoolStructurePiece _snowmanxxxxxxxxx : _snowmanxxxxx) {
         _snowmanxxxxxxxxx.method_27236(world, _snowmanxx, _snowman, _snowmanxxx, BlockBox.infinite(), _snowmanxxxx, keepJigsaws);
      }
   }

   public static enum Joint implements StringIdentifiable {
      ROLLABLE("rollable"),
      ALIGNED("aligned");

      private final String name;

      private Joint(String name) {
         this.name = name;
      }

      @Override
      public String asString() {
         return this.name;
      }

      public static Optional<JigsawBlockEntity.Joint> byName(String name) {
         return Arrays.stream(values()).filter(_snowmanx -> _snowmanx.asString().equals(name)).findFirst();
      }
   }
}
