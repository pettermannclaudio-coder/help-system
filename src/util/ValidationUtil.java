package util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final int NOME_MINIMO = 3;
    private static final int NOME_MAXIMO = 150;

    private static final int EMAIL_MAXIMO = 150;

    private static final int SENHA_MINIMA = 8;
    private static final int SENHA_MAXIMA = 72;

    private static final Pattern NOME_PATTERN = Pattern.compile(
            "^[\\p{L}\\p{M}]+(?:[ .'’-][\\p{L}\\p{M}]+)*$");

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+"
                    + "@"
                    + "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"
                    + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+$");

    private ValidationUtil() {
        throw new IllegalStateException(
                "Classe utilitária não pode ser instanciada.");
    }

    public static String validarENormalizarNome(String nome) {
        String valor = normalizarTexto(nome);

        validarObrigatorio(valor, "Informe o nome.");

        validarTamanho(
                valor,
                NOME_MINIMO,
                NOME_MAXIMO,
                "O nome deve possuir entre "
                        + NOME_MINIMO
                        + " e "
                        + NOME_MAXIMO
                        + " caracteres.");

        validarUnicode(valor, "O nome contém caracteres inválidos.");

        if (!NOME_PATTERN.matcher(valor).matches()) {
            throw new IllegalArgumentException(
                    "O nome deve conter apenas letras, espaços, pontos, "
                            + "apóstrofos ou hífens.");
        }

        return valor;
    }

    public static String validarENormalizarEmail(String email) {
        String valor = normalizarTexto(email).toLowerCase();

        validarObrigatorio(valor, "Informe o e-mail.");

        if (valor.length() > EMAIL_MAXIMO) {
            throw new IllegalArgumentException(
                    "O e-mail deve possuir no máximo "
                            + EMAIL_MAXIMO
                            + " caracteres.");
        }

        validarUnicode(valor, "O e-mail contém caracteres inválidos.");

        if (contemEspaco(valor)) {
            throw new IllegalArgumentException(
                    "O e-mail não pode conter espaços.");
        }

        if (!EMAIL_PATTERN.matcher(valor).matches()) {
            throw new IllegalArgumentException(
                    "Informe um e-mail válido.");
        }

        validarPartesEmail(valor);

        return valor;
    }

    public static void validarSenha(String senha) {
        validarObrigatorio(senha, "Informe a senha.");

        if (senha.length() < SENHA_MINIMA) {
            throw new IllegalArgumentException(
                    "A senha deve possuir pelo menos "
                            + SENHA_MINIMA
                            + " caracteres.");
        }

        if (senha.length() > SENHA_MAXIMA) {
            throw new IllegalArgumentException(
                    "A senha deve possuir no máximo "
                            + SENHA_MAXIMA
                            + " caracteres.");
        }

        validarUnicode(
                senha,
                "A senha contém caracteres inválidos ou corrompidos.");

        if (contemControle(senha)) {
            throw new IllegalArgumentException(
                    "A senha não pode conter quebras de linha, tabulações "
                            + "ou caracteres de controle.");
        }

        boolean possuiLetra = senha.codePoints()
                .anyMatch(Character::isLetter);

        boolean possuiNumero = senha.codePoints()
                .anyMatch(Character::isDigit);

        if (!possuiLetra || !possuiNumero) {
            throw new IllegalArgumentException(
                    "A senha deve possuir pelo menos uma letra e um número.");
        }
    }

    public static Integer validarDepartamentoId(
            Integer departamentoId) {
        if (departamentoId == null || departamentoId <= 0) {
            throw new IllegalArgumentException(
                    "Selecione um departamento válido.");
        }

        return departamentoId;
    }

    public static void validarTipoUsuario(Object tipoUsuario) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException(
                    "Selecione um tipo de usuário.");
        }
    }

    public static String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        String normalizado = Normalizer.normalize(
                texto,
                Normalizer.Form.NFC);

        return normalizado
                .trim()
                .replaceAll(" {2,}", " ");
    }

    private static void validarObrigatorio(
            String valor,
            String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    private static void validarTamanho(
            String valor,
            int minimo,
            int maximo,
            String mensagem) {
        int tamanho = valor.codePointCount(
                0,
                valor.length());

        if (tamanho < minimo || tamanho > maximo) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    private static void validarUnicode(
            String valor,
            String mensagem) {
        if (valor.indexOf('\uFFFD') >= 0) {
            throw new IllegalArgumentException(mensagem);
        }

        for (int i = 0; i < valor.length(); i++) {
            char atual = valor.charAt(i);

            if (Character.isHighSurrogate(atual)) {
                if (i + 1 >= valor.length()
                        || !Character.isLowSurrogate(
                                valor.charAt(i + 1))) {
                    throw new IllegalArgumentException(mensagem);
                }

                i++;
            } else if (Character.isLowSurrogate(atual)) {
                throw new IllegalArgumentException(mensagem);
            }
        }
    }

    private static boolean contemControle(String valor) {
        return valor.codePoints().anyMatch(
                codigo -> Character.isISOControl(codigo));
    }

    private static boolean contemEspaco(String valor) {
        return valor.codePoints().anyMatch(
                codigo -> Character.isWhitespace(codigo));
    }

    private static void validarPartesEmail(String email) {
        int arroba = email.lastIndexOf('@');

        if (arroba <= 0 || arroba == email.length() - 1) {
            throw new IllegalArgumentException(
                    "Informe um e-mail válido.");
        }

        String parteLocal = email.substring(0, arroba);
        String dominio = email.substring(arroba + 1);

        if (parteLocal.length() > 64) {
            throw new IllegalArgumentException(
                    "A parte anterior ao @ é muito longa.");
        }

        if (parteLocal.startsWith(".")
                || parteLocal.endsWith(".")
                || parteLocal.contains("..")) {
            throw new IllegalArgumentException(
                    "Informe um e-mail válido.");
        }

        if (dominio.startsWith("-")
                || dominio.endsWith("-")
                || dominio.contains("..")) {
            throw new IllegalArgumentException(
                    "Informe um domínio de e-mail válido.");
        }
    }
}