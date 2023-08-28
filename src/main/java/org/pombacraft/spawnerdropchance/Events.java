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
	
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockExplode(BlockExplodeEvent e) {
        handleExplosion(e.blockList());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityExplode(EntityExplodeEvent e) {
        handleExplosion(e.blockList());
    }

    private void handleExplosion(List<Block> blocks) {
    	double dropChance = ConfigManager.getInstance().getDropChance();
    	
    	List<Block> blocksToRemove = new ArrayList<Block>();
    	
    	if (Math.random() < dropChance) {
    		return;
    	}
    	
    	for (Block block : blocks) {
    		if (block.getType() == Material.SPAWNER) {
    			blocksToRemove.add(block);
    		}
    	}
    	
    	for (Block block : blocksToRemove) {
    		blocks.remove(block);
    		
    		StackedSpawner stackedSpawner = WildStackerAPI.getStackedSpawner((CreatureSpawner) block.getState());
    		stackedSpawner.runUnstack(stackedSpawner.getStackAmount());
    	}
    }

}
