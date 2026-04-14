package ec.edu.uce;

import ec.edu.uce.cifrados.CifradoAtbash;
import ec.edu.uce.cifrados.CifradoCesar;
import ec.edu.uce.cifrados.CifradoVigenere;
import ec.edu.uce.cifrados.Cifrador;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sr = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n========================================");
            System.out.println("      SISTEMA DE CIFRADO MODULAR");
            System.out.println("========================================");
            System.out.println("1. Cesar    (Sustitucion por desplazamiento)");
            System.out.println("2. Atbash   (Espejo del alfabeto)");
            System.out.println("3. Vigenere (Polialfabetico con clave)");
            System.out.println("4. Salir");
            System.out.print("Elige una opcion: ");

            int opcion = sr.nextInt();
            sr.nextLine(); // Limpiar buffer

            if (opcion == 4) {
                salir = true;
                System.out.println("Saliendo del sistema...");
                break;
            }

            Cifrador miCifrado = null;

            switch (opcion) {
                case 1 -> {
                    System.out.println("\n[CIFRADO CESAR]");
                    System.out.println("Descripción: Desplaza cada letra un número fijo de posiciones en el alfabeto.");
                    System.out.print("Ingresa el desplazamiento (número): ");
                    int desplazamiento = sr.nextInt();
                    sr.nextLine();
                    miCifrado = new CifradoCesar(desplazamiento);
                }
                case 2 -> {
                    System.out.println("\n[CIFRADO ATBASH]");
                    System.out.println("Descripción: Sustituye la primera letra (A) por la última (Z), la segunda (B) por la penúltima (Y), etc.");
                    miCifrado = new CifradoAtbash();
                }
                case 3 -> {
                    System.out.println("\n[CIFRADO VIGENERE]");
                    System.out.println("Descripción: Utiliza una palabra clave para aplicar diferentes desplazamientos a cada letra.");
                    System.out.print("Ingresa la palabra clave: ");
                    String clave = sr.nextLine();
                    //miCifrado = new CifradoVigenere(clave);
                }
                default -> System.out.println("Opcion no válida. Intenta de nuevo.");
            }

            if (miCifrado != null) {
                System.out.print("Ingresa el texto a procesar: ");
                String texto = sr.nextLine();
                System.out.println("\n----------------------------------------");
                System.out.println("RESULTADO: " + miCifrado.cifrar(texto));
                System.out.println("----------------------------------------");
            }
        }
        sr.close();
    }
}
