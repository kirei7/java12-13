
//compile with
// javac --enable-preview --release 12 PreviewFeature.java

//run with
// java --enable-preview PreviewFeature

public class PreviewFeature {
    public static void main(String[] args) {
        System.out.println(
                test(-1)
        );
    }

//    public static String test(int arg) {
//        return switch (arg) {
//            case -1:
//                break "Minus one";
//            case 0:
//                break "Zero";
//            default:
//                break "Any other";
//        };
//    }

    public static String test(int arg) {
        return "";
    }
}
