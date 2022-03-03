import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        String[][] tasks = new String[0][0];                 //toworzymy tablice napisow
        tasks= readTask(tasks,"tasks.csv");          //wczytujemy plik
        Scanner scanner = new Scanner(System.in);            //wczytujemy skanet zeby mozna bylo wyswietlac na terminalu

        while (true){                                        // Tworzymy petle zeby wczytala true jak bedzie poprwne

            System.out.println(ConsoleColors.BLUE+"Please select an options:");      //kolor niebieski wywolany z ConsoleColors
            System.out.println(ConsoleColors.RESET+"add");                           // cofniety kolor niebieski poprzez Reset
            System.out.println("remove");
            System.out.println("list");
            System.out.println("exit");
            String userInput= scanner.nextLine();
            if (userInput.equals("add")){                               //equals porownuje nam czy dane String jest taki sam
                tasks=add(tasks);                                       //wczytanie metody add
            }else if (userInput.equals("remove")){
                tasks=remove(tasks);

            }else if (userInput.equals("list")){
                list(tasks);
            }else if (userInput.equals("exit")){
                saveTask(tasks,"tasks.csv");                    //podanie metody zapisu i przypisania do pliku odbywa sie to na koncu
                break;                                                  // przerwanie jesli by nie bylo break to petla by krazyla caly czas
            }
        }
    }
    public static String[][] readTask(String[][] tab, String newTask){   // metoda wczytania pliku
        int index=0;
        File file =new File(newTask);                                   //wczytanie pliku
        try{
            Scanner scanner =new Scanner(file);   //wczytanie skanera od pliku ktory zostal wczytany
            while (scanner.hasNextLine()){        //petla dynamiczne dodaewanie
                String[] s=scanner.nextLine().split(", ");//podział na elementy
                tab=Arrays.copyOf(tab,tab.length+1); //dodawanie dynamiczne
                tab[index]=s;
                index++;
            }
        }catch (FileNotFoundException e){    //catch plik nie zostal znaleziony
            e.printStackTrace();               // komunikat z FileNotFound
            System.out.println("File not found!"); // komunikat przypisany przez nas
        }
        return tab;
    }
    public static String[][] add(String[][] tab){     //metoda dodawania wiersza w tablicy na koncu
        Scanner scanner=new Scanner(System.in);    //wczytanie skanera
        System.out.println("Please add task description");  //prosimy o podanie pierwszego skladnika bez sprawdzania
        String first=scanner.nextLine();              //wywolanie skanera
        System.out.println("Please add task due date");    //prosimy o podanie drugiego skladnika bez sprawdzania
        String second=scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        while (!scanner.hasNextBoolean()){            //sprawdzamy czy wynik jest prawda/fałsz
            scanner.nextLine();
            System.out.println("Please write true/false"); //jeśli  nie prosimy o podanie prawda/fałsz
        }
        boolean third=scanner.hasNextLine();

        tab=Arrays.copyOf(tab,tab.length+1);  //powiekszamy o 1 nasza tablice
        tab[tab.length-1]=new String[3];    //tworzymy na koncu(tab.length-1) naszej tab nowa tab z 3 elementami
        tab[tab.length-1][0]=first;         //przypisujemy pierwsze wyrazenie w stworzonej tab
        tab[tab.length-1][1]=second;        //przypisujemy drugie wyrarzenie
        tab[tab.length-1][2]=Boolean.toString(third);  //przypisujemy trzecie wyrazenie i zmieniamy z boolean na String
        return tab;
    }
    public static String[][] remove(String[][] tab){   //metoda usuwania z tablicy jednego wiersza i przerzucenie wszystkiego w gore
        System.out.println("Please select number to remove:");
        Scanner scanner=new Scanner(System.in);    //uruchomienie skanera
        int removeNumber=0;                        //przypisanie poczatkowego id
        while (true){                              //petla sprawdzajac poprawnosc
            try{
                removeNumber=Integer.parseInt(scanner.nextLine());
                if (removeNumber>=0 && removeNumber< tab.length){            //przedzial od 0 do ilosci wierszy
                    tab=ArrayUtils.remove(tab, removeNumber);                // metoda z Arraya
                    break;                                                   //przerwanie jesli bedzie w zakresie id
                }else{
                    System.out.println("The number you selected is out of range. Try again.");  // jesli podana wartosc nie jest w zakresie
                }
            }catch (IndexOutOfBoundsException e){              //catch jesli numer z poza przedzialu
                System.out.println("Invalid number!");
            }
        }
        return tab;
    }
    public static void list(String[][] tab){               //metoda wyswietlenia listy
        for (int i=0; i< tab.length;i++){                   //
            System.out.print(i+" ");                        // petla przechodzaca po wszystkich elementach tablicy
            for (int j=0; j<tab[i].length;j++){             // dwuwymiarowej
                System.out.print(tab[i][j]+ " ");
            }
            System.out.println();
        }
    }
    public static void saveTask (String[][]tab,String newTask){  //metoda zapisu do pliku
        try(PrintWriter printWriter=new PrintWriter(newTask)){   //wczytanie PrintWriter-a
            for(int i=0;i< tab.length;i++){
                for (int j=0; j<tab[i].length;j++){
                    printWriter.print(tab[i][j]+", ");
                }
                printWriter.print(tab[i][tab[i].length-1]+"\n");       //wyswietlenie w osobnej linii
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Error saving the file!");
        }
        System.out.println(ConsoleColors.RED+"Bye, bye.");
    }
}
