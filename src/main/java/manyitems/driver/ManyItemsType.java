package manyitems.driver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import featherpowders.items.CustomType;
import manyitems.ManyItems;
import manyitems.traits.ItemTrait;
import manyitems.traits.TraitData;

public class ManyItemsType extends CustomType {
    
    private static File itemsFolder;
    
    public static void init(ManyItems plugin) {
        itemsFolder = new File(plugin.getDataFolder(), "items");
        if (!itemsFolder.exists()) itemsFolder.mkdirs();
        
    }
    
    public static File getItemsFolder() { return itemsFolder; }
    
    private File itemFile;
    protected YamlConfiguration config;
    
    public final Material display;
    public final String name;
    public final String[] description;
    public final StackingType type;
    
    public ManyItemsType(String id) {
        super(id);
        this.itemFile = new File(itemsFolder, id + ".yml");
        if (!itemFile.exists()) throw new RuntimeException("The file '" + itemFile.getPath() + "' does not exists");
        
        config = YamlConfiguration.loadConfiguration(itemFile);
        display = Material.valueOf(config.getString("display", "DIAMOND").toUpperCase());
        name = config.getString("name", id);
        if (config.contains("description")) {
            description = config.getStringList("description").toArray(String[]::new);
        } else description = new String[0];
        type = StackingType.valueOf(config.getString("type", "STACKABLE").toUpperCase());
    }
    
    public List<TraitData> createTraits() {
        List<TraitData> data = new ArrayList<>();
        for (String traitID : config.getConfigurationSection("traits").getKeys(false)) {
            ItemTrait trait = ItemTrait.getTrait(traitID);
            data.add(trait.createData(config.getConfigurationSection("traits." + traitID)));
        }
        return data;
    }
    
}
