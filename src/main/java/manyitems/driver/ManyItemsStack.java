package manyitems.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import featherpowders.items.CustomStack;
import manyitems.ManyItems;
import manyitems.traits.ItemTrait;
import manyitems.traits.TraitData;

public class ManyItemsStack extends CustomStack {
    
    private static ManyItemsDriver driver;
    protected static NamespacedKey KEY_ID;
    private static NamespacedKey KEY_UUID;
    
    private static String[] modifiersOrder;
    
    public static void init(ManyItems plugin) {
        driver = plugin.driver;
        KEY_ID = new NamespacedKey(plugin, "id");
        KEY_UUID = new NamespacedKey(plugin, "uuid");
        
        modifiersOrder = plugin.getConfig().getStringList("traits.meta-modifiers-order").toArray(String[]::new);
    }
    
    protected UUID uniqueId;
    protected List<TraitData> traits;
    
    public ManyItemsStack(ManyItemsType type, int amount) {
        this.type = type;
        this.amount = amount;
        setup();
    }
    
    public ManyItemsStack(ManyItemsType type) { this(type, 1); }
    
    public ManyItemsStack(ItemStack bukkit) {
        ItemMeta meta = bukkit.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        
        String id = container.get(KEY_ID, PersistentDataType.STRING);
        ManyItemsType type = driver.fromDataID(id);
        if (type == null) throw new RuntimeException("Item with ID " + id + " does not exists");
        this.type = type;
        this.amount = bukkit.getAmount();
        
        if (type.type == StackingType.UNIQUE) uniqueId = UUID.fromString(container.get(KEY_UUID, PersistentDataType.STRING));
        
        // Traits data
        for (String traitID : type.config.getConfigurationSection("traits").getKeys(false)) {
            ItemTrait trait = ItemTrait.getTrait(traitID);
            if (trait == null) continue;
            this.traits = new ArrayList<TraitData>();
            this.traits.add(trait.loadDataFromPOC(container));
        }
    }
    
    private void setup() {
        ManyItemsType type = (ManyItemsType) this.type;
        if (type.type == StackingType.UNIQUE) uniqueId = UUID.randomUUID();
        
        // Traits
        traits = type.createTraits();
    }
    
    public TraitData getTraitData(String id) {
        for (TraitData data : traits) if (data.getTrait().id.equals(id)) return data;
        return null;
    }
    
    public ItemStack getBukkitStack() {
        ManyItemsType type = (ManyItemsType) this.type;
        ItemStack stack = new ItemStack(type.display, amount);
        ItemMeta meta = stack.getItemMeta();
        
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(KEY_ID, PersistentDataType.STRING, type.dataId);
        if (type.type == StackingType.UNIQUE) container.set(KEY_UUID, PersistentDataType.STRING, uniqueId.toString());
        
        // Traits data
        for (TraitData data : traits) data.saveToPOC(container);
        
        // Item display, will be modified by traits
        meta.setDisplayName("§f" + type.name);
        List<String> lore = new ArrayList<>();
        for (String line : type.description) lore.add("§7§o" + line);
        meta.setLore(lore);
        
        // Traits meta modifiers
        for (String traitID : modifiersOrder) {
            TraitData data = getTraitData(traitID);
            if (data == null) continue;
            data.getTrait().modifyMeta(this, meta, data, type.config.getConfigurationSection("traits." + traitID));
        }
        
        stack.setItemMeta(meta);
        return stack;
    }
    
}
