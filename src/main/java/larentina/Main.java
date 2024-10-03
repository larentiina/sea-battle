package larentina;

import larentina.io.GameIO;

import larentina.utils.GameSimulation;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        new GameSimulation(new GameIO(new Scanner(System.in))).start();
    }
}