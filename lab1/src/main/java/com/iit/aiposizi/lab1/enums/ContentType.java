package com.iit.aiposizi.lab1.enums;

import com.iit.aiposizi.lab1.reader.Reader;
import com.iit.aiposizi.lab1.reader.impl.FileReader;
import com.iit.aiposizi.lab1.reader.impl.PngImageReader;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ContentType {

    PLAIN("text/plain", "txt", new FileReader()),
    HTML("text/html", "html", new FileReader()),
    CSS("text/css", "css", new FileReader()),
    JS("application/javascript", "js", new FileReader()),
    PNG("image/png", "png", new PngImageReader()),
    JPEG("image/jpeg", "jpeg", new PngImageReader()),
    SVG("image/svg+xml", "svg", new FileReader());

    private final String text;
    private final String extension;
    private final Reader reader;


    ContentType(String text, String extension, Reader reader){
        this.text = text;
        this.extension = extension;
        this.reader = reader;
    }

    public static ContentType findByFileName(String fileName){
        var extension = fileName.substring(fileName.lastIndexOf(".")+1);
        return Arrays.stream(ContentType.values())
                .filter(x -> x.getExtension().equalsIgnoreCase(extension))
                .findFirst()
                .orElse(PLAIN);
    }

}
