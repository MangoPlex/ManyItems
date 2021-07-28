package manyitems.traits.rarity;

import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import manyitems.ManyItems;
import manyitems.driver.ManyItemsStack;
import manyitems.driver.ManyItemsType;
import manyitems.traits.ItemTrait;
import manyitems.traits.TraitData;

public class RarityTrait extends ItemTrait {

    public RarityTrait(ManyItems plugin) {
        super("rarity");
        RarityData.init(plugin);
    }

    @Override
    public TraitData createData(ConfigurationSection configData) {
        return new RarityData(this);
    }

    @Override
    public void modifyMeta(ManyItemsStack stack, ItemMeta meta, TraitData data, ConfigurationSection configData) {
        //Rarity rarity = ((RarityData) data).rarity;
        Rarity rarity = Rarity.valueOf(configData.getString("value", "COMMON").toUpperCase());
        
        meta.setDisplayName(rarity.color + ((ManyItemsType) stack.type).name);
        
        List<String> lore = meta.getLore();
        lore.add(0, rarity.color + rarity.displayName);
        meta.setLore(lore);
    }
    
    @Override
    public TraitData loadDataFromPOC(PersistentDataContainer container) { 
        return new RarityData(this);
    }
    
    
}
