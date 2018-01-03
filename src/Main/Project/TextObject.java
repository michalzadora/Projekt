package Project;

import java.util.*;

public class TextObject {
    String title = "";
    Sections section;
    List<String> text = new ArrayList<>();
    List<TextObject> kids = new ArrayList<>();



    TextObject(Sections section, String text) {
        this.section = section;
        this.text.add(text);
        if(text!=null && !text.equals(""))
            this.title = text.split(" ",3)[0] + " " + text.split(" ",3)[1];
    }
    TextObject (Sections section, String content, Object parent) {
        this.section = section;
        this.text.add(content);
    }

    public List<TextObject> getKids() {
        return kids;
    }

    public TextObject getLastFromList()
    {
        return kids.get(kids.size()-1);
    }

    public TextObject search(Sections section) {

        if (this.section.getLvlTree() == section.getLvlTree() || kids.size() == 0)
            return this;
        return kids.get(kids.size() - 1).search(section);
    }
    public void addKid(TextObject fragment)
    {
        kids.add(fragment);
    }

    public void getSectiion()
    {
        System.out.println("Aktualny obiekt to: " + this.section.toString());
    }

    public void printTable() {
        if(!text.isEmpty()) {
            for (int i = 0; i < text.size(); i++) {
                    if(text.get(i)!=null)
                        System.out.println(text.get(i));
            }
            int x=0;
            while(section == Sections.Dział && x<kids.size()) {
                System.out.println("");
                kids.get(x).printTable();
                x++;
            }
        }
    }
    public void print(){
        if(!text.isEmpty()) {
            for (int i = 0; i < text.size(); i++) {

                System.out.println(text.get(i));
            }
            int x=0;
            while(x<kids.size()) {
                kids.get(x).print();
                x++;
            }
        }
    }
    public void sArt(String a_Nr){
        if(this.section == Sections.Art && a_Nr.equals(this.text.get(0).split("\\.",3)[1].substring(1))) {
            print();
            return;
        }
        for(int i =0;i<kids.size();i++)
            kids.get(i).sArt(a_Nr);
    }

    public void sExactly(String a,String u,String p,String l){
        if(this.section == Sections.Art && a.equals(this.text.get(0).split("\\.",3)[1].substring(1))){
            if(u !=null)
                for(TextObject kid : kids)
                    kid.sExactly(a,u,p,l);
            else
                print();
        }
        else if(this.section== Sections.Ust && (u.equals("-") || u.equals(this.text.get(0).split("\\.",2)[0]))){
            if(p!=null)
                for(TextObject kid : kids)
                    kid.sExactly(a,u,p,l);
            else {
                System.out.println(section.pred().toString() + " " + a + ".");
                print();
            }
        }
        else if(this.section== Sections.Pkt && p.equals(this.text.get(0).split("\\)",2)[0])){
            if(l!=null) {
                for (TextObject kid : kids)
                    kid.sExactly(a, u, p, l);
            }
            else {
                System.out.print(section.pred().pred().toString() + " " + a + ". ");
                if(!u.equals("-"))
                    System.out.println(section.pred().toString() + " " + u + ".");
                else
                    System.out.println();
                print();
            }
        }
        else if(this.section== Sections.Litera && l.equals(this.text.get(0).split("\\)",2)[0])){
            System.out.print(section.pred().pred().pred().toString() + " " + a + ". ");
            if(!u.equals("-"))
                System.out.print(section.pred().pred().toString() + " " + u + ". ");
            else
                System.out.println();
            System.out.println(section.pred().toString() + " " + p + ".");
            print();
        }
        else if(section.getLvlTree()<3){
            for(TextObject kid : kids)
                kid.sExactly(a,u,p,l);
        }

    }

    public void sArts(String from, String to) {
        if(this.section == Sections.Art) {
            if (to.equals(this.text.get(0).split("\\.", 3)[1].substring(1))) {
                print();
                Flag.setWrite(false);
                return;
            }
            if (from.equals(this.text.get(0).split("\\.", 3)[1].substring(1))) {
                Flag.setWrite(true);
            }
            if(Flag.isWrite())
                print();
        }

        for(TextObject kid : this.kids)
            kid.sArts(from,to);
    }
}
