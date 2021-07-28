package manyitems.traits.rarity;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

import manyitems.ManyItems;
import manyitems.traits.ItemTrait;
import manyitems.traits.TraitData;

public class RarityData extends TraitData {
    
    protected static NamespacedKey KEY_RARITY;
    public static void init(ManyItems plugin) {
        KEY_RARITY = new NamespacedKey(plugin, "rarity");
    }
    
    private RarityTrait trait;
    
    public RarityData(RarityTrait trait) { this.trait = trait; }
    
    @Override
    public ItemTrait getTrait() { return trait; }

    @Override
    public void saveToPOC(PersistentDataContainer container) {
    }

}
