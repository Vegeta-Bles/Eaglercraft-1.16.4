package net.minecraft.util.math;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanArrayList;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.enums.JigsawOrientation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

public enum DirectionTransformation implements StringIdentifiable {
   IDENTITY("identity", AxisTransformation.P123, false, false, false),
   ROT_180_FACE_XY("rot_180_face_xy", AxisTransformation.P123, true, true, false),
   ROT_180_FACE_XZ("rot_180_face_xz", AxisTransformation.P123, true, false, true),
   ROT_180_FACE_YZ("rot_180_face_yz", AxisTransformation.P123, false, true, true),
   ROT_120_NNN("rot_120_nnn", AxisTransformation.P231, false, false, false),
   ROT_120_NNP("rot_120_nnp", AxisTransformation.P312, true, false, true),
   ROT_120_NPN("rot_120_npn", AxisTransformation.P312, false, true, true),
   ROT_120_NPP("rot_120_npp", AxisTransformation.P231, true, false, true),
   ROT_120_PNN("rot_120_pnn", AxisTransformation.P312, true, true, false),
   ROT_120_PNP("rot_120_pnp", AxisTransformation.P231, true, true, false),
   ROT_120_PPN("rot_120_ppn", AxisTransformation.P231, false, true, true),
   ROT_120_PPP("rot_120_ppp", AxisTransformation.P312, false, false, false),
   ROT_180_EDGE_XY_NEG("rot_180_edge_xy_neg", AxisTransformation.P213, true, true, true),
   ROT_180_EDGE_XY_POS("rot_180_edge_xy_pos", AxisTransformation.P213, false, false, true),
   ROT_180_EDGE_XZ_NEG("rot_180_edge_xz_neg", AxisTransformation.P321, true, true, true),
   ROT_180_EDGE_XZ_POS("rot_180_edge_xz_pos", AxisTransformation.P321, false, true, false),
   ROT_180_EDGE_YZ_NEG("rot_180_edge_yz_neg", AxisTransformation.P132, true, true, true),
   ROT_180_EDGE_YZ_POS("rot_180_edge_yz_pos", AxisTransformation.P132, true, false, false),
   ROT_90_X_NEG("rot_90_x_neg", AxisTransformation.P132, false, false, true),
   ROT_90_X_POS("rot_90_x_pos", AxisTransformation.P132, false, true, false),
   ROT_90_Y_NEG("rot_90_y_neg", AxisTransformation.P321, true, false, false),
   ROT_90_Y_POS("rot_90_y_pos", AxisTransformation.P321, false, false, true),
   ROT_90_Z_NEG("rot_90_z_neg", AxisTransformation.P213, false, true, false),
   ROT_90_Z_POS("rot_90_z_pos", AxisTransformation.P213, true, false, false),
   INVERSION("inversion", AxisTransformation.P123, true, true, true),
   INVERT_X("invert_x", AxisTransformation.P123, true, false, false),
   INVERT_Y("invert_y", AxisTransformation.P123, false, true, false),
   INVERT_Z("invert_z", AxisTransformation.P123, false, false, true),
   ROT_60_REF_NNN("rot_60_ref_nnn", AxisTransformation.P312, true, true, true),
   ROT_60_REF_NNP("rot_60_ref_nnp", AxisTransformation.P231, true, false, false),
   ROT_60_REF_NPN("rot_60_ref_npn", AxisTransformation.P231, false, false, true),
   ROT_60_REF_NPP("rot_60_ref_npp", AxisTransformation.P312, false, false, true),
   ROT_60_REF_PNN("rot_60_ref_pnn", AxisTransformation.P231, false, true, false),
   ROT_60_REF_PNP("rot_60_ref_pnp", AxisTransformation.P312, true, false, false),
   ROT_60_REF_PPN("rot_60_ref_ppn", AxisTransformation.P312, false, true, false),
   ROT_60_REF_PPP("rot_60_ref_ppp", AxisTransformation.P231, true, true, true),
   SWAP_XY("swap_xy", AxisTransformation.P213, false, false, false),
   SWAP_YZ("swap_yz", AxisTransformation.P132, false, false, false),
   SWAP_XZ("swap_xz", AxisTransformation.P321, false, false, false),
   SWAP_NEG_XY("swap_neg_xy", AxisTransformation.P213, true, true, false),
   SWAP_NEG_YZ("swap_neg_yz", AxisTransformation.P132, false, true, true),
   SWAP_NEG_XZ("swap_neg_xz", AxisTransformation.P321, true, false, true),
   ROT_90_REF_X_NEG("rot_90_ref_x_neg", AxisTransformation.P132, true, false, true),
   ROT_90_REF_X_POS("rot_90_ref_x_pos", AxisTransformation.P132, true, true, false),
   ROT_90_REF_Y_NEG("rot_90_ref_y_neg", AxisTransformation.P321, true, true, false),
   ROT_90_REF_Y_POS("rot_90_ref_y_pos", AxisTransformation.P321, false, true, true),
   ROT_90_REF_Z_NEG("rot_90_ref_z_neg", AxisTransformation.P213, false, true, true),
   ROT_90_REF_Z_POS("rot_90_ref_z_pos", AxisTransformation.P213, true, false, true);

   private final Matrix3f matrix;
   private final String name;
   @Nullable
   private Map<Direction, Direction> mappings;
   private final boolean flipX;
   private final boolean flipY;
   private final boolean flipZ;
   private final AxisTransformation axisTransformation;
   private static final DirectionTransformation[][] COMBINATIONS = Util.make(
      new DirectionTransformation[values().length][values().length],
      _snowman -> {
         Map<Pair<AxisTransformation, BooleanList>, DirectionTransformation> _snowmanx = Arrays.stream(values())
            .collect(Collectors.toMap(_snowmanxx -> Pair.of(_snowmanxx.axisTransformation, _snowmanxx.getAxisFlips()), _snowmanxx -> _snowmanxx));

         for (DirectionTransformation _snowmanxx : values()) {
            for (DirectionTransformation _snowmanxxx : values()) {
               BooleanList _snowmanxxxx = _snowmanxx.getAxisFlips();
               BooleanList _snowmanxxxxx = _snowmanxxx.getAxisFlips();
               AxisTransformation _snowmanxxxxxx = _snowmanxxx.axisTransformation.prepend(_snowmanxx.axisTransformation);
               BooleanArrayList _snowmanxxxxxxx = new BooleanArrayList(3);

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 3; _snowmanxxxxxxxx++) {
                  _snowmanxxxxxxx.add(_snowmanxxxx.getBoolean(_snowmanxxxxxxxx) ^ _snowmanxxxxx.getBoolean(_snowmanxx.axisTransformation.map(_snowmanxxxxxxxx)));
               }

               _snowman[_snowmanxx.ordinal()][_snowmanxxx.ordinal()] = _snowmanx.get(Pair.of(_snowmanxxxxxx, _snowmanxxxxxxx));
            }
         }
      }
   );
   private static final DirectionTransformation[] INVERSES = Arrays.stream(values())
      .map(_snowman -> Arrays.stream(values()).filter(_snowmanxx -> _snowman.prepend(_snowmanxx) == IDENTITY).findAny().get())
      .toArray(DirectionTransformation[]::new);

   private DirectionTransformation(String name, AxisTransformation axisTransformation, boolean flipX, boolean flipY, boolean flipZ) {
      this.name = name;
      this.flipX = flipX;
      this.flipY = flipY;
      this.flipZ = flipZ;
      this.axisTransformation = axisTransformation;
      this.matrix = new Matrix3f();
      this.matrix.a00 = flipX ? -1.0F : 1.0F;
      this.matrix.a11 = flipY ? -1.0F : 1.0F;
      this.matrix.a22 = flipZ ? -1.0F : 1.0F;
      this.matrix.multiply(axisTransformation.getMatrix());
   }

   private BooleanList getAxisFlips() {
      return new BooleanArrayList(new boolean[]{this.flipX, this.flipY, this.flipZ});
   }

   public DirectionTransformation prepend(DirectionTransformation transformation) {
      return COMBINATIONS[this.ordinal()][transformation.ordinal()];
   }

   @Override
   public String toString() {
      return this.name;
   }

   @Override
   public String asString() {
      return this.name;
   }

   public Direction map(Direction direction) {
      if (this.mappings == null) {
         this.mappings = Maps.newEnumMap(Direction.class);

         for (Direction _snowman : Direction.values()) {
            Direction.Axis _snowmanx = _snowman.getAxis();
            Direction.AxisDirection _snowmanxx = _snowman.getDirection();
            Direction.Axis _snowmanxxx = Direction.Axis.values()[this.axisTransformation.map(_snowmanx.ordinal())];
            Direction.AxisDirection _snowmanxxxx = this.shouldFlipDirection(_snowmanxxx) ? _snowmanxx.getOpposite() : _snowmanxx;
            Direction _snowmanxxxxx = Direction.from(_snowmanxxx, _snowmanxxxx);
            this.mappings.put(_snowman, _snowmanxxxxx);
         }
      }

      return this.mappings.get(direction);
   }

   public boolean shouldFlipDirection(Direction.Axis axis) {
      switch (axis) {
         case X:
            return this.flipX;
         case Y:
            return this.flipY;
         case Z:
         default:
            return this.flipZ;
      }
   }

   public JigsawOrientation mapJigsawOrientation(JigsawOrientation orientation) {
      return JigsawOrientation.byDirections(this.map(orientation.getFacing()), this.map(orientation.getRotation()));
   }
}
