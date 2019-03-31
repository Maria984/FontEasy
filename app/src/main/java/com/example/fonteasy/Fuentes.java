package com.example.fonteasy;

import android.support.v7.app.AppCompatActivity;

import com.google.api.services.webfonts.Webfonts;
import com.google.api.services.webfonts.WebfontsRequest;
import com.google.api.services.webfonts.WebfontsRequestInitializer;
import com.google.api.services.webfonts.model.Webfont;

public final class Fuentes extends AppCompatActivity {

    private Webfont Webfont;
    private Webfonts Webfonts;
    private WebfontsRequest WebfontsRequest;
    private WebfontsRequestInitializer WebfontsInitializer;
    private WebfontList WebfontsList;



    @SuppressWarnings("javadoc")
    public static final class WebfontList extends com.google.api.client.json.GenericJson {

        @com.google.api.client.util.Key
        private java.util.List<Webfont> items;

        static {
            com.google.api.client.util.Data.nullOf(Webfont.class);
        }


        @com.google.api.client.util.Key
        private java.lang.String kind;

        public java.util.List<Webfont> getItems() {
            return items;
        }

        public WebfontList setItems(java.util.List<Webfont> items) {
            this.items = items;
            return this;
        }

        public java.lang.String getKind() {
            return kind;
        }

        public WebfontList setKind(java.lang.String kind) {
            this.kind = kind;
            return this;
        }

        @Override
        public WebfontList set(String fieldName, Object value) {
            return (WebfontList) super.set(fieldName, value);
        }

        @Override
        public WebfontList clone() {
            return (WebfontList) super.clone();
        }

    }









}