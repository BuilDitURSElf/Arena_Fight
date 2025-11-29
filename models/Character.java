package models;

import java.util.List;

public abstract class Character {
    protected String name;
    protected double health;
    protected double speed;
    protected Weapon currentWeapon;
    protected List<Weapon> weapons;
    protected double positionX, positionY;
    protected boolean isFacingRight;
    
    public abstract void useSpecialAbility();
    public abstract void loadDefaultWeapons();
    
}
