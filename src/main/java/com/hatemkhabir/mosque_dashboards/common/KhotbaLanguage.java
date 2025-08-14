package com.hatemkhabir.mosque_dashboards.common;

import lombok.Getter;

@Getter
public enum KhotbaLanguage {
    ARABIC("ar", "العربية", "Arabic"),
    ENGLISH("en", "English", "English"),
    URDU("ur", "اردو", "Urdu"),
    TURKISH("tr", "Türkçe", "Turkish"),
    PERSIAN("fa", "فارسی", "Persian/Farsi"),
    INDONESIAN("id", "Bahasa Indonesia", "Indonesian"),
    MALAY("ms", "Bahasa Melayu", "Malay"),
    BENGALI("bn", "বাংলা", "Bengali"),
    FRENCH("fr", "Français", "French"),
    GERMAN("de", "Deutsch", "German"),
    SPANISH("es", "Español", "Spanish"),
    RUSSIAN("ru", "Русский", "Russian"),
    SWAHILI("sw", "Kiswahili", "Swahili"),
    HAUSA("ha", "هَوُسَ", "Hausa"),
    SOMALI("so", "Soomaali", "Somali"),
    BOSNIAN("bs", "Bosanski", "Bosnian"),
    ALBANIAN("sq", "Shqip", "Albanian"),
    PASHTO("ps", "پښتو", "Pashto"),
    CHINESE("zh", "中文", "Chinese"),
    TAMIL("ta", "தமிழ்", "Tamil"),
    GUJARATI("gu", "ગુજરાતી", "Gujarati"),
    PUNJABI("pa", "ਪੰਜਾਬੀ", "Punjabi"),
    UZBEK("uz", "O'zbek", "Uzbek"),
    KAZAKH("kk", "Қазақ", "Kazakh"),
    AZERI("az", "Azərbaycan", "Azerbaijani"),
    THAI("th", "ไทย", "Thai"),
    DUTCH("nl", "Nederlands", "Dutch"),
    ITALIAN("it", "Italiano", "Italian"),
    PORTUGUESE("pt", "Português", "Portuguese"),
    YORUBA("yo", "Yorùbá", "Yoruba"),
    WOLOF("wo", "Wolof", "Wolof"),
    FULANI("ff", "Fulfulde", "Fulani"),
    AMHARIC("am", "አማርኛ", "Amharic"),
    OROMO("om", "Afaan Oromoo", "Oromo"),
    SINDHI("sd", "سنڌي", "Sindhi"),
    KURDISH("ku", "کوردی", "Kurdish"),
    UYGHUR("ug", "ئۇيغۇرچە", "Uyghur"),
    TAJIK("tg", "Тоҷикӣ", "Tajik"),
    TATAR("tt", "Татар", "Tatar"),
    CHECHEN("ce", "Нохчийн", "Chechen"),
    MALAYALAM("ml", "മലയാളം", "Malayalam"),
    TELUGU("te", "తెలుగు", "Telugu"),
    KANNADA("kn", "ಕನ್ನಡ", "Kannada"),
    MARATHI("mr", "मराठी", "Marathi"),
    HINDI("hi", "हिन्दी", "Hindi"),
    OTHER("other", "Other", "Other Language");

    private final String code;
    private final String nativeName;
    private final String englishName;

    KhotbaLanguage(String code, String nativeName, String englishName) {
        this.code = code;
        this.nativeName = nativeName;
        this.englishName = englishName;
    }


}