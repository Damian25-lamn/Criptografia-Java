package ec.edu.uce.cifrados;

import java.util.stream.Collectors;

public class CifradoPlayfair implements Cifrador {
    private String matriz;

    public CifradoPlayfair(String llave) {
        this.matriz = generarMatriz(llave);
    }

    @Override
    public String cifrar(String texto) {
        return procesar(prepararTexto(texto), true);
    }

    @Override
    public String descifrar(String texto) {
        // Asumimos que el texto a descifrar ya viene en pares (no lo preparamos insertando 'X')
        String limpio = texto.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
        return procesar(limpio, false);
    }

    private String procesar(String preparado, boolean esCifrado) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < preparado.length(); i += 2) {
            // Protección por si el texto a descifrar tiene longitud impar (no debería pasar si se cifró bien)
            if (i + 1 >= preparado.length()) break;

            char a = preparado.charAt(i);
            char b = preparado.charAt(i + 1);

            int idxA = matriz.indexOf(a);
            int idxB = matriz.indexOf(b);

            // Si hay letras raras que no están en la matriz (ej. X en texto cifrado que no generamos), las saltamos
            if (idxA == -1 || idxB == -1) continue;

            int filaA = idxA / 5, colA = idxA % 5;
            int filaB = idxB / 5, colB = idxB % 5;

            // Dirección del movimiento: +1 para cifrar, -1 (+4 en módulo 5) para descifrar
            int dir = esCifrado ? 1 : 4;

            if (filaA == filaB) {
                // Misma fila
                resultado.append(matriz.charAt(filaA * 5 + (colA + dir) % 5));
                resultado.append(matriz.charAt(filaB * 5 + (colB + dir) % 5));
            } else if (colA == colB) {
                // Misma columna
                resultado.append(matriz.charAt(((filaA + dir) % 5) * 5 + colA));
                resultado.append(matriz.charAt(((filaB + dir) % 5) * 5 + colB));
            } else {
                // Rectángulo (Igual para cifrar y descifrar)
                resultado.append(matriz.charAt(filaA * 5 + colB));
                resultado.append(matriz.charAt(filaB * 5 + colA));
            }
        }
        return resultado.toString();
    }

    //metodos adicionales
    private String generarMatriz(String clave){
        String alfabeto = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Sin la 'J'
        String base = (clave.toUpperCase() + alfabeto).replace("J", "I")
                .replaceAll("[^A-Z]", "");

        // Usamos funcionales para eliminar duplicados y mantener el orden
        return base.chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining())
                .substring(0, 25);
    }

    private String prepararTexto(String texto){
        String limpio = texto.toUpperCase().replace("J","I").replaceAll("[^A-Z]", "");
        // Insertamos 'X' entre letras iguales consecutivas usando Regex
        limpio = limpio.replaceAll("(.)(?=\\1)", "$1X");
        if (limpio.length() % 2 != 0) limpio += "X";
        return limpio;
    }
}
