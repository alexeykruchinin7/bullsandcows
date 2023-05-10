import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

class BullsAndCows {
    public static void main(String[] args) {
        //TODO работу с консолью (вывод и ввод можно вынести в отдельный класс)
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

            //TODO логику вычисления коров и быков тоже вынести в отдельный класс
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
            //TODO грамотно обошел не использовать окончания слов, ну ОК ))
            System.out.println("Быки: " + bulls + "; Коровы: " + cows);
        }
    }
}

//TODO тогда уж лучше WriterResult
class WriteResult{
    public static void saveResult(String result) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("results.txt", true);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            writer.write(format.format(new Date()) + " - " + result + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл!");
            //TODO чтобы не писать весь этот блок finally. Используй try(FileWriter = ...) {}
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

//TODO Лучше тогда назвать GeneratorNumber, потому что класс это представление какой-то сущности (некритично)
//TODO Каждый класс должен быть в отдельном файле (некритично)
class GenerateNumber {
    //TODO смотри в этом классе у тебя нет свойств (характеристик), то есть метод можно сделать static (некритично)
    //TODO модификатор доступа забыл (некритично)
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

//TODO 1) Посмотри в каком формате должен храниться результат игры (критично). У тебя сейчас сохраняется только последняя
//      строчка, что человек победил
//     2) Нет вычисления номера последней игры
//     3) После окончания, нужно предложить человеку сыграть еще раз