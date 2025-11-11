import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ann implements DynamicMBean {
   private static final Logger a = LogManager.getLogger();
   private final MinecraftServer b;
   private final MBeanInfo c;
   private final Map<String, ann.a> d = Stream.of(
         new ann.a("tickTimes", this::b, "Historical tick times (ms)", long[].class),
         new ann.a("averageTickTime", this::a, "Current average tick time (ms)", long.class)
      )
      .collect(Collectors.toMap(var0 -> var0.a, Function.identity()));

   private ann(MinecraftServer var1) {
      this.b = _snowman;
      MBeanAttributeInfo[] _snowman = this.d.values().stream().map(var0 -> var0.a()).toArray(MBeanAttributeInfo[]::new);
      this.c = new MBeanInfo(ann.class.getSimpleName(), "metrics for dedicated server", _snowman, null, null, new MBeanNotificationInfo[0]);
   }

   public static void a(MinecraftServer var0) {
      try {
         ManagementFactory.getPlatformMBeanServer().registerMBean(new ann(_snowman), new ObjectName("net.minecraft.server:type=Server"));
      } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException var2) {
         a.warn("Failed to initialise server as JMX bean", var2);
      }
   }

   private float a() {
      return this.b.aO();
   }

   private long[] b() {
      return this.b.h;
   }

   @Nullable
   @Override
   public Object getAttribute(String var1) {
      ann.a _snowman = this.d.get(_snowman);
      return _snowman == null ? null : _snowman.b.get();
   }

   @Override
   public void setAttribute(Attribute var1) {
   }

   @Override
   public AttributeList getAttributes(String[] var1) {
      List<Attribute> _snowman = Arrays.stream(_snowman)
         .map(this.d::get)
         .filter(Objects::nonNull)
         .map(var0 -> new Attribute(var0.a, var0.b.get()))
         .collect(Collectors.toList());
      return new AttributeList(_snowman);
   }

   @Override
   public AttributeList setAttributes(AttributeList var1) {
      return new AttributeList();
   }

   @Nullable
   @Override
   public Object invoke(String var1, Object[] var2, String[] var3) {
      return null;
   }

   @Override
   public MBeanInfo getMBeanInfo() {
      return this.c;
   }

   static final class a {
      private final String a;
      private final Supplier<Object> b;
      private final String c;
      private final Class<?> d;

      private a(String var1, Supplier<Object> var2, String var3, Class<?> var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      private MBeanAttributeInfo a() {
         return new MBeanAttributeInfo(this.a, this.d.getSimpleName(), this.c, true, false, false);
      }
   }
}
