package Project;

import java.util.List;

public class Printer
{
    private List<TextObject> list;

    Printer(List list, String[] args)
    {
        this.list=list;
        check(args);
    }


    public void check(String[] args) throws IllegalArgumentException
    {
        if(args[1].equals("table"))
            printTable(args[2]);
        else if(args[1].equals("text"))
           print(args[2]);
        else
            throw new IllegalArgumentException("Bledny 2. argument");
    }

    private void print(String arg) {
        if(arg.contains("D")&& arg.contains("R")) {// drukowanie jednego dzialu
            String d_Nr = "";
            String r_Nr = "";

            for (int i=1;i<arg.length();i++) {  //
                if(arg.charAt(i)=='R') {
                    for (i = i + 1; i < arg.length(); i++)
                        r_Nr += arg.charAt(i);
                    break;
                }
                d_Nr += arg.charAt(i);
            }
            findSectionAndChapter(d_Nr,r_Nr).print();
        }
        else if(arg.startsWith("R")){
            String r_Nr = "";
            for(int i =1;i<arg.length();i++)
                r_Nr += arg.charAt(i);
            findChapter(r_Nr, list).print();
        }
        else if(arg.startsWith("Art")){
            String a_Nr ="";
            String u_Nr ="";
            String p_Nr ="";
            String l    ="";
            int x = 0;
            for(x=3;x<arg.length();x++) {
                if(arg.charAt(x)=='U'){
                    for(x=x+3;x<arg.length();x++) {
                        if (arg.charAt(x) == 'P') {
                            for(x=x+3;x<arg.length();x++) {
                                if(arg.charAt(x)=='L'){
                                    for(x=x+3;x<arg.length();x++)
                                        l += arg.charAt(x);
                                    if(arg.matches("Art[0-9]+[a-z]?Ust(\\d+|-)Pkt\\d+Lit[a-z]")) {
                                        find(a_Nr,u_Nr,p_Nr,l);
                                        return;
                                    }
                                    else
                                        throw new IllegalArgumentException("Czy na pewno wprowadziłeś dobrze argument 3.");
                                }
                                p_Nr += arg.charAt(x);
                            }
                            if(arg.matches("Art[0-9]+[a-z]?Ust(\\d+|-)Pkt\\d+")) {
                                find(a_Nr,u_Nr,p_Nr,null);
                                return;
                            }
                            else
                                throw new IllegalArgumentException("Czy na pewno wprowadziłeś dobrze argument 3.");

                        }
                        u_Nr += arg.charAt(x);
                    }
                    if(arg.matches("Art[0-9]+[a-z]?Ust(\\d+|-)")) {
                        find(a_Nr, u_Nr, null, null);
                        return;
                    }
                    else
                        throw new IllegalArgumentException("Czy na pewno wprowadziłeś dobrze argument 3.");
                }
                a_Nr += arg.charAt(x);
            }
            if(arg.matches("Art[0-9]+[a-z]?+"))
                findArt(a_Nr);
            else
                throw new IllegalArgumentException("Czy na pewno wprowadziłeś dobrze argument 3.");
        }
        else if(arg.matches("\\d+-\\d+")){
            String from ="";
            String to = "";
            int x = 0;
            for(;arg.charAt(x)!='-';x++)
                from+=arg.charAt(x);
            for(x=x+1;x<arg.length();x++)
                to += arg.charAt(x);
            if(Integer.valueOf(from)> Integer.valueOf(to))
                throw new IllegalArgumentException("Bledny przedzial artykulów, podaj OD-DO.");
            findArts(from,to);
        }
        else
            throw new IllegalArgumentException("Błąd w 3. argumencie.");

    }



    public void printTable(String arg)
    {
        if(arg.startsWith("Sec")){
            String number ="";
            for(int i =3; i<arg.length();i++){
                number += arg.charAt(i);
            }
            TextObject discovered = findSection(number);
            discovered.printTable();
            return;
        }
        else if (arg.startsWith("All")) {
            for (int i = 0; i < list.size(); i++)
                list.get(i).printTable();
            return;
        }
        throw new IllegalArgumentException("Błędny 3. argument.");
    }

    private TextObject findChapter(String r_nr, List<TextObject> list1) {
        if(list.size()>2) throw new IllegalArgumentException("W tym dokumencie rozdziały nie są unikalne, zmien 3. argument.");
        for(int i = 0; i<list1.size();i++) {
            if (list1.get(i).section == Sections.Rozdział && r_nr.equals(romToNum(list1.get(i).text.get(0))))
                return list1.get(i);
            else if(list1.get(i).section.getLvlTree()<2)
                if(findChapter(r_nr,list1.get(i).getKids())!=null)
                    return findChapter(r_nr,list1.get(i).getKids());
            else
                return null;
        }
        throw new IllegalArgumentException("Nie znaleiono rozdziału o numerze: " + r_nr);
    }

    private TextObject findSection(String number) throws IllegalArgumentException {
        if(list.size()>2){
            for(int x=0;x<list.size();x++){
                if(list.get(x).section == Sections.Dział && number.equals(romToNum(list.get(x).text.get(0)))) {
                    return list.get(x);
                }
            }
        }
        throw  new IllegalArgumentException("Nie znaleziono wybranego działu");
    }

    private TextObject findSectionAndChapter(String d_nr, String r_nr) {
        TextObject discovered = findSection(d_nr);
        for(int i =0; i<discovered.kids.size();i++)
            if(discovered.kids.get(i).section == Sections.Rozdział && r_nr.equals(romToNum(list.get(i).text.get(0)))) {
                System.out.println(discovered.title);
                return discovered.kids.get(i);
            }

        throw new IllegalArgumentException("Nie znaleziono rozdziału w podanym dziale");
    }

    private void findArt(String a_nr) {
        for(TextObject object : list)
            object.sArt(a_nr);
    }

    private void findArts(String from, String to) {
        for(TextObject object : list)
            object.sArts(from,to);
    }


    private void find(String a_nr, String u_nr, String p_nr, String l) {
        for(TextObject object : list)
            object.sExactly(a_nr,u_nr,p_nr,l);
    }

    public static String romToNum(String roman){
        roman = roman.split(" ",3)[1];
        int result = 0;
        for(int i = 0;i<roman.length();i++) {
            char toCover = roman.charAt(i);


            switch (toCover) {
                case 'X':
                    result += 10;
                    break;

                case 'V':
                    result += 5;
                    break;
                case 'I':
                    result += 1;
                    break;
                default:
                   return String.valueOf(result)+toCover;
            }
        }
        if(roman.contains("IV"))
            result-=2;
        if(roman.contains("IX"))
            result-=2;

        return String.valueOf(result);
    }
}
