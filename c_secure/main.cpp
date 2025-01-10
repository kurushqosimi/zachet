#include"cesar.h"
#include "plaifor.h"
#include "vertical.h"
#include "veginer.h"
#include <windows.h>
#include <fstream>
#include <map>
#include <vector>
#include <functional>
// #include <Functional>



std::string readFile(std::string fileName) {
    std::ifstream f(fileName);
    if (!f) {
        std::cerr << "couln't open file";
    }
    std::string line, full;
    while (std::getline(f, line )) full+=line;
    f.close();
    return full;
}

std::map<int, std::function<std::string(std::string, std::string)>> getFuncMaps() {
    std::map<int, std::function<std::string(std::string,std::string)>> mp;
    mp[0] = cesarDecode;
    mp[1] = veginerDecode;
    mp[2] = verticalDecode;
    mp[3] = plaiforDecode;
    return mp;
}

int main(){
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);
    std::map<int, std::function<std::string(std::string, std::string)>> pairs = getFuncMaps();
    std::map<int, std::string> mp;
    mp[0] = "Cesar";
    mp[1] = "Veginer";
    mp[2] = "Vertical";
    mp[3] = "Plaifor";
    std::string enc = readFile("../plaifor.txt");
    std::string keys[] = {
            "доступный", "информатика", "информация", "клавиатура", "клиент",
            "компьютер", "модернизация", "накопление", "обеспечить", "память",
            "предложение", "прикладная", "системное", "сохранение", "угроза",
            "целостность", "центральный", "шифрование", "applied", "client",
            "computer", "decrypt", "display", "encrypt", "keyboard", "matrix",
            "memory", "model", "monitor", "mouse", "program", "result", "save",
            "system", "value", "vector"
    };
    for (std::string key:keys) {
        for (int i = 0; i < 4; ++i) {
            std::cout << "\n" << mp[i] << " " << pairs[i](enc, key) << "\n";
        }
    }
}
