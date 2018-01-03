package Project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private String filePath;
    private List <TextObject> treeList = new ArrayList<>();
    private TextObject parent = new TextObject(Sections.Dokument,"");
    private boolean flag = false;//bedzie mowic czy dana linijka nalezy bezposrednio do dzialu



    public Parser(String[] args) throws IOException {

        this.filePath = args[0];
        makeTree();
    }

    public List<TextObject> getTreeList() {
        return treeList;
    }


    private ArrayList<String> readAndClean() throws IOException
    {
        BufferedReader reader = null;
        ArrayList<String> file = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader (this.filePath);
            reader = new BufferedReader(fileReader);
            String line = null;

            while(reader.ready()) {
                line = reader.readLine();
                if(line.startsWith("\uFEFF©")||line.startsWith("©"))
                {
                    reader.readLine();
                }
                else if(line.endsWith("-"))
                {
                    wordJoin(reader, file, line);
                }
                else if(line.startsWith("Dz.U."))
                {
                    line = reader.readLine();
                    file.add(line);
                }
                else
                    file.add(line);
            }
        }

        catch (FileNotFoundException ex)
        {
            System.out.println("Nie moge otworzyc pliku '" + this.filePath +"'. Prawdopodobnie błędna ścieżka dostępu.");
            return null;
        }
        finally
        {
            try
            {
                if(reader!= null)
                    reader.close();
            } catch (IOException ex) {
                System.out.println("Bład odczytu pliku.");
            }
        }
        return file;

    }

    private void wordJoin(BufferedReader reader, ArrayList<String> file, String line) throws IOException {
        while (line.endsWith("-")) {
            line = line.substring(0, line.length() - 1);
            String temp_line = reader.readLine();
            if (temp_line.contains(" ")) {
                line = line.concat(temp_line.substring(0, temp_line.indexOf(" ")));
                temp_line = temp_line.substring(temp_line.indexOf(" ") + 1);
            }
            file.add(line);
            line = temp_line;
        }
    }

    boolean makeTree() throws IOException {
        ArrayList<String> file = new ArrayList<String>();
        file = this.readAndClean();
        String line = "";

        if (file != null && !file.isEmpty()) {
            int i = 0;
            while (i < file.size() && file.get(i) != null)
            {
                line = file.get(i);
                fillTree(line);

                i++;
            }
        }
        return false;
    }

    public void fillTree(String line)
    {
        if(isSpecial(line)==1)
        {
            flag = true;
            treeList.add(new TextObject(Sections.Dział,line));
            parent = treeList.get(treeList.size()-1).search(Sections.Dział);
            parent.addKid(new TextObject(Sections.Rozdział,null));
        }
        else if(isSpecial(line)==2)
        {
            flag = false;                            //Sprawdzam pierwsze czy są działy(czy na liscie drzewa cos jest) ??
            if(treeList.isEmpty())                  // jesli nie ma nic, to tworze pusty dzial w ktorym bede przechowywac liste rozdzialow
                treeList.add(new TextObject(Sections.Dział,null));
            parent = treeList.get(treeList.size()-1).search(Sections.Dział);
            parent.addKid(new TextObject(Sections.Rozdział,line));
        }

        else if(isSpecial(line)==3)
        {
            flag = false;

            if(line.matches("Art\\. [0-9]+–[0-9]+\\..*"))
            {
                parent = treeList.get(treeList.size()-1).search(Sections.Rozdział);
                int from = Integer.valueOf(line.split(". ")[1].split("–")[0]);
                int to = Integer.valueOf(line.split("–")[1].split("\\. ",2)[0]);
                for(;to-from>=0;to--)
                    parent.addKid(new TextObject(Sections.Art,"Art. " + to + ". (pominięty)" ,parent));
            }
            else if(!line.matches("Art\\. [1-9a-z]+\\. [1-9]\\.(.*)"))
            {
                parent = treeList.get(treeList.size()-1).search(Sections.Rozdział);
                parent.addKid(new TextObject(Sections.Art,line,parent));
            }
            else
            {
                parent = treeList.get(treeList.size()-1).search(Sections.Rozdział);
                parent.addKid(new TextObject(Sections.Art,line.split(" ",3)[0]+ " " +line.split(" ",3)[1],parent));
                parent = treeList.get(treeList.size()-1).search(Sections.Art);
                parent.addKid(new TextObject(Sections.Ust,line.split(" ",3)[2],parent));
            }
        }
        else if(isSpecial(line)==4)
        {
            flag = false;
            parent = treeList.get(treeList.size()-1).search(Sections.Art);
            parent.addKid(new TextObject(Sections.Ust,line,parent));
        }
        else if(isSpecial(line)==5)
        {

            flag = false;
            if(treeList.get(treeList.size()-1).search(Sections.Art).getKids().size()==0)
            {
                parent = treeList.get(treeList.size()-1).search(Sections.Art);
                parent.addKid(new TextObject(Sections.Ust,"",parent));
            }
            parent = treeList.get(treeList.size()-1).search(Sections.Ust);
            parent.addKid(new TextObject(Sections.Pkt,line,parent));
        }
        else if(isSpecial(line)==6)
        {

            flag=false;
            parent = treeList.get(treeList.size()-1).search(Sections.Pkt);
            parent.addKid(new TextObject(Sections.Litera,line,parent));
        }
        else
        {
            if(flag)
                treeList.get(treeList.size() - 1).text.add(line);

            else if(treeList.size()==0)
                parent.text.add(line);
            else {
                parent.getLastFromList().text.add(line);
            }
        }

    }

    private int isSpecial (String line)
    {
        if(line.matches(".*DZIAŁ [IVXL]+.*"))
            return 1;
        if(line.matches("^Rozdział [0-9IVXL]+.*"))
            return 2;
        //if(line.matches("^Art\\. [0-9]+[a-z]?\\..*"))
        if(line.matches("^Art\\..*"))
            return 3;
        if(line.matches("^\\d+[a-z]?\\.(?= \\D).*"))
            return 4;
        if(line.matches("\\d+[a-z]?\\).*"))
            return 5;
        if(line.matches("[a-z]\\).*"))
            return 6;
        return -1;
    }


}