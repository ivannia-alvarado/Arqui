package proy_arqui;


import java.util.ArrayList;
import proy_arqui.CargadorArchivos;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Iva
 * Esta clase se encarga de controlar todo el multiprocesador, es el hilo principal
 * Aqui van a estar declaradas todas las estructuras que representan la memoria, cache y demas de cada procesador
 * Aqui inicia el programa
 */
public class Multiprocesador {

    private Simulacion sim;
    private Estadistica est;
    private Procesador proc1 = new Procesador(this);
    private ArrayList<Integer> instrucciones = new ArrayList<Integer>();
    private ArrayList<Integer> pcs = new ArrayList<Integer>();
    private int numHilitos; //cantidad de archivos cargados por el usuario
    
    public Multiprocesador(Simulacion sim, Estadistica est){
        this.sim = sim;
    }
    
    public void agregarInstruccion(int num){
        instrucciones.add(num);
    }
    
    public void verInstrucciones(){
        System.out.println("Se han cargado "+numHilitos+" programas.");
        System.out.println("El arreglo de instrucciones hasta el momento es el siguiente:");
        for(int i=0; i < instrucciones.size(); i++){
            System.out.print(instrucciones.get(i)+ " ");
        }
        System.out.println();
        System.out.println("Los indices donde inicia cada programa(PCs) son los siguientes:");
        for(int i=0; i < pcs.size(); i++){
            System.out.print(pcs.get(i)+ " ");
        }
        System.out.println();
        System.out.println();
    }
    
    public void sumarHilito(){
        numHilitos++;
    }
    
    public void agregarPc(){
        pcs.add(instrucciones.size());
    }
    
    public int getInstIdx(int idx){
        return instrucciones.get(idx);
    }
    
    public void correrProgramas(){
        int pcActual;
        int limite = -1;
        if(numHilitos!=0){
            for(int i = 0; i < numHilitos; i++){
                pcActual = pcs.get(i);
                if((i+1)<pcs.size()) limite = pcs.get(i+1); else limite = instrucciones.size()-pcActual;
                proc1.procesar(pcActual, limite);
                //est.agregarEstadistica(proc1.verEstado());
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Estadistica est = new Estadistica();
        Simulacion sim = new Simulacion(est);
        Multiprocesador mp = new Multiprocesador(sim, est);
        CargadorArchivos crg = new CargadorArchivos(mp, sim);
        crg.setVisible(true);
    }
 
}
