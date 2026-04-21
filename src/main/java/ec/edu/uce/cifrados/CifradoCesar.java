package ec.edu.uce.cifrados;

public class CifradoCesar implements Cifrador {
    private int llave;
    // Definimos el alfabeto exacto de 27 letras
    private final String ALFABETO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    public CifradoCesar(int llave) {
        this.llave = llave;
    }

    @Override
    public String cifrar(String texto) {
        return procesar(texto, this.llave);
    }

    @Override
    public String descifrar(String texto) {
        // En un alfabeto de 27, el inverso es (27 - llave)
        return procesar(texto, 27 - (this.llave % 27));
    }

    private String procesar(String texto, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();
        String textoUpper = texto.toUpperCase(); // Trabajamos en mayúsculas para el ejemplo

        for (int i = 0; i < textoUpper.length(); i++) {
            char caracter = textoUpper.charAt(i);
            int indiceActual = ALFABETO.indexOf(caracter);

            if (indiceActual != -1) { // Si la letra existe en nuestro alfabeto
                // Aplicamos la fórmula: (x + n) mod 27
                int nuevoIndice = (indiceActual + desplazamiento) % 27;

                // Manejo de índices negativos para descifrado
                if (nuevoIndice < 0) nuevoIndice += 27;

                resultado.append(ALFABETO.charAt(nuevoIndice));
            } else {
                // Si es espacio o número, se queda igual
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }
}
