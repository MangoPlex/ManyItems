package manyitems.traits.rarity;

public enum Rarity {
    
    COMMON("Common", "§f"),
    UNCOMMON("Uncommon", "§a"),
    RARE("Rare", "§9"),
    EPIC("Epic", "§5"),
    LEGENDARY("Legendary", "§6");
    
    public final String displayName;
    public final String color;
    
    private Rarity(String displayName, String color) {
        this.displayName = displayName;
        this.color = color;
    }
    
}
