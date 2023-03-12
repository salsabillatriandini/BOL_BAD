import java.util.Scanner;

public class CheckAngkaPrima {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input sebuah angka bilangan bulat: ");
        int angka = scanner.nextInt();

        String hasil = "";
        boolean isPrimary = true;

        if (angka <= 1) {
            isPrimary = false;
        } else {
            for (int i = 2; i <= Math.sqrt(angka); i++) {
                if (angka % i == 0) {
                    isPrimary = false;
                    hasil = angka + " bukan angka prima karena bisa dibagi " + i;
                    break;
                }
            }
        }

        if (hasil == "") {
            if (isPrimary) {
                hasil = angka + " adalah bilangan prima";
            } else {
                hasil = angka + " bukan bilangan prima";
            }
        }

        System.out.println(hasil);
    }
}
