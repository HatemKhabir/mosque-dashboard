package com.hatemkhabir.mosque_dashboards.common;

public enum KhotbaType {
    FRIDAY("Friday Khutbah"),
    EID_AL_FITR("Eid al-Fitr"),
    EID_AL_ADHA("Eid al-Adha"),
    TARAWEEH("Taraweeh"),
    SPECIAL_OCCASION("Special Occasion"),
    OTHER("Other");

    private final String displayName;

    KhotbaType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
