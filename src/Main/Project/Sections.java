package Project;

public enum Sections {
   Dokument(0),
   Dział(1),
   Rozdział(2),
   Art(3),
   Ust(4),
   Pkt(5),
   Litera(6)
   ;

   public Sections succ(){
       int lvl = this.getLvlTree()+1;
       return Sections.values()[lvl];
   }
    public Sections pred(){
       int lvl = this.getLvlTree()-1;
       return Sections.values()[lvl];
    }

    private final int lvlTree;

    Sections(int lvlTree){
        this.lvlTree = lvlTree;
    }

    public int getLvlTree() {
        return lvlTree;
    }

   public String toString()
   {
       switch (this){
           case Dokument:
               return "Dokument";
           case Dział:
               return "Dział";
           case Rozdział:
               return "Rozdział";
           case Art:
               return "Artykuł";
           case Ust:
               return "Ustęp";
           case Pkt:
               return "Punkt";
           case Litera:
               return "Litera";
           default:
               return null;
       }
   }



}
