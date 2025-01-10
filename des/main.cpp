#include <algorithm>
#include <cmath>
#include <iostream>
#include <windows.h>
#include <vector>
#include <algorithm>
#include <random>

const int pc_1[56] = {
    57, 49, 41, 33, 25, 17, 9,
    1, 58, 50, 42, 34, 26, 18,
    10, 2, 59, 51, 43, 35, 27,
    19, 11, 3, 60, 52, 44, 36,
    63, 55, 47, 39, 31, 23, 15,
    7, 62, 54, 46, 38, 30, 22,
    14, 6, 61, 53, 45, 37, 29,
    21, 13, 5, 28, 20, 12, 4
};

int numLeftShift[16] = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1}; // number of bits to shift for each iteration

const int pc_2[48] = {
    14, 17, 11, 24, 1, 5,
    3, 28, 15, 6, 21, 10,
    23, 19, 12, 4, 26, 8,
    16, 7, 27, 20, 13, 2,
    41, 52, 31, 37, 47, 55,
    30, 40, 51, 45, 33, 48,
    44, 49, 39, 56, 34, 53,
    46, 42, 50, 36, 29, 32
};

const int IP_t[64] = {
    58, 50, 42, 34, 26, 18, 10, 2, // intital permutation table
    60, 52, 44, 36, 28, 20, 12, 4,
    62, 54, 46, 38, 30, 22, 14, 6,
    64, 56, 48, 40, 32, 24, 16, 8,
    57, 49, 41, 33, 25, 17, 9, 1,
    59, 51, 43, 35, 27, 19, 11, 3,
    61, 53, 45, 37, 29, 21, 13, 5,
    63, 55, 47, 39, 31, 23, 15, 7
};

const int E_t[48] = {
    32, 1, 2, 3, 4, 5, // expantion table
    4, 5, 6, 7, 8, 9,
    8, 9, 10, 11, 12, 13,
    12, 13, 14, 15, 16, 17,
    16, 17, 18, 19, 20, 21,
    20, 21, 22, 23, 24, 25,
    24, 25, 26, 27, 28, 29,
    28, 29, 30, 31, 32, 1
};

int S[8][4][16] = {
    // S-box
    {
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    },
    {
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    },
    {
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    },
    {
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    },
    {
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    },
    {
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    },
    {
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    },
    {
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    }
};

const int P[32] = {
    16, 7, 20, 21,
    29, 12, 28, 17,
    1, 15, 23, 26,
    5, 18, 31, 10,
    2, 8, 24, 14,
    32, 27, 3, 9,
    19, 13, 30, 6,
    22, 11, 4, 25
};

const int P_1[64] = {
    40, 8, 48, 16, 56, 24, 64, 32,
    39, 7, 47, 15, 55, 23, 63, 31,
    38, 6, 46, 14, 54, 22, 62, 30,
    37, 5, 45, 13, 53, 21, 61, 29,
    36, 4, 44, 12, 52, 20, 60, 28,
    35, 3, 43, 11, 51, 19, 59, 27,
    34, 2, 42, 10, 50, 18, 58, 26,
    33, 1, 41, 9, 49, 17, 57, 25
};


std::string decToHex(int decimal) {
    if (decimal == 0) {
        return "0"; // Обработка случая 0
    }

    std::string result;
    while (decimal != 0) {
        int remainder = decimal % 16;
        char ch;
        if (remainder >= 10)
            ch = 'A' + (remainder - 10); // Используем символы 'A' - 'F'
        else
            ch = '0' + remainder; // Используем символы '0' - '9'

        result += ch; // Добавляем символ в конец строки
        decimal = decimal / 16;
    }

    std::reverse(result.begin(), result.end()); // Переворачиваем строку
    return result;
}

std::string hexToBinary(std::string hex) {
    std::string binary;

    for (char c: hex) {
        switch (c) {
            case '0': binary += "0000";
                break;
            case '1': binary += "0001";
                break;
            case '2': binary += "0010";
                break;
            case '3': binary += "0011";
                break;
            case '4': binary += "0100";
                break;
            case '5': binary += "0101";
                break;
            case '6': binary += "0110";
                break;
            case '7': binary += "0111";
                break;
            case '8': binary += "1000";
                break;
            case '9': binary += "1001";
                break;
            case 'A': binary += "1010";
                break;
            case 'B': binary += "1011";
                break;
            case 'C': binary += "1100";
                break;
            case 'D': binary += "1101";
                break;
            case 'E': binary += "1110";
                break;
            case 'F': binary += "1111";
                break;
            default: throw std::invalid_argument("Invalid hexadecimal character");
        }
    }

    return binary;
}

std::string prepareText(std::string text) {
    std::vector<std::string> hexText;

    for (unsigned char c: text) {
        std::cout << "Text symbol: " << c << ", symbol code: " << int(c) << " " << ", hex code: " << decToHex(int(c)) <<
                "\n";
        hexText.push_back(decToHex(int(c)));
    }

    std::string combinedHexText;
    for (std::string hex: hexText) {
        combinedHexText += hex;
    }

    std::cout << "Hex Text: " << combinedHexText << "\n";

    return combinedHexText;
}

std::string prepareKey(std::string key) {
    std::vector<std::string> hexKey;

    for (unsigned char c: key) {
        std::cout << "Key symbol: " << c << ", symbol code: " << int(c) << " " << ", hex code: " << decToHex(int(c)) <<
                "\n";
        hexKey.push_back(decToHex(int(c)));
    }

    std::string combinedHexKey;
    for (std::string hex: hexKey) {
        combinedHexKey += hex;
    }

    std::cout << "Hex Key: " << combinedHexKey << "\n";

    return combinedHexKey;
}

std::string shiftBit(std::string s, int n) {
    std::string k = "";

    for (int i = n; i < s.size(); i++)
        k += s[i];

    for (int i = 0; i < n; i++)
        k += s[i];

    return k;
}

std::string xor_add(std::string s1, std::string s2) {
    std::string result = "";
    for (int j = 0; j < s1.size(); j++) {
        if (s1[j] != s2[j]) result += '1';
        else result += '0';
    }
    return result;
}

std::string decToBin(int n) {
    std::string bin = "";
    while (n > 0) {
        bin = (char) (n % 2 + '0') + bin;
        n /= 2;
    }
    while (bin.size() < 4)
        bin = '0' + bin;
    return bin;
}

std::string getElementFromBox(std::string s, int k) {
    int dec1 = 0, dec2 = 0, pwr = 0;
    dec1 = (int) (s[0] - '0') * 2 + (int) (s[5] - '0');
    for (int i = s.size() - 2; i >= 1; i--) {
        dec2 += (int) (s[i] - '0') * pow(2, pwr++);
    }

    return decToBin(S[k][dec1][dec2]);
}

std::string Bin_to_Hex(std::string s) {
    std::string hex = "";
    for (int i = 0; i < s.size(); i += 4) {
        std::string k = "";
        for (int j = i; j < i + 4; j++)
            k += s[j];
        if (k == "0000")
            hex += '0';
        else if (k == "0001")
            hex += '1';
        else if (k == "0010")
            hex += '2';
        else if (k == "0011")
            hex += '3';
        else if (k == "0100")
            hex += '4';
        else if (k == "0101")
            hex += '5';
        else if (k == "0110")
            hex += '6';
        else if (k == "0111")
            hex += '7';
        else if (k == "1000")
            hex += '8';
        else if (k == "1001")
            hex += '9';
        else if (k == "1010")
            hex += 'A';
        else if (k == "1011")
            hex += 'B';
        else if (k == "1100")
            hex += 'C';
        else if (k == "1101")
            hex += 'D';
        else if (k == "1110")
            hex += 'E';
        else if (k == "1111")
            hex += 'F';
    }
    return hex;
}

std::string binaryToWindows1251(const std::string &binary) {
    std::string text = "";

    for (size_t i = 0; i < binary.size(); i += 8) {
        std::string byte = binary.substr(i, 8);
        unsigned char character = static_cast<char>(std::stoi(byte, nullptr, 2));
        text += character;
    }

    return text;
}

std::string padPKCS7(const std::string &text) {
    size_t blockSize = 16; // Блок DES равен 16 байт
    size_t paddingLength = blockSize - (text.size() % blockSize);
    std::string paddedText = text;
    paddedText.append(paddingLength, static_cast<char>(paddingLength));
    return paddedText;
}

std::string removePKCS7Padding(const std::string &paddedText) {
    char paddingValue = paddedText.back(); // Последний байт указывает количество добавленных символов
    size_t paddingLength = static_cast<size_t>(paddingValue);
    return paddedText.substr(0, paddedText.size() - paddingLength);
}

// std::string decrypt(const std::string &encryptedBlock, const std::string key48[16]) {
//     std::string binaryBlock = hexToBinary(encryptedBlock);
//     std::string IP = "";
//
//     for (int i = 0; i < 64; i++) {
//         IP += binaryBlock[IP_t[i] - 1];
//     }
//
//     std::string L = "", R = "";
//
//     for (int i = 0; i < 32; i++)
//         L += IP[i];
//
//     for (int i = 32; i < 64; i++)
//         R += IP[i];
//
//     std::string L_32[16], R_32[16], R48[16];
//     L_32[15] = L;
//     R_32[15] = R;
//
//     for (int i = 15; i >= 0; i--) {
//         R48[i] = "";
//         for (int j = 0; j < 48; j++)
//             R48[i] += R_32[i][E_t[j] - 1];
//
//         std::string R_xor_K = xor_add(R48[i], key48[i]);
//
//         std::string s[8];
//
//         for (int j = 0; j < 48; j += 6) {
//             s[j / 6] = R_xor_K.substr(j, 6);
//         }
//
//         std::string s_1 = "";
//         for (int j = 0; j < 8; j++) {
//             s_1 += getElementFromBox(s[j], j);
//         }
//
//         std::string P_R = "";
//         for (int j = 0; j < 32; j++) {
//             P_R += s_1[P[j] - 1];
//         }
//
//         L_32[i - 1] = R_32[i];
//         if (i > 0) {
//             R_32[i - 1] = xor_add(P_R, L_32[i]);
//         }
//     }
//
//     std::string RL = R_32[0] + L_32[0];
//     std::string decryptedBinary = "";
//
//     for (int i = 0; i < 64; i++) {
//         decryptedBinary += RL[P_1[i] - 1];
//     }
//
//     std::cout<<decryptedBinary<<"\n";
//
//     return binaryToWindows1251(decryptedBinary);
// }
std::string decrypt(const std::string &hexEncrypted, const std::string &key) {
    std::string hexKey = prepareKey(key);

    if (hexKey.size() < 16) {
        while (hexKey.size() != 16) {
            hexKey = "0" + hexKey;
        }
    } else {
        hexKey = hexKey.substr(0, 16);
    }

    std::string keyBits = hexToBinary(hexKey);

    std::string key56 = "";
    std::string keyFirstHalf = "", keySecondHalf = "";

    for (int i = 0; i < 56; i++)
        key56 += keyBits[pc_1[i] - 1];

    for (int i = 0; i < 28; i++)
        keyFirstHalf += key56[i];

    for (int i = 28; i < 56; i++)
        keySecondHalf += key56[i];

    std::string lKey[16], rKey[16];

    lKey[0] = shiftBit(keyFirstHalf, numLeftShift[0]);
    rKey[0] = shiftBit(keySecondHalf, numLeftShift[0]);

    for (int i = 1; i < 16; i++) {
        lKey[i] = shiftBit(lKey[i - 1], numLeftShift[i]);
        rKey[i] = shiftBit(rKey[i - 1], numLeftShift[i]);
    }

    std::string key48[16], keys56[16];

    for (int i = 0; i < 16; i++) {
        keys56[i] = lKey[i] + rKey[i];
    }
    for (int i = 0; i < 16; i++) {
        key48[i] = "";
        for (int j = 0; j < 48; j++)
            key48[i] += keys56[i][pc_2[j] - 1];
    }

    std::string encryptedBin = hexToBinary(hexEncrypted);
    std::string IP = "";

    for (int i = 0; i < 64; i++)
        IP += encryptedBin[IP_t[i] - 1];

    std::string L = "", R = "";

    for (int i = 0; i < 32; i++)
        L += IP[i];

    for (int i = 32; i < 64; i++)
        R += IP[i];

    std::string R48[16];

    for (int i = 15; i >= 0; i--) {
        std::string prevL = L;
        std::string prevR = R;

        L = prevR;
        R48[i] = "";

        for (int j = 0; j < 48; j++)
            R48[i] += prevR[E_t[j] - 1];

        std::string R_xor_K = xor_add(R48[i], key48[i]);

        std::string s[8];
        for (int j = 0; j < 48; j += 6)
            for (int k = j; k < j + 6; k++)
                s[j / 6] += R_xor_K[k];

        std::string s_1 = "";
        for (int j = 0; j < 8; j++)
            s_1 += getElementFromBox(s[j], j);

        std::string P_R = "";
        for (int j = 0; j < 32; j++)
            P_R += s_1[P[j] - 1];

        R = xor_add(P_R, prevL);
    }

    std::string decryptedBin = R + L;
    std::string RL = "";

    for (int i = 0; i < 64; i++)
        RL += decryptedBin[P_1[i] - 1];

    return binaryToWindows1251(RL);
}


int main() {
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);

    std::cout << "Enter text to encrypt/decrypt:" << std::endl;
    std::string text;
    std::getline(std::cin, text);

    std::string combinedHexText = prepareText(text);

    std::vector<std::string> hexTextBlocks;
    for (int i = 0; i < combinedHexText.length(); i += 16) {
        hexTextBlocks.push_back(combinedHexText.substr(i, 16));
    }

    for (std::string &block: hexTextBlocks) {
        if (block.size() < 16) {
            while (block.size()<16) {
                block = "0"+block;
            }
        }
    }

    std::string key;
    std::getline(std::cin, key);

    std::string hexKey = prepareKey(key);

    if (hexKey.size() < 16) {
        while (hexKey.size() != 16) {
            hexKey = "0" + hexKey;
        }
    } else
        hexKey = hexKey.substr(0, 16);

    std::string keyBits = hexToBinary(hexKey);
    std::cout << "Key Bits: " << keyBits << "\n";

    std::string key56 = "";
    std::string keyFirstHalf = "", keySecondHalf = "";

    for (int i = 0; i < 56; i++)
        key56 += keyBits[pc_1[i] - 1];

    std::cout << "Key after permutation: " << key56 << "\n";

    for (int i = 0; i < 28; i++)
        keyFirstHalf += key56[i];

    for (int i = 28; i < 56; i++)
        keySecondHalf += key56[i];

    std::string lKey[16], rKey[16];

    lKey[0] = shiftBit(keyFirstHalf, numLeftShift[0]);
    rKey[0] = shiftBit(keySecondHalf, numLeftShift[0]);

    for (int i = 1; i < 16; i++) {
        lKey[i] = shiftBit(lKey[i - 1], numLeftShift[i]);
        rKey[i] = shiftBit(rKey[i - 1], numLeftShift[i]);
    }

    std::string key48[16], keys56[16];

    for (int i = 0; i < 16; i++) {
        keys56[i] = lKey[i] + rKey[i]; // making 56 bits keys
    }
    for (int i = 0; i < 16; i++) {
        key48[i] = "";
        for (int j = 0; j < 48; j++)
            key48[i] += keys56[i][pc_2[j] - 1]; // making 48 bits keys

        std::cout << "Key48[" << i << "]: " << key48[i] << std::endl;
    }

    for (const std::string &hexTextBlock: hexTextBlocks) {
        std::string hexTextBlockBits = hexToBinary(hexTextBlock);
        std::cout << "Hex Text Bits: " << hexTextBlockBits << "\n";

        std::string IP = "";

        for (int i = 0; i < 64; i++)
            IP += hexTextBlockBits[IP_t[i] - 1];

        std::string L = "", R = "";

        for (int i = 0; i < 32; i++)
            L += IP[i];

        for (int i = 32; i < 64; i++)
            R += IP[i];

        std::string R48[16];

        R48[0] = "";
        for (int j = 0; j < 48; j++)
            R48[0] += R[E_t[j] - 1];

        std::string R_xor_K[16];
        R_xor_K[0] = xor_add(R48[0], key48[0]); // fill the R_xor_K array

        std::string s[16][8];
        for (int j = 0; j < 48; j += 6) // dividing each value of R_xor_K to 8 string contaning 6 char each
            for (int k = j; k < j + 6; k++)
                s[0][j / 6] += R_xor_K[0][k];

        std::string s_1[16];
        s_1[0] = "";
        for (int j = 0; j < 8; j++)
            s_1[0] += getElementFromBox(s[0][j], j);

        std::string P_R[16];
        for (int j = 0; j < 32; j++)
            P_R[0] += s_1[0][P[j] - 1];

        std::string L_32[16], R_32[16];
        L_32[0] = R;
        R_32[0] = "";
        R_32[0] = xor_add(P_R[0], L);
        std::cout << "Encryption Round " << 1 << ":\n";
        std::cout << "    R48: " << R48[0] << "\n";
        std::cout << "    R_xor_K: " << R_xor_K[0] << "\n";
        std::cout << "    P_R: " << P_R[0] << "\n";

        for (int i = 1; i < 16; i++) {
            L_32[i] = R_32[i - 1];
            R48[i] = "";
            for (int j = 0; j < 48; j++)
                R48[i] += R_32[i - 1][E_t[j] - 1];

            R_xor_K[i] = xor_add(R48[i], key48[i]); // fill the R_xor_K

            for (int j = 0; j < 48; j += 6) // dividing each value of R_xor_K to 8 string contaning 6 char each
                for (int k = j; k < j + 6; k++)
                    s[i][j / 6] += R_xor_K[i][k];

            s_1[i] = "";
            for (int j = 0; j < 8; j++)
                s_1[i] += getElementFromBox(s[i][j], j);

            for (int j = 0; j < 32; j++)
                P_R[i] += s_1[i][P[j] - 1];

            L_32[i] = R_32[i - 1];
            R_32[i] = "";
            R_32[i] = xor_add(P_R[i], L_32[i - 1]);

            std::cout << "Encryption Round " << i + 1 << ":\n";
            std::cout << "    R48: " << R48[i] << "\n";
            std::cout << "    R_xor_K: " << R_xor_K[i] << "\n";
            std::cout << "    P_R: " << P_R[i] << "\n";
        }

        std::string encrypted_bin = "", RL;

        RL = R_32[15] + L_32[15];

        for (int i = 0; i < 64; i++)
            encrypted_bin += RL[P_1[i] - 1];

        std::string encryptedText = binaryToWindows1251(encrypted_bin);
        std::cout << "Encrypted text in Windows-1251: " << encryptedText << std::endl;

        std::string hexEncrypted = Bin_to_Hex(encrypted_bin);
        std::cout << hexEncrypted << std::endl;

        std::cout << "Расшифрованный текст: " << decrypt(hexEncrypted, key) << std::endl;
    }

    return 0;
}
