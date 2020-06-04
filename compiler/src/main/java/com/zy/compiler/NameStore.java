package com.zy.compiler;



public final class NameStore {

    private NameStore() {
        // not to be instantiated in public
    }


    public static class Package {
        public static final String ANDROID_JSON = "org.json";
    }

    public static class Class {
        //
        public static final String ANDROID_JSONOBJECT = "JSONObject";
    }

    public static class Method {
        // Binder
        public static final String BIND_DATA = "bindData";
    }

    public static class Variable {
        public static final String ANDROID_BASEDATA = "BaseData";
        public static final String ANDROID_DATA = "data";
        public static final String ANDROID_BEAN = "bean";
    }
}

