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
    ZARZADZENI_KONTRAKTEM,
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
            case 11 -> ZARZADZENI_KONTRAKTEM;
            case 12 -> CZYSTOSC_NA_OBIEKTACH;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}
