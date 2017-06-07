/*
Escribir un programa que lea una lista de nÃºmeros desde un archivo.
El objetivo del programa es presentar por pantalla la suma total de la lista de nÃºmeros.
El programa debe predir al usuario que ingrese la cantidad de hilos que desea crear.
Se debe dividir la lista de nÃºmeros en la cantidad hilos que especifique el usuario y cada parte de la lista
asignarle a un hilo.
La lista de nÃºmeros solo puede contener una cantidad par de elementos y la cantidad de hilos que especifique el
usuario debe ser tambiÃ©n un nÃºmero par.
Cada hilo debe realizar la suma de la sublista de nÃºmeros que ha recibido
El hilo principal debe encargarse de recibir los resultados parciales de cada hilo y sumarlas
para presentar el resultado total.
 */

package sumaparalelanhilos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SumaParalelaNHilos extends Thread {
    
    static ArrayList<Integer> principal = new ArrayList<Integer>();
    static ArrayList<Integer> totales  = new ArrayList<Integer>();
    ArrayList<Integer> numeros;
    int sum = 0;
  
    private static void abrir(String texto) {
        try {
            FileReader fr = new FileReader(texto);
            BufferedReader bf = new BufferedReader(fr);
            String Linea;
            while ((Linea = bf.readLine())!=null) 
            {
                principal.add(Integer.parseInt(Linea));
            }
            
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
   
    public SumaParalelaNHilos(ArrayList<Integer> num)
    {
        this.numeros = num;
    }
    
    public static void llenar(int incio,ArrayList<Integer> parte1 ,int fin )
    {
        for (int i = incio; i < fin; i++) 
        {
            parte1.add(principal.get(i));
        }
    }
    public static void CantidadHilos (String[] ar) throws InterruptedException{
        
        ArrayList<Integer> parte1 = new ArrayList<Integer>();
        ArrayList<Integer> parte2  = new ArrayList<Integer>();
                
        abrir(ar[0]);
        
        int division = (principal.size())/2;
        
        llenar(0,parte1,division);
        llenar(division,parte2,principal.size());
        
        SumaParalelaNHilos Hilo1 = new SumaParalelaNHilos(parte1);
        SumaParalelaNHilos Hilo2 = new SumaParalelaNHilos(parte2);
        Thread hilo1 = new Thread(Hilo1);
        Thread hilo2 = new Thread(Hilo2);
        hilo1.start();
        hilo2.start();
        hilo1.join();
        hilo2.join();
    }
       public static void inprimirHilos(int[] a){
       String array="";
       for (int i = 0; i < a.length; i++) 
       {
           array=array+a[i]+" ";
       }
       System.out.println("numeros: [ "+array+"]");
   }
    
    @Override
    public void run(){
        for (int i = 0; i < this.numeros.size(); i++) 
        {
            System.out.println(""+this.numeros.get(i));
            sum = sum + this.numeros.get(i);
        }
        totales.add(sum);
    }
    public static void main(String[] args) throws InterruptedException {
        
        int x,numHilos,elementos;
        int [] numeros;
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el numero de Hilos: ");
        elementos = sc.nextInt();
        numeros= new int[elementos];
        for (int i = 0; i < elementos; i++) 
        {
            System.out.print((i+1)+": ");
            numeros[i] = sc.nextInt();
        }
        
        inprimirHilos(numeros);
        System.out.println("archivo txt:");
        int total = 0;
        CantidadHilos(args);
        for (int i = 0; i < totales.size(); i++) 
        {
            total = total + totales.get(i);
        }
        System.out.println("La lista total de los numeros extraidos son: "+total);    
    }  
}
