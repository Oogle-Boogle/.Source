package com.platinum.discordshit.embed;

import com.platinum.discordshit.Payload;
import com.platinum.discordshit.PayloadMap;

public class EmbedMedia extends Payload {

    private final String url;
    private final Integer height, width;

    public EmbedMedia(String url, Integer height, Integer width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    @Override
    public void save(PayloadMap map) {
        map.put("url", url);
        map.putIfExists("height", height);
        map.putIfExists("width", width);
    }
}
