import java.io.Serializable;

public class SwitchExpressions {

    public static void main(String[] args) {
        System.out.println(
        test13(-1)
//                testFallThrough(-1)
//                testThrow(-5)
//                testArrowSyntax(-5)
//                testPolyExpression(-5)
//                testStatement(-5)
        );
    }

    //java 13
    public static String test13(int arg) {
        return switch (arg) {
            case -1:
                yield "Yield case";
            case 0, 1, 2:
                yield "Multiple labels case";
            default:
                int mul = arg * 2;
                yield Integer.toString(mul);
        };
    }

    //fallthrough in expression
    public static String testFallThrough(int arg) {
        return switch (arg) {
            case -1:
                System.out.println("fallthrough here");
            case 0, 1, 2:
                yield "Multiple labels case";
            default:
                yield "Default";
        };
    }

    //throw in expression
    public static String testThrow(int arg) {
        return switch (arg) {
            case -1:
                System.out.println("fallthrough here");
            case 0, 1, 2:
                yield "Multiple labels case";
            default:
                throw new IllegalArgumentException("Unknown integer");
        };
    }

    //arrow syntax
    public static String testArrowSyntax(int arg) {
        return switch (arg) {
            case -1 ->
                    "Yield case";
            case 0, 1, 2 ->
                    "Multiple labels case";
            default -> {
                int mul = arg * 2;
                yield Integer.toString(mul);
            }
        };
    }

    //poly expression
    public static Serializable testPolyExpression(int arg) {
        //compiler tries to find the most specific supertype
        var result = switch (arg) {
            case -1 ->
                    -1;
            case 0, 1, 2 ->
                    "Multiple labels case";
            default -> {
                int mul = arg * 2;
                yield Integer.toString(mul);
            }
        };
        return result;
    }

    public static String testStatement(int arg) {
        switch (arg) {
            case -1 -> {return "Minus one";}
            case 0 -> {return "Zero";}
            default -> {return "Default";}
        }
    }
}
