package manyitems;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import manyitems.driver.ManyItemsDriver;
import manyitems.driver.ManyItemsStack;
import manyitems.driver.ManyItemsType;
import manyitems.traits.rarity.RarityTrait;

public class ManyItems extends JavaPlugin {
    
    public ManyItemsDriver driver;
    
    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("FeatherPowders")) {
            getServer().getConsoleSender().sendMessage(new String[] {
                "",
                "§cFATAL ERROR: §fFeatherPowders is not enabled! (or it's doesn't exists...)",
                "§fManyItems rely heavily on FeatherPowders to provide custom items driver,",
                "§fwhich allow other plugins to use your own custom item.",
                "",
                "§fPlease install §bFeatherPowders §fto enable ManyItems",
                ""
            });
            getServer().getPluginManager().disablePlugin(this);
        }

        saveResource("item_template.yml", false);
        saveResource("config.yml", false);
        reloadConfig();
        
        this.driver = new ManyItemsDriver();
        this.driver.registerDriver();
        
        ManyItemsType.init(this);
        ManyItemsStack.init(this);
        
        // Register default traits
        new RarityTrait(this).registerTrait();
        
        // Load items
        getServer().getConsoleSender().sendMessage("§e[ManyItems] §fLoading items...");
        int registered = 0;
        for (File f : ManyItemsType.getItemsFolder().listFiles()) {
            String fileName = f.getName();
            String id;
            if (fileName.contains(".")) {
                String[] fileNameSplit = fileName.split("\\.");
                id = fileName.substring(0, fileName.length() - fileNameSplit[fileNameSplit.length - 1].length() - 1);
            } else id = fileName;
            
            ManyItemsType type = new ManyItemsType(id);
            driver.registerItem(type);
            registered++;
        }
        
        getServer().getConsoleSender().sendMessage("§e[ManyItems] §fRegistered " + registered + " items");
    }
    
}
