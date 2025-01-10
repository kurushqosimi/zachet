#include "cesar.h"

std::string cesarEncode(std::string s, std::string key) {
    std::string enc;
    for(char c:s) {
        char nc = (key.length() + c)%256;
        if (nc >= 0 && nc < 32) nc -= 224;

        enc += nc;
    }
    return enc;
}

std::string cesarDecode(std::string s, std::string key) {
    std::string dec;
    for (char c:s) {
        char nc = (c - key.length())%256;
        if (nc >= 0 && nc < 32) nc += 224;
        dec += nc;
    }
    return dec;
}