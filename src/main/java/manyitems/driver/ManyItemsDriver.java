package manyitems.driver;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import featherpowders.items.ItemsDriver;

public class ManyItemsDriver extends ItemsDriver<ManyItemsType, ManyItemsStack> {
    
    private HashMap<String, ManyItemsType> types = new HashMap<>();
    
    public ManyItemsDriver() { super(ManyItemsType.class); }
    
    @Override
    public ManyItemsStack createItem(ManyItemsType type, int amount) { return new ManyItemsStack(type, amount); }
    
    @Override
    public ManyItemsStack fromBukkit(ItemStack stack) {
        if (stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0) return null;
        ItemMeta meta = stack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(ManyItemsStack.KEY_ID, PersistentDataType.STRING)) return new ManyItemsStack(stack);
        return null;
    }
    
    @Override
    public ItemStack fromCustom(ManyItemsStack stack) { return stack.getBukkitStack(); }
    
    @Override
    public ManyItemsType fromDataID(String id) { return types.get(id); }
    
    @Override
    public Collection<ManyItemsType> getAllTypes() { return types.values(); }
    
    public void registerItem(ManyItemsType type) { this.types.put(type.dataId, type); }
    
}
