package me.draivin.mobremover;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.plugin.java.JavaPlugin;


public class MobRemover extends JavaPlugin {
    
    public void onEnable() {
        MobChecker checker = new MobChecker();
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(this, checker, 5 , 400);       
    }
    
    
    private class MobChecker implements Runnable {
        
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            
            for(World world : Bukkit.getServer().getWorlds()){
                
                for(Monster monster : world.getEntitiesByClass(Monster.class)){
                        
                    List<Entity> nearbyEntities = monster.getNearbyEntities(1, 1, 1);
                    
                    int nearbyCount = 0;
                    for(Entity entity : nearbyEntities) {
                        if(entity instanceof Monster && !entity.isDead())
                            ++nearbyCount;
                    }
                    
                    if(nearbyCount > 4){
                        monster.remove();
                        /*if(Math.random() > (1.0 / (nearbyCount-4))){
                            monster.remove();
                        }*/
                    }
                }
            }
            
            
            getLogger().info("Check made in " + (System.currentTimeMillis() - start) + "ms.");
        }
        
    }
}