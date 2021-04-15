import java.util.Objects;

public class TextBlocks {
    public static void main(String[] args) {
        Tuple strings = helloWorld();
//        Tuple strings = helloWorldDelimiterNewLine();
//        Tuple strings = helloWorldTrailingSpace();
//        Tuple strings = helloWorldWithTabWhitespace();
//        Tuple strings = helloWorldWithTabEscaped();
//        Tuple strings = helloWorldQuotationMarksEscaped();
//        Tuple strings = helloWorldWindowsLineEnding();

        String rd = "\n" + "=".repeat(30) + "\n";
        System.out.printf("Text block:%s%s%s\n", rd, strings.textBlock, rd);
        if (!Objects.equals(strings.textBlock, strings.plainString))
            System.out.printf("Strings are not equals. Plain string:%s%s%s\n", rd, strings.plainString, rd);
        if ( !(strings.textBlock == strings.plainString) )
            System.out.printf("Strings are not equals. Plain string:%s%s%s\n", rd, strings.plainString, rd);

    }

    public static Tuple helloWorld() {
        return new Tuple(

                """
                        Hello world!""",

                "Hello world!"
        );
    }

    public static Tuple helloWorldDelimiterNewLine() {
        return new Tuple(

                """
                      Hello world!
                      """,

                "Hello world!\n"
        );
    }

    public static Tuple helloWorldTrailingSpace() {
        return new Tuple(

                """
                      Hello world!    
                         """,

                "Hello world!\n"
        );
    }

    public static Tuple helloWorldWithTabWhitespace() {
        return new Tuple(

                """
                      Hello world!
                	     Hello world!""",

                "Hello world!\nHello world!"
        );
    }

    public static Tuple helloWorldWithTabEscaped() {
        return new Tuple(

                """
                   Hello world!
                \tHello world!""",

                "   Hello world!\n\tHello world!"
        );
    }

    public static Tuple helloWorldQuotationMarksEscaped() {
        return new Tuple(

                """
                        "Hello world!"
                        ""Hello world!""
                        \"""Hello world!\""" """,

                "\"Hello world!\"" +
                        "\n\"\"Hello world!\"\"" +
                        "\n\"\"\"Hello world!\"\"\""
        );
    }

    //if we want to have carriage return, we must explicitly add it since java converts all to \n
    public static Tuple helloWorldWindowsLineEnding() {
        return new Tuple(

                """
                        Hello world!\r
                        Hello world!""",

                "Hello world!\r\nHello world!"
        );
    }

    static class Tuple {
        String textBlock;
        String plainString;

        Tuple(String textBlock, String plainString) {
            this.textBlock = textBlock;
            this.plainString = plainString;
        }
    }
}
