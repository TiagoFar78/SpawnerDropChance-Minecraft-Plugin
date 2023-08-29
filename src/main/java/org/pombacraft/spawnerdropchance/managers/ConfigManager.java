package org.pombacraft.spawnerdropchance.managers;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.pombacraft.spawnerdropchance.SpawnerDropChance;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	private double _dropChance;
	private int _minimumStackAmount;
	private List<String> _logsCommands;
	
	private ConfigManager() {
		YamlConfiguration config = SpawnerDropChance.getYamlConfiguration();
		
		_dropChance = config.getDouble("DropChance");
		_minimumStackAmount = config.getInt("MinimumStackAmount");
		_logsCommands = config.getStringList("LogsCommands");
	}

	public double getDropChance() {
		return _dropChance;
	}
	
	public int getMinimumStackAmount() {
		return _minimumStackAmount;
	}
	
	public List<String> getLogsCommands() {
		return _logsCommands;
	}
}
