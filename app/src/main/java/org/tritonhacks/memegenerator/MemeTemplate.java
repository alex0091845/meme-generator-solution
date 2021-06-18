package org.tritonhacks.memegenerator;

class MemeTemplate {

    private String id;
    private String url;
    private int boxCount;

    MemeTemplate(final String id, final String url, final int boxCount) {
        this.id = id;
        this.url = url;
        this.boxCount = boxCount;
    }

    String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    int getBoxCount() {
        return this.boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }

}
