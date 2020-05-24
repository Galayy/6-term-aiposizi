package com.iit.aiposizi.lab1.reader.impl;

import com.iit.aiposizi.lab1.reader.Reader;

import java.io.IOException;
import java.io.InputStream;

public class FileReader implements Reader {

    @Override
    public byte[] read(InputStream inputStream) throws IOException {
        var fileData = new byte[inputStream.available()];
        try (inputStream) {
            inputStream.read(fileData);
        }
        return fileData;
    }
}
