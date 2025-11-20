package net.minecraft.util.profiler;

public final class ProfilerTiming implements Comparable<ProfilerTiming> {
   public final double parentSectionUsagePercentage;
   public final double totalUsagePercentage;
   public final long visitCount;
   public final String name;

   public ProfilerTiming(String name, double parentUsagePercentage, double totalUsagePercentage, long visitCount) {
      this.name = name;
      this.parentSectionUsagePercentage = parentUsagePercentage;
      this.totalUsagePercentage = totalUsagePercentage;
      this.visitCount = visitCount;
   }

   public int compareTo(ProfilerTiming _snowman) {
      if (_snowman.parentSectionUsagePercentage < this.parentSectionUsagePercentage) {
         return -1;
      } else {
         return _snowman.parentSectionUsagePercentage > this.parentSectionUsagePercentage ? 1 : _snowman.name.compareTo(this.name);
      }
   }

   public int getColor() {
      return (this.name.hashCode() & 11184810) + 4473924;
   }
}
