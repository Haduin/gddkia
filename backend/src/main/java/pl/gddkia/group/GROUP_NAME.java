package pl.gddkia.group;

public enum GROUP_NAME {
    NAWIERZCHNIA(1),
    POBOCZA_PASY_ROZDZIALU(2),
    KORPUS_DROGI(3),
    ODWONIENIE(4),
    CHODNIKI_SCIEZKI(5),
    OZNAKOWANIE(6),
    BRD(7),
    ESTETYKA(8),
    URZADZENIA_WSPOMAGAJACE(9),
    ZARZADZENI_KONTRAKTEM(11),
    CZYSTOSC_NA_OBIEKTACH(12);

    private final int value;

    GROUP_NAME(int value) {
        this.value = value;
    }

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
