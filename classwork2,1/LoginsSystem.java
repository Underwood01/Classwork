import java.util.Scanner;

public class LoginSystem {

    public static void main(String[] args) {

        final String USERNAME = "admin";

        final String PASSWORD = "1234";

        Scanner scanner = new Scanner(System.in);

        int attempts = 0;

        boolean success = false;

        // Loop for 3 attempts

        while (attempts < 3) {

            System.out.print("Enter username: ");

            String inputUser = scanner.nextLine();

            System.out.print("Enter password: ");

            String inputPass = "";

            // Show each char as typed

            for (int i = 0; i < PASSWORD.length(); i++) {

                char ch = scanner.next().charAt(0);

                inputPass += ch;

                System.out.print(ch); // print char

            }

            scanner.nextLine(); // clear buffer

            System.out.println();

            // Check credentials

            if (inputUser.equals(USERNAME) && inputPass.equals(PASSWORD)) {

                System.out.println("Login successful.");

                success = true;

                break;

            } else {

                System.out.println("Invalid login.");

                attempts++;

            }

        }

        if (!success) {

            System.out.println("Access blocked after 3 attempts.");

        }

        scanner.close();

    }

}