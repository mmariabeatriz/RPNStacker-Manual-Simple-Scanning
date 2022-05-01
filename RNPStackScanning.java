import java.util.Scanner;
import java.util.Stack;

/**
 * @author Henrique Rebelo
*/
enum TokenType {

	// Literals.
	NUM,

	// Single-character tokens for operations.
	MINUS, PLUS, SLASH, STAR,
	
	EOF

}

/**
 * @author Henrique Rebelo
 */
class Token {

	public final TokenType type; // token type
	public final String lexeme; // token value

	public Token (TokenType type, String value) {
		this.type = type;
		this.lexeme = value;
	}

	@Override
	public String toString() {
		return "Token [type=" + this.type + ", lexeme=" + this.lexeme + "]";
	}
}


public class RPN {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);  
        System.out.println("Bem-vindo a calculadora! (Digite SAIR para encerrar)");
        System.out.println("Digite inicie a operação desejada:");

        Stack<Integer> stack = new Stack<>();
        String singleChar = "";
        String file = "file.txt";
        int output = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((singleChar = br.readLine()) != null) {                  
                if (isInt(singleChar)) {                
                    Token t = new Token(TokenType.NUM , singleChar);
                    System.out.println(t.toString());
                    stack.push(Integer.parseInt(t.getLexeme()));
                } else if (isOp(singleChar)) {      
                    int current = parseOp(singleChar, stack);
                    stack.push(current);
                    output = current;                   
                }else {
                    System.out.println("Error: Unexpected character:" + singleChar);
                    break;                   
                }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");       // Erro ao abrir o arquivo.
            e.printStackTrace();
        }
        System.out.println(output);
    }

    public static int calculate(String operation, int left, int right) {
        if (operation.equals("+")) {
            return left + right;
        }else if (operation.equals("-")) {
            return left - right;
        }else if (operation.equals("*")) {
            return left * right;
        }else if (operation.equals("/")) {
            return left / right;
        }else {
            return left;
        }
    }
    
    public static boolean isOp(String input) {
        if (operation.equals("+")) {
            Token t = new Token(TokenType.PLUS , input);
            System.out.println(t.toString());
            return true;
        }else if (operation.equals("-")) {
            Token t = new Token(TokenType.MINUS , input);
            System.out.println(t.toString());
            return true;
        }else if (operation.equals("*")) {
            Token t = new Token(TokenType.STAR , input);
            System.out.println(t.toString());
            return true;
        }else if (operation.equals("/")) {
            Token t = new Token(TokenType.SLASH , input);
            System.out.println(t.toString());
            return true;
        }else {
            return false;
        }
    }

    public static boolean isInt(String input) {
        if (input == null) return false;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

    
    public static int parseOp(String operation, Stack<Integer> stack) {
        int output = stack.pop();

        if (!stack.empty()) {
            output = calculate(operation, stack.pop(), output);
        }

        return output;
    }  
   
}