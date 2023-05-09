import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

class BullsAndCows {
    public static void main(String[] args) {
        System.out.println("Игра Быки и Коровы");
        GenerateNumber gen = new GenerateNumber();
        String genNumber = gen.generateNum();
        Scanner console = new Scanner(System.in);

        int guesses = 0;
        while (true) {
            System.out.print("Введите четырехзначное число: ");
            String guess = console.nextLine();
            if (guess.length() != 4 || !guess.matches("\\d+")) {
                System.out.println("Ошибка! Нужно ввести четырехзначное число!");
                continue;
            }

            guesses++;
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < 4; i++) {
                char digit = guess.charAt(i);

                if (digit == genNumber.charAt(i)) {
                    bulls++;
                } else if (genNumber.indexOf(digit) != -1) {
                    cows++;
                }
            }

            if (bulls == 4) {
                String result = "Победа! Количество попыток: " + guesses;
                System.out.println(result);
                WriteResult resulttext = new WriteResult();
                resulttext.saveResult(result);
                break;
            }
            System.out.println("Быки: " + bulls + "; Коровы: " + cows);
        }
    }
}

class WriteResult{
    public static void saveResult(String result) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("results.txt", true);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            writer.write(format.format(new Date()) + " - " + result + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл!");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class GenerateNumber {
    String generateNum() {
        Random random = new Random();
        String genNum;
        while (true) {
            genNum = String.format("%04d", random.nextInt(10000));
            if (genNum.chars().distinct().count() == 4) {
                break;
            }
        }
        return genNum;
    }
}