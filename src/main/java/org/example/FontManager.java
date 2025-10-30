package org.example;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontManager {

    public static Font loadFont(String path, float size) {
        try (InputStream is = FontManager.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Font not found at: " + path);
            }
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
            return baseFont.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Monospaced", Font.PLAIN, (int)size);
        }
    }
}