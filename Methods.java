public class Methods {
    public static boolean checkHorizontal(char c, int a, int b, int WIN_COUNT, int fieldSizeY, char[][] field){
            int count = 0;
        for (int y = b; y < fieldSizeY; y++){
            if (field[a][y] == c) {
                count++;
            } else {
                break;
            }
        }
        for (int y = b - 1; y >= 0; y--) {
            if (field[a][y] == c) {
                count++;
            } else {
                break;
            }
        }
        if (WIN_COUNT - count <= 0) return true;
        return false;
    }

    public static boolean checkVertical(char c, int a, int b, int WIN_COUNT, int fieldSizeX, char[][] field) {
        int count = 0;
        for (int x = a; x < fieldSizeX; x++) {
            if (field[x][b] == c) {
                count++;
            } else {
                break;
            }
        }
        for (int x = a - 1; x >= 0; x--) {
            if (field[x][b] == c) {
                count++;
            } else {
                break;
            }
        }
        if (WIN_COUNT - count <= 0) return true;
        return false;
    }

    public static boolean checkVerticalRL(char c, int a, int b, int WIN_COUNT, int fieldSizeX, int fieldSizeY, char[][] field) {
        int temp = b;
        int count = 0;
        for (int x = a; x < fieldSizeX; x++) {
            if (field[x][temp] == c) {
                count++;
                if (temp < fieldSizeY - 1) {
                    temp++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        temp = b;
        for (int x = a - 1; x >= 0; x--) {
            if (temp > 0) {
                temp--;
                if (field[x][temp] == c) {
                    count++;
                } else {
                    break;
                }
            }
        }
        if (WIN_COUNT - count <= 0) return true;
        return false;
    }

    public static boolean checkVerticalLR(char c, int a, int b, int WIN_COUNT, int fieldSizeX, int fieldSizeY, char[][] field) {
        int temp = b;
        int count = 0;
        for (int x = a; x < fieldSizeX; x++) {
            if (field[x][temp] == c) {
                count++;
                if (temp > 0) {
                    temp--;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        temp = b;
        for (int x = a - 1; x >= 0; x--) {
            if (temp < fieldSizeY - 1) {
                temp++;
                if (field[x][temp] == c) {
                    count++;
                } else {
                    break;
                }
            }
        }
        if (WIN_COUNT - count <= 0) return true;
        return false;
    }
}
