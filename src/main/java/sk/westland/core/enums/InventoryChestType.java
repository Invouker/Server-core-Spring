package sk.westland.core.enums;

public enum InventoryChestType {

    CHEST_9(1),
    CHEST_18(2),
    CHEST_27(3),
    CHEST_36(4),
    CHEST_45(5),
    CHEST_54(6),
    CHEST_64(7);


    private int rows;

    InventoryChestType(int rows) {
        this.rows = rows;
    }

    public int getSize() {
        return 9 * rows;
    }
}
