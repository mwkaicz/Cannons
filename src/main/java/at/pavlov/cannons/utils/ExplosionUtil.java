package at.pavlov.cannons.utils;

import net.minecraft.server.v1_8_R2.EntityTNTPrimed;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.event.entity.ExplosionPrimeEvent;

/**
 *
 * @author mwkaicz <mwkaicz@gmail.com>
 */
public class ExplosionUtil {
    
    public static boolean createExplosion(Location loc, float explosionPower ){
        return createExplosion(loc, explosionPower, false);
    }
    
    public static boolean createExplosion(Location loc, float explosionPower, boolean fire){
        return createExplosion(loc, explosionPower, fire, true);
    }
    
    public static boolean createExplosion(Location loc, float explosionPower, boolean fire, boolean blockDamage ){
        return createExplosion(loc, explosionPower, fire, blockDamage, false);
    }
    
    public static boolean createExplosion(Location loc, float explosionPower, boolean fire, boolean breakBlocks, boolean oForce ){

        //correct explosion ... tnt event ... may be changed to any else entity type
        EntityTNTPrimed e = new EntityTNTPrimed(((CraftWorld)loc.getWorld()).getHandle());
        e.setLocation(loc.getX(),loc.getBlockY(), loc.getBlockZ(), 0f, 0f);
        e.setSize(0.89F, 0.89F);
        e.setInvisible(true);
        org.bukkit.craftbukkit.v1_8_R2.CraftWorld craftWorld = (CraftWorld) loc.getWorld();
        org.bukkit.craftbukkit.v1_8_R2.CraftServer server = craftWorld.getHandle().getServer();

        ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Explosive) org.bukkit.craftbukkit.v1_8_R2.entity.CraftEntity.getEntity(server, e));
        event.setRadius(explosionPower);
        event.setFire(fire);
        server.getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            craftWorld.getHandle().createExplosion(e, loc.getX() , loc.getY() , loc.getZ(), explosionPower, fire, breakBlocks);
        }
        return !event.isCancelled();
    }
    
}
