package org.pombacraft.spawnerdropchance;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
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
        handleExplosion(e.blockList(), EntityType.PRIMED_TNT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent e) {
        handleExplosion(e.blockList(), e.getEntityType());
    }

    @SuppressWarnings("deprecation")
	private void handleExplosion(List<Block> blocks, EntityType entity) {
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
    		CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
    		StackedSpawner stackedSpawner = WildStackerAPI.getStackedSpawner(creatureSpawner);
    		int stackAmount = stackedSpawner.getStackAmount();
    		
    		boolean wontDrop = stackAmount < minimumStackAmount || !shouldDrop;
    		if (wontDrop) {
        		blocks.remove(block);
        		stackedSpawner.runUnstack(stackAmount + 2);
        		block.setType(Material.AIR);
    		}
    		
    		executeLogsCommands(configManager, creatureSpawner.getCreatureTypeName(), stackAmount, block.getLocation(), entity.toString(), !wontDrop);
    	}
    }
    
    private void executeLogsCommands(ConfigManager configManager, String creature, int amount, Location loc, String explosionType, boolean dropped) {
    	for (String command : configManager.getLogsCommands()) {
    		command = command.replace("%creature%", creature)
    				.replace("%amount%", Integer.toString(amount))
    				.replace("%block_x%", Integer.toString(loc.getBlockX()))
    				.replace("%block_y%", Integer.toString(loc.getBlockY()))
    				.replace("%block_z%", Integer.toString(loc.getBlockZ()))
    				.replace("%explosion%", explosionType)
    				.replace("%dropped%", Boolean.toString(dropped));
    		
    		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    	}
    }

}
