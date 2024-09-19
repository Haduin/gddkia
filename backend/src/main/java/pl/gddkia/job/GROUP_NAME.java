package pl.gddkia.job;

public enum GROUP_NAME {
    NAWIERZCHNIA,
    POBOCZA_PASY_ROZDZIALU,
    KORPUS_DROGI,
    ODWONIENIE,
    CHODNIKI_SCIEZKI,
    OZNAKOWANIE,
    BRD,
    ESTETYKA,
    URZADZENIA_WSPOMAGAJACE,
    ZIMOWE_UTRZYMANIE,
    ZARZADZANIE_KONTRAKTEM,
    CZYSTOSC_NA_OBIEKTACH;

    public static GROUP_NAME findByValue(int value) {
        return switch (value) {
            case 1 -> NAWIERZCHNIA;
            case 2 -> POBOCZA_PASY_ROZDZIALU;
            case 3 -> KORPUS_DROGI;
            case 4 -> ODWONIENIE;
            case 5 -> CHODNIKI_SCIEZKI;
            case 6 -> OZNAKOWANIE;
            case 7 -> BRD;
            case 8 -> ESTETYKA;
            case 9 -> URZADZENIA_WSPOMAGAJACE;
            case 10, 11 -> ZIMOWE_UTRZYMANIE;
            case 12 -> ZARZADZANIE_KONTRAKTEM;
            case 13 -> CZYSTOSC_NA_OBIEKTACH;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}
