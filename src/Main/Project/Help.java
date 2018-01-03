package Project;

public class Help {
    public static void print(){
        System.out.println("Program przyjmuję trzy argumenty odzielone jednym białym znakiem:\n"+
        "       1. Scieżka dostępu do odczytywanego pliku np. C:\\konstytucja.txt\n"+
        "       2. Tryb dzialania programu:\n"+
        "           table - tryb drukujacy spis tresci\n"+
        "           text  - tryb drukujacy tekst danego fragmentu tekstu\n"+
        "       3. Opcje drukowania tekstu lub spisów według poleceń:\n"+
        "           w trybie table:\n"+
        "               Sec(X)  - Drukuje spis tresci dzialu o podanym numerze  (pod (X) postaw numer dzialu)\n"+
        "               All     - Drukuje spis tresci calego dokumentu\n"+
        "           w trybie text:\n"+
        "               Drukowanie artykułu:\n"+
        "                   Art(X)  - Drukuje artykuł o podanym numerze             (pod (X) postaw numer artykułu)\n"+
        "                   (X)-(X)     - Drukuje artykuły z podanego przedziału     (pod (X) postaw numer artykułu)\n"+
        "               Drukowanie rozdziału:\n"+
        "                   R(X)    - Drukuje rozdziału o podanym numerze           (pod (X) postaw numer rozdzialu)\n"+
        "                               jeśli rozdziały są unikalne\n"+
        "                   D(X)R(X)- Drukuje rozdział o podanym numerze\n"+
        "                       z danego działu (rozdziały są nieunikalne)  (pod (X) podstaw numer działu i rozdziału)\n"+
        "               Drukowanie wybranego fragmentu artykułu:\n"+
        "                   Art(X)Ust(X)Pkt(X)Lit(X)\n"+
        "                       - Drukuje wybrany fragment tekstu np. Art.1 Ust.1 Pkt. 1)\n"+
        "       Aby wydrukować Pkt, przy braku Ustępów należy wpisać (-) np. Art1Ust-Pkt1Lita\n"+
        "Uwagi:\n"+
        "   Numery podstawiane pod (X) wpisywane są cyframi arabskimi chyba że numer ma 'podpunkt' wtedy np 11a\n"+
        "   Szukana Litera powinna być wprowadzona małą literą.\n"+ "Przykładowa linia komend: C:\\konstytucja.txt text Art14Ust1Pkt3Lita\n");


    }
}
