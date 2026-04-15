package ec.edu.uce;

import ec.edu.uce.cifrados.*;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sr = new Scanner(System.in);
        boolean salir = false;

        System.out.println("==================================================");
        System.out.println("   BIENVENIDO AL SISTEMA MODULAR DE CIFRADO");
        System.out.println("==================================================");

        while (!salir) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("Elige un metodo de cifrado:");
            System.out.println("1. Cesar      - Desplaza las letras un numero fijo de posiciones.");
            System.out.println("2. Atbash     - Invierte el abecedario (A->Z, B->Y). No requiere clave.");
            System.out.println("3. Vigenere   - Usa una palabra clave para un desplazamiento variable.");
            System.out.println("4. Rail Fence - Escribe el texto en zigzag usando varios 'rieles'.");
            System.out.println("5. Playfair   - Encripta pares de letras usando una matriz 5x5.");
            System.out.println("6. Salir      - Cerrar el programa.");
            System.out.print("\nTu opcion: ");

            int opcion = sr.nextInt();
            sr.nextLine(); // Limpiar el buffer del scanner

            // Validamos si el usuario quiere salir o si ingresó una opción incorrecta
            if (opcion == 6) {
                salir = true;
                System.out.println("Cerrando el sistema.");
                continue;
            } else if (opcion < 1 || opcion > 6) {
                System.out.println("⚠️ Opción no válida. Por favor, elige un número del 1 al 6.");
                continue;
            }

            // Apartado para ingresar el texto a cifrar
            System.out.println("\n--- APARTADO DE ENTRADA ---");
            System.out.print("Ingresa el texto a procesar: ");
            String textoEntrada = sr.nextLine();

            // Usamos nuestra interfaz Polimórfica
            Cifrador miCifrado = null;

            // El switch determina qué clase instanciar y qué datos extra pedir
            switch (opcion) {
                case 1:
                    System.out.print("Ingresa el desplazamiento (número): ");
                    int llaveCesar = sr.nextInt();
                    miCifrado = new CifradoCesar(llaveCesar);
                    break;

                case 2:
                    System.out.println("Aplicando Atbash (no requiere clave)...");
                    miCifrado = new CifradoAtbash();
                    break;

                case 3:
                    System.out.print("Ingresa la palabra clave: ");
                    String claveVigenere = sr.nextLine();
                    miCifrado = new CifradoVigenere(claveVigenere);
                    break;

                case 4:
                    System.out.print("Ingresa el número de rieles (niveles): ");
                    int rieles = sr.nextInt();
                    miCifrado = new CifradoRailFence(rieles);
                    break;

                case 5:
                    System.out.print("Ingresa la palabra clave: ");
                    String clavePlayfair = sr.nextLine();
                    miCifrado = new CifradoPlayfair(clavePlayfair);
                    break;
            }

            // Apartado de salida
            System.out.println("\n--- APARTADO DE SALIDA ---");
            // Verificamos que el cifrado se haya instanciado correctamente (para evitar errores si aún no creas todas las clases)
            if (miCifrado != null) {
                String textoSalida = miCifrado.cifrar(textoEntrada);
                System.out.println("Texto Original : " + textoEntrada);
                System.out.println("Texto Cifrado  : " + textoSalida);
            } else {
                System.out.println("Este modulo de cifrado aun está en construccion.");
            }

            System.out.println("==================================================");
        }

        sr.close();
    }
}
