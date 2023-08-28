package org.pombacraft.spawnerdropchance.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pombacraft.spawnerdropchance.SpawnerDropChance;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	private double _dropChance;
	private int _minimumStackAmount;
	
	private ConfigManager() {
		YamlConfiguration config = SpawnerDropChance.getYamlConfiguration();
		
		_dropChance = config.getDouble("DropChance");
		_minimumStackAmount = config.getInt("MinimumStackAmount");
	}

	public double getDropChance() {
		return _dropChance;
	}
	
	public int getMinimumStackAmount() {
		return _minimumStackAmount;
	}
}
