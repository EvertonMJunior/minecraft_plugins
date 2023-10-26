package me.everton.esw;

public enum TagType {
	DONO ("&4&lDONO"),
    ADMIN ("&c&lADMIN"),
    CODER ("&c&l&oCODER"),
    MODPLUS ("&5&l&oMOD+"),
    MOD ("&5&lMOD"),
    BUILDER ("&3&lBUILDER"),
    TRIAL ("&d&lTRIAL"),
    PRO ("&6&lPRO"),
    VIP ("&a&lVIP"),
    NORMAL ("&7&l");

    private final String name;       

    private TagType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
       return name;
    }
}
