package manyitems.traits;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * The trait data, which is parsed from {@link ConfigurationSection}
 * @author nahkd
 *
 */
public abstract class TraitData {
    
    public abstract ItemTrait getTrait();
    
    public abstract void saveToPOC(PersistentDataContainer container);
    
}
