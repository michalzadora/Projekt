package Project;

import java.io.IOException;

public class mainSystem {


    public static void main(String[] args) throws IOException {

        try {
            if(args[0].equals("help")){
                Help.print();
                return;
            }
            else if(args.length !=3)
                throw new IllegalArgumentException("Wprowadz 3. argumenty lub wpisz help.");
            Parser parser = new Parser(args);
            new Printer(parser.getTreeList(), args);
        }
        catch(IllegalArgumentException e){
            System.err.println(e.getMessage() + "\nAby uzyskac pomoc wprowadz help");
        }


        /**
        Program przyjmuję trzy argumenty odzielone jednym białym znakiem:
         1. Scieżka dostępu do odczytywanego pliku np. C:\konstytucja.txt
         2. Tryb dzialania programu:
            table - tryb drukujacy spis tresci
            text  - tryb drukujacy tekst danego fragmentu tekstu
         3. Opcje drukowania tekstu lub spisów według poleceń:
                w trybie table:
                        Sec(X)  - Drukuje spis tresci dzialu o podanym numerze  (pod (X) postaw numer dzialu)
                        All     - Drukuje spis tresci calego dokumentu
                w trybie text:
                    Drukowanie artykułu:
                        Art(X)  - Drukuje artykuł o podanym numerze             (pod (X) postaw numer artykułu)
                        (X)-(X)     - Drukuje artykuły z podanego przedziału     (pod (X) postaw numer artykułu)
                    Drukowanie rozdziału:
                        R(X)    - Drukuje rozdziału o podanym numerze           (pod (X) postaw numer rozdzialu)
                                    jeśli rozdziały są unikalne
                        D(X)R(X)- Drukuje rozdział o podanym numerze
                                    z danego działu (rozdziały są nieunikalne)  (pod (X) podstaw numer działu i rozdziału)
                    Drukowanie wybranego fragmentu artykułu:
                        Art(X)Ust(X)Pkt(X)Lit(X)
                                - Drukuje wybrany fragment tekstu np. Art.1 Ust.1 Pkt. 1)
                        Aby wydrukować Pkt, przy braku Ustępów należy wpisać (-) np. Art1Ust-Pkt1Lita
         Uwagi:
            Numery podstawiane pod (X) wpisywane są cyframi arabskimi chyba że numer ma "podpunkt" wtedy np 11a
            Szukana Litera powinna być wprowadzona małą literą.
            Przykładowa linia komend: C:\konstytucja.txt text Art14Ust1Pkt3Lita


        **/

    }
}
