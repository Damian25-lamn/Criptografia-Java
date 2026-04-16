package ec.edu.uce.cifrados;

public class CifradoVigenere implements Cifrador {
    private String llave;

    public CifradoVigenere(String llave) {
        this.llave = llave.toUpperCase().replaceAll("[^A-Z]", "");
        if (this.llave.isEmpty()) {
            this.llave = "A"; // Prevención de error por clave vacía
        }
    }

    @Override
    public String cifrar(String texto) {
        return procesar(texto, true);
    }

    @Override
    public String descifrar(String texto) {
        return procesar(texto, false);
    }

    // Metodo auxiliar para manejar ambos procesos
    private String procesar(String texto, boolean esCifrado) {
        StringBuilder resultado = new StringBuilder();
        int indiceLlave = 0;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);

            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                char letraLlave = llave.charAt(indiceLlave % llave.length());
                int desplazamiento = letraLlave - 'A';

                // LÓGICA DE DESCIFRADO
                if (!esCifrado) {
                    desplazamiento = -desplazamiento;
                }

                // Aseguramos que el resultado sea positivo sumando 26 antes del módulo
                char nuevoCaracter = (char) (base + (c - base + desplazamiento + 26) % 26);
                resultado.append(nuevoCaracter);

                indiceLlave++;
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
}
