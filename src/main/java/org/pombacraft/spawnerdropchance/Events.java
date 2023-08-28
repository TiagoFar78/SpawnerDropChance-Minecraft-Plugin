package org.pombacraft.spawnerdropchance;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.pombacraft.spawnerdropchance.managers.ConfigManager;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedSpawner;

public class Events implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
    public void onBlockExplode(BlockExplodeEvent e) {
        handleExplosion(e.blockList());
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent e) {
        handleExplosion(e.blockList());
    }

    private void handleExplosion(List<Block> blocks) {
    	ConfigManager configManager = ConfigManager.getInstance();
    	double dropChance = configManager.getDropChance();
    	int minimumStackAmount = configManager.getMinimumStackAmount();
    	
    	List<Block> blocksToRemove = new ArrayList<Block>();
    	
    	boolean shouldDrop = Math.random() < dropChance;

    	for (Block block : blocks) {
    		if (block.getType() == Material.SPAWNER) {
    			blocksToRemove.add(block);
    		}
    	}
    	
    	for (Block block : blocksToRemove) {
    		StackedSpawner stackedSpawner = WildStackerAPI.getStackedSpawner((CreatureSpawner) block.getState());
    		if (stackedSpawner.getStackAmount() < minimumStackAmount || !shouldDrop) {
        		blocks.remove(block);
        		stackedSpawner.runUnstack(stackedSpawner.getStackAmount() + 2);
        		block.setType(Material.AIR);
    		}
    	}
    	
    	
    }

}
