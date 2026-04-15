package ec.edu.uce.cifrados;

import java.util.stream.Collectors;

public class CifradoPlayfair implements Cifrador {
    private String matriz;

    public CifradoPlayfair(String llave) {
        this.matriz = generarMatriz(llave);
    }

    @Override
    public String cifrar(String texto) {
        String preparado = prepararTexto(texto);
        StringBuilder resultado = new StringBuilder();

        // Recorremos el texto de dos en dos (dígrafos)
        for(int i=0; i<preparado.length();i+=2){
            char a= preparado.charAt(i);
            char b= preparado.charAt(i+1);

            //obtener las coordenadas de cada letra
            int idxA = matriz.indexOf(a);
            int idxB = matriz.indexOf(b);

            int filaA = idxA / 5, colA = idxA % 5;
            int filaB = idxB / 5, colB = idxB % 5;

            // Reglas basadas en columnas y filas
            if (filaA == filaB) {
                // Misma fila: desplazamos a la derecha
                resultado.append(matriz.charAt(filaA * 5 + (colA + 1) % 5));
                resultado.append(matriz.charAt(filaB * 5 + (colB + 1) % 5));
            } else if (colA == colB) {
                // Misma columna: desplazamos hacia abajo
                resultado.append(matriz.charAt(((filaA + 1) % 5) * 5 + colA));
                resultado.append(matriz.charAt(((filaB + 1) % 5) * 5 + colB));
            } else {
                // Rectángulo: intercambiamos columnas
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
