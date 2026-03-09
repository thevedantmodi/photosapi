package com.vedantmodi.photosapi.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class SizerService {
    public byte[] resizeToThumbnail(File image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(image).size(300, 300).keepAspectRatio(true)
                .toOutputStream(out);
        return out.toByteArray();
    }

    public byte[] resizeToLarge(File image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails.of(image).size(1600, 1600).keepAspectRatio(true)
                .toOutputStream(out);
        return out.toByteArray();
    }
}
