package net.goodcraft.api.json;

public enum JSONChatFormat {
    BOLD("bold"),
    UNDERLINED("underlined"),
    STRIKETHROUGH("strikethrough"),
    ITALIC("italic"),
    OBFUSCATED("obfuscated"),
    NENHUM("");
    private final String format;

    JSONChatFormat(String format) {
        this.format = format;
    }

    public String getFormatString() {
        return format;
    }
}