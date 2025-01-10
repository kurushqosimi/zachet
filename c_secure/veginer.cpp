#include"veginer.h"

std::string veginerDecode(std::string s, std::string key) {
    std::string dec;
    for (int i = 0; i < s.length(); ++i) {
        char nc = (s[i] - key[i % key.length()]) % 256;
        if (nc >= 0 && nc < 32) nc -= 224;
        dec += nc;
    }
    return dec;
}

std::string veginerEncode(std::string s, std::string key) {
    std::string enc;
    for (int i = 0; i < s.length(); ++i) {
        char nc = (s[i] + key[i % key.length()]) % 256;
        if (nc >= 0 && nc < 32) nc += 224;
        enc += nc;
    }
    return enc;
}
