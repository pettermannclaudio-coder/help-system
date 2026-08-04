package util;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Arrays;

public final class PasswordUtil {

    private static final int BCRYPT_ROUNDS = 12;

    private PasswordUtil() {
        throw new IllegalStateException(
                "Classe utilitária não pode ser instanciada.");
    }

    public static String gerarHash(String senha) {
        validarSenhaParaHash(senha);

        return BCrypt.hashpw(
                senha,
                BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    public static boolean verificarSenha(
            String senha,
            String hash) {
        if (senha == null
                || senha.isEmpty()
                || hash == null
                || hash.isBlank()) {
            return false;
        }

        try {
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public static String gerarHash(char[] senha) {
        if (senha == null || senha.length == 0) {
            throw new IllegalArgumentException(
                    "Informe a senha.");
        }

        try {
            return gerarHash(new String(senha));
        } finally {
            Arrays.fill(senha, '\0');
        }
    }

    public static boolean verificarSenha(
            char[] senha,
            String hash) {
        if (senha == null || senha.length == 0) {
            return false;
        }

        try {
            return verificarSenha(
                    new String(senha),
                    hash);
        } finally {
            Arrays.fill(senha, '\0');
        }
    }

    public static boolean hashValido(String hash) {
        if (hash == null || hash.isBlank()) {
            return false;
        }

        return hash.matches(
                "^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");
    }

    private static void validarSenhaParaHash(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe a senha.");
        }

        if (senha.getBytes().length > 72) {
            throw new IllegalArgumentException(
                    "A senha ultrapassa o limite permitido.");
        }

        boolean possuiCaractereDeControle = senha
                .codePoints()
                .anyMatch(Character::isISOControl);

        if (possuiCaractereDeControle) {
            throw new IllegalArgumentException(
                    "A senha contém caracteres de controle inválidos.");
        }
    }
}
