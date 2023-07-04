import java.util.*;

public class Main {
    public static String calc(String input) {
        String[] tokens = input.split(" "); // Разбиваем строку на токены по пробелу

        String num1 = tokens[0]; // Первое число
        String operator = tokens[1]; // Арифметический оператор
        String num2 = tokens[2]; // Второе число

        int operand1, operand2; // Целочисленные операнды
        boolean isRoman = false; // Флаг, указывающий на использование римских чисел

        try {
            operand1 = Integer.parseInt(num1); // Преобразуем первое число в целое число
            operand2 = Integer.parseInt(num2); // Преобразуем второе число в целое число
        } catch (NumberFormatException e) {
            // Если не удалось преобразовать числа, то предполагаем, что это римские числа
            operand1 = RomanNumeral.convertToInteger(num1); // Преобразуем первое число в арабское число
            operand2 = RomanNumeral.convertToInteger(num2); // Преобразуем второе число в арабское число
            isRoman = true; // Устанавливаем флаг использования римских чисел
        }

        if (operand1 < 1 || operand1 > 10 || operand2 < 1 || operand2 > 10) {
            // Если операнды не попадают в диапазон от 1 до 10, выбрасываем исключение
            throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно!");
        }

        int result; // Результат выполнения операции
        switch (operator) { // Проверяем арифметический оператор
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Некорректный оператор!"); // Если оператор некорректный, выбрасываем исключение
        }

        if (isRoman) { // Если использовались римские числа
            if (result <= 0) { // Если результат меньше или равен нулю, выбрасываем исключение
                throw new IllegalArgumentException("Результат с римскими числами не может быть меньше 1!");
            }
            return RomanNumeral.convertToRoman(result); // Преобразуем результат в римские числа
        } else {
            return Integer.toString(result); // Преобразуем результат в строку
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение: ");
        String input = scanner.nextLine(); // Считываем введенное выражение

        scanner.close(); // Закрываем сканнер

        try {
            String result = calc(input); // Выполняем вычисления
            System.out.println("Результат: " + result); // Выводим результат
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage()); // Выводим сообщение об ошибке
        }
    }
}

class RomanNumeral {
    private static final String[] ROMAN_SYMBOLS = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
    private static final int[] ROMAN_VALUES = {1, 4, 5, 9, 10, 40, 50, 90, 100};

    public static String convertToRoman(int number) {
        StringBuilder roman = new StringBuilder(); // Строка для хранения результата преобразования в римские числа
        int i = ROMAN_VALUES.length - 1; // Индекс символов римских чисел

        while (number > 0) { // Пока число больше нуля
            if (number >= ROMAN_VALUES[i]) { // Если число больше или равно текущему символу римского числа
                roman.append(ROMAN_SYMBOLS[i]); // Добавляем символ в римскую строку
                number -= ROMAN_VALUES[i]; // Вычитаем значение символа из числа
            } else {
                i--; // Переходим к предыдущему символу
            }
        }

        return roman.toString(); // Возвращаем римское число в виде строки
    }

    public static int convertToInteger(String roman) {
        int number = 0; // Переменная для хранения результата преобразования в арабское число
        int i = 0; // Индекс для итерации по символам римского числа

        while (i < roman.length()) { // Пока не достигнут конец строки
            String currentSymbol = roman.substring(i, i + 1); // Получаем текущий символ из строки

            if (i < roman.length() - 1) { // Если текущий символ не последний символ в строке
                String nextSymbol = roman.substring(i + 1, i + 2); // Получаем следующий символ
                String combinedSymbol = currentSymbol + nextSymbol; // Объединяем текущий и следующий символы

                int combinedValue = findRomanValue(combinedSymbol); // Ищем значение для объединенного символа

                if (combinedValue != -1) { // Если значение не равно -1 (допустимый символ римского числа)
                    number += combinedValue; // Добавляем значение к результату
                    i += 2; // Увеличиваем индекс на 2, чтобы пропустить следующий символ
                    continue; // Продолжаем с следующей итерации цикла
                }
            }

            int value = findRomanValue(currentSymbol); // Ищем значение для текущего символа

            if (value == -1) { // Если значение равно -1 (недопустимый символ римского числа)
                throw new IllegalArgumentException("Некорректное римское число: " + roman); // Выбрасываем исключение
            }

            number += value; // Добавляем значение к результату
            i++; // Увеличиваем индекс на 1, чтобы перейти к следующему символу
        }

        return number; // Возвращаем арабское число
    }

    private static int findRomanValue(String symbol) {
        for (int i = 0; i < ROMAN_SYMBOLS.length; i++) {
            if (ROMAN_SYMBOLS[i].equals(symbol)) { // Сравниваем символы римских чисел
                return ROMAN_VALUES[i]; // Возвращаем соответствующее значение
            }
        }

        return -1; // Если символ не найден, возвращаем -1
    }
}
