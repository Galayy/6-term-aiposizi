package com.iit.aiposizi.lab1.reader.impl;

import com.iit.aiposizi.lab1.reader.Reader;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PngImageReader implements Reader {

    @Override
    public byte[] read(InputStream inputStream) throws IOException {
        try (inputStream) {
            var image = ImageIO.read(inputStream);
            var byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

}
