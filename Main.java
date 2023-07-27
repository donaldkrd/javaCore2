import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int valueX;
    private static int valueY;
    private static final int WIN_COUNT = 4;
    private static final char DOT_HUMAN = 'X'; // ход игрока
    private static final char DOT_AI = 'O'; // ход компьютера
    private static final char DOT0_EMPTY = '*'; // пустое поле
    private static Scanner scanner; // создаем объект типа сканер

    /**
     *  Создаем метод вызова сканера для ввода данных пользователем
     * @return возвращает данные пользователя
     */
    private static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in); // создали отложенную инициализацию
        }
        return scanner;
    }
    private static char[][] field; // игровое поле
    private static int fieldSizeX; // размерность поля по X
    private static int fieldSizeY; // размерность поля по Y
    private static Random random = new Random(); // создаем объект типа рандом

    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkGame(DOT_HUMAN, "Вы победили!!!"))
                    break;
                aiTurnHard();
                printField();
                if (checkGame(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.println("Вы хотите еще раз сыграть? (да - y)");
            if (!getScanner().next().equalsIgnoreCase("y"))
                break;
        }
    }

    /**
     * Создаем метод отрисовки поля и заполнения пустых ячеек
     */
    private static void initialize() {

        fieldSizeX = 5;
        fieldSizeY = 5;

        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++){
                field[x][y] = DOT0_EMPTY;
            }
        }
    }

    /**
     * Метод отрисовки игрового поля
     */
    private static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeX * 2 + 1; i++){
            System.out.print(i % 2 == 0 ? "-" : i / 2 + 1);
        }
        System.out.println();
        for (int x = 0; x < fieldSizeX; x++){
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeX; y++){
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Метод проверяющий общее состояние игры
     * @param c фишка игрока
     * @param string победный слоган
     * @return состояние игры (true - игра завершена, false - продолжается)
     */
    static boolean checkGame(char c, String string) {
        if (checkWinNew(c, valueX, valueY, WIN_COUNT)) {
            System.out.println(string);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; // игра продолжается
    }

    /**
     * Проверка победы
     * TODO: Задача 2: Проработать метод проверки победы в домашнем задании, необходимо использовать
     *       вспомогательные методы и циклы (например for)
     * @param c принимает фишку игрока
     * @return возвращает результат проверки
     */
    static boolean checkWin(char c) {
        // Проверка по 3-м горизонталям
        if (field[0][0] == c && field[0][1] == c && field[0][2] == c) return true;
        if (field[1][0] == c && field[1][1] == c && field[1][2] == c) return true;
        if (field[2][0] == c && field[2][1] == c && field[2][2] == c) return true;
        // Проверка по диагонали
        if (field[0][0] == c && field[1][1] == c && field[2][2] == c) return true;
        if (field[2][0] == c && field[1][1] == c && field[0][2] == c) return true;
        // Проверка по вертикали
        if (field[0][0] == c && field[1][0] == c && field[2][0] == c) return true;
        if (field[0][1] == c && field[1][1] == c && field[2][1] == c) return true;
        if (field[0][2] == c && field[1][2] == c && field[2][2] == c) return true;

        return false;
    }

    /**
     * Метод работает таким образом, что в качестве аргументов принимает фишку игрока, координаты его хода (Х и У)
     * и количество совпадений для выигрыша.
     * Получая координаты хода, метод проверяет наличие таких фишек по горизонтали в право, запоминает, потом проверяет
     * влево, запоминает. Если данного количества хватает для выигрыша - выдает соответствующий результат. Если нет -
     * обнуляет данные и проверяет аналогично по вертикали и диагоналям.
     * P.S. На семинаре предлагалось сделать так, чтобы была проверка каждой ячейки вправо, влево и диагональ - при
     * таком подходе, нам не нужно было бы передавать в метод координаты хода => в связи с этим, метод сделал согласно
     * своему первичному видению :-)
     * @param c Фишка игрока
     * @param a Координата по Х
     * @param b Координата по У
     * @param WIN_COUNT Количество фишек для выигрыша
     * @return выводит результат
     */
    static boolean checkWinNew(char c, int a, int b, int WIN_COUNT){
        //int count = 0;
        // Проверка по горизонтали
        if (Methods.checkHorizontal(c, valueX, valueY, WIN_COUNT, fieldSizeY, field)) return true;
        // Проверка по вертикали
        if (Methods.checkVertical(c, valueX, valueY, WIN_COUNT, fieldSizeX, field)) return true;
        // Проверка по диагонали (x+/y+)
        if (Methods.checkVerticalRL(c, valueX, valueY, WIN_COUNT, fieldSizeX, fieldSizeY, field)) return true;
        // Проверка по диагонали (x-/y+)
        if (Methods.checkVerticalLR(c, valueX, valueY, WIN_COUNT, fieldSizeX, fieldSizeY, field)) return true;
        return false;
    }

    /**
     * Проверка на ничью
     * @return возвращает результат проверки
     */
    private static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Обработка хода игрока
     */
    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты хода:");
            x = getScanner().nextInt() - 1;
            y = getScanner().nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y)); // именно в такой последовательности
// Составное условие - сперва проверяем границы игрового поля, и только потом на свободную ячейку
// в обратном случае, если ячейка не в границах поля, она всегда будет свободной, выпадет Exception
        field[x][y] = DOT_HUMAN;
        valueX = x;
        valueY = y;
    }

    /**
     * Обработка хода компьютера
     * TODO: Задача 3: Сделать так, чтобы компьютер мешал игроку победить
     */
    private static void aiTurn(){
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
        valueX = x;
        valueY = y;
    }

    /**
     * Метод работает следующим образом: есть флаг, который проверяет, нашлась ли ячейка,
     * сделав ход в которую, игрок победит. Циклом перебираем каждую ячейку, если она пустая, пробуем в неё
     * подставить фишку игрока и проверяем на выигрыш. Если будет победа, то в эту ячейку AI ставит свою фишку
     * фиксируем координаты AI, меняем флаг - ячейка нашлась, и выходим из цикла.
     * Если ячейка не нашлась, то рандомно ставим любую.
     * Учитывая задание, сделать так, чтобы AI мешал игроку - этого достаточно думаю))
     */
    private static void aiTurnHard(){
        int a, b;
        boolean checkTurn = false;
        char gamer = 'X';
        for (int x = 0; x < fieldSizeX; x++) {
            if (checkTurn) break;
            for (int y = 0; y < fieldSizeY; y++) {
                if (checkTurn) break;
                if (field[x][y] == DOT0_EMPTY) {
                    field[x][y] = gamer;
                    if (Methods.checkHorizontal(gamer, valueX, valueY, WIN_COUNT, fieldSizeY, field)) {
                        field[x][y] = DOT_AI;
                        valueX = x;
                        valueY = y;
                        checkTurn = true;
                        break;
                    }
                    if (Methods.checkVertical(gamer, valueX, valueY, WIN_COUNT, fieldSizeX, field)) {
                        field[x][y] = DOT_AI;
                        checkTurn = true;
                        valueX = x;
                        valueY = y;
                        break;
                    }
                    if (Methods.checkVerticalRL(gamer, valueX, valueY, WIN_COUNT, fieldSizeX, fieldSizeY, field)) {
                        field[x][y] = DOT_AI;
                        checkTurn = true;
                        valueX = x;
                        valueY = y;
                        break;
                    }
                    if (Methods.checkVerticalLR(gamer, valueX, valueY, WIN_COUNT, fieldSizeX, fieldSizeY, field)) {
                        field[x][y] = DOT_AI;
                        checkTurn = true;
                        valueX = x;
                        valueY = y;
                        break;
                    }
                    field[x][y] = DOT0_EMPTY;
                }
            }
        }
        if (!checkTurn) {
            do {
                a = random.nextInt(fieldSizeX);
                b = random.nextInt(fieldSizeY);
            } while (!isCellEmpty(a, b));
            field[a][b] = DOT_AI;
            valueX = a;
            valueY = b;
        }
    }

    /**
     * Метод принимает координаты ячейки и проверяет, свободна ли она
     * @param x координата по X
     * @param y координата по Y
     * @return возвращает свободна ли ячейка
     */
    private static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT0_EMPTY;
    }

    /**
     * Проверка вводимых данных игрока в рамках игрового поля
     * @param x координата по X
     * @param y координата по Y
     * @return возвращает в пределах ли поля ввел данные игрок
     */
    public static boolean isCellValid (int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }
}