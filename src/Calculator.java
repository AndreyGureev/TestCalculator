import java.io.IOException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Calculator {
    public static void main(String[] args) throws DataFormatException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input: ");
        String user = scanner.nextLine().trim();
        System.out.println("Output: ");
        System.out.println(calc(user));
        scanner.close();
    }

    public static String calc(String input) throws DataFormatException, IOException {
        input = input.replaceAll(" +", ""); // Заменяем пробелы пустотой, разделителем будет оператор (+, -, /, *).
        input = input.toUpperCase();     // Меняем регистор для исключения ошибки прочтения из-за разницы в регистрах.
        String[] data = input.split("[+-/*/]");         // Делим строки на операнды.
        char operator = input.charAt(data[0].length());       // Вычисляем оператор.

        if (data.length == 1)               // Выводим исключение, т.к операнд введен один.
            throw new IOException("//т.к. строка не является математической операцией"); // Или между числами стоит символ не являющийся оператором.
        if (data.length > 2)                // Выводим исключение, т.к. операнд введено более двух.
            throw new IOException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        boolean numRoman1 = data[0].matches("[IVX]+");      // Состоят ли операнды целиком из римских символов
        boolean numRoman2 = data[1].matches("[IVX]+");

        if ((numRoman1 && !numRoman2) || (!numRoman1 && numRoman2))
            throw new NumberFormatException("// т.к. используются одновременно разные системы счисления"); // выводим исключение если одно из чисел является риским а другое арабским

        int a, b;
        int result;

        if (!numRoman1 && !numRoman2) {                           // Преобразование арабских значений в Int
            try {
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[1]);
            } catch (Exception e) {
                throw new IOException("Ошибка при вводе чисел!"); // Вывод ошибки преобразования string в int
            }
        } else {                                                  // Преобразование римских значений в Int
            a = romanToInt(data[0]);
            b = romanToInt(data[1]);
        }

        if (!(a > 0 && a <= 10) || !(b > 0 && b <= 10)) // Вывод исключения при значения операндов не входящих в диапазон от 1 до 10
            throw new IOException("//т.к. калькудятор принимает на вход числа от 1 до 10 включительно, не более");

        switch (operator) {                                       // Выполнения арифметических операций +,-,/,*
            case '+':
                result = (a + b);
                break;
            case '-':
                result = (a - b);
                break;
            case '/':
                result = (a / b);
                break;
            case '*':
                result = (a * b);
                break;
            default:
                throw new DataFormatException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if (!numRoman1 && !numRoman2) {
            return Integer.toString(result);                      // Вывод ответа для арабских чисел
        } else if (result > 0) {
            return intToRoman(result);                            // Вывод ответа для римских чисел
        } else {
            throw new RuntimeException("//т.к. в римской системе нет отрицательных и нулевых чисел");   // Вывод исключения для римских чисел <=0
        }
    }

    public static int romanToInt(String value) { // Метод расчитан на значения от 01 до 39, начиная с 40 идет символ (XL)
        if (value.endsWith(Roman.IV.name()))
            return Roman.IV.value + romanToInt(value.substring(0, value.length() - 2)); // В первую очередь проверяются входжения сочетаний сиволов IV,IX для исключения
        if (value.endsWith(Roman.IX.name())) return Roman.IX.value + romanToInt(value.substring(0, value.length() - 2));
        if (value.endsWith("VX")) throw new NumberFormatException("Ошибка при вводе чисел!");
        if (value.endsWith(Roman.X.name())) return Roman.X.value + romanToInt(value.substring(0, value.length() - 1));
        if (value.endsWith(Roman.V.name())) return Roman.V.value + romanToInt(value.substring(0, value.length() - 1));
        if (value.endsWith(Roman.I.name())) return Roman.I.value + romanToInt(value.substring(0, value.length() - 1));
        if (value.isEmpty())
            return 0;                            // При достижении пустой строки рекурсия завершается
        throw new NumberFormatException("Ошибка при вводе чисел!");
    }

    public static String intToRoman(int val) {
        String result = "";            // Корректное отображение вплоть до 399, начиная с 400 идет символ D (CD)
        result += Roman.C.RepeatName(val / Roman.C.value);
        val %= Roman.C.value;
        result += Roman.XC.RepeatName(val / Roman.XC.value);
        val %= Roman.XC.value;
        result += Roman.L.RepeatName(val / Roman.L.value);
        val %= Roman.L.value;
        result += Roman.XL.RepeatName(val / Roman.XL.value);
        val %= Roman.XL.value;
        result += Roman.X.RepeatName(val / Roman.X.value);
        val %= Roman.X.value;
        result += Roman.IX.RepeatName(val / Roman.IX.value);
        val %= Roman.IX.value;
        result += Roman.V.RepeatName(val / Roman.V.value);
        val %= Roman.V.value;
        result += Roman.IV.RepeatName(val / Roman.IV.value);
        val %= Roman.IV.value;
        result += Roman.I.RepeatName(val);
        return result;
    }
}