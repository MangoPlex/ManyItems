package manyitems.traits;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import manyitems.driver.ManyItemsStack;

public abstract class ItemTrait {
    
    public final String id;
    
    public ItemTrait(String id) { this.id = id; }
    
    public abstract TraitData createData(ConfigurationSection configData);
    
    public abstract void modifyMeta(ManyItemsStack stack, ItemMeta meta, TraitData data, ConfigurationSection configData);
    
    public abstract TraitData loadDataFromPOC(PersistentDataContainer container);
    
    public void registerTrait() { traits.put(id, this); }
    
    private static HashMap<String, ItemTrait> traits = new HashMap<>();
    public static ItemTrait getTrait(String id) { return traits.get(id); }
    
}
