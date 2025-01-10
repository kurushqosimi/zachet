#include<iostream>
#include<map>

std::string getUniqueS(std::string s)
{
    std::string unique;
    std::map<char, int> check;
    for (char c:s)
    {
        if (check.find(c) == check.end())
        {
            unique += c;
            check[c] = 1;
        }
    }
    return unique;
}

int row = 14, colum = 16;
char** mrx = new char*[row];

std::map<char, int*> getMatrix(std::string key)
{
    key = getUniqueS(key);
    std::map<char, int> alphabet;
    std::map<char, int*> mp;

    for (int i = 0; i < row; ++i)
    {
        mrx[i] = new char[colum];
    }

    int k  = 0, i = 0, j = 0;
    while (k < key.length())
    {
        alphabet[key[k]] = k;
        mp[key[k]] = new int[2]{i,j};
        mrx[i][j++] = key[k++];
        i += j % colum == 0;
        j = j % colum;
    }
    for (int l = 32; l < 256; ++l)
    {
        if (alphabet.find(char(l)) == alphabet.end()) {
            mp[char(l)] = new int[2]{i,j};
            mrx[i][j++] = char(l);
            i += j % colum == 0;
            j = j % colum;
        }
    }

    return mp;
}

int* getFromMrx(int* a, int* b)
{
    int rowA = a[0], colA = a[1], rowB = b[0], colB = b[1];
    if (rowA == rowB && colA == colB)
        return new int[4]{rowA, colA, rowB, colB};

    if (rowA == rowB)
        return new int[4]{rowA, (colA+1) % 16, rowA, (colB+1) % 16};

    if (colA == colB)
        return new int[4]{(rowA+1) % 14, colA, (rowB+1)%14, colB};

    return new int[4]{rowA, colB, rowB, colA};
}


std::string getCleanStr(std::string s) {
    if (s.length()%2) s += " ";
    // std::string newS;
    // for (int i = 0; i < s.length(); i+=2) {
    //     if (s[i] == s[i+1]) {
    //         newS += s[i];
    //         newS += " ";
    //         newS += s[i+1];
    //         newS += " ";
    //         continue;
    //     }
    //     newS += s[i];
    //     newS += s[i+1];
    // }
    return s;
}
int *getDecFromMatrix(int *a, int *b) {
    int rowA = a[0], colA = a[1], rowB = b[0], colB = b[1];

    if (rowA == rowB && colA == colB)
        return new int[4]{rowA, colA, rowB, colB};

    if (rowA == rowB)
        return new int[4]{rowA, (colA - 1 + colum)%colum, rowB, (colB - 1 + colum)%colum};

    if (colA == colB)
        return new int[4]{(rowA - 1 + row) % row, colA, (rowB - 1 + row) % row, colB};

    return new int[4]{rowA, colB, rowB, colA};
}



std::string  plaiforEncode(std::string key, std::string s) {
    std::map<char, int*> mp1= getMatrix(key);
    s = getCleanStr(s);
    std::string enc;
    for (int i = 0; i < s.length(); i+=2) {
        int * indexes = getFromMrx(mp1[s[i]], mp1[s[i+1]]);
        enc += mrx[indexes[0]][indexes[1]];
        enc += mrx[indexes[2]][indexes[3]];
    }
    return enc;
}


std::string plaiforDecode(std::string s, std::string key) {
    std::map<char, int*> mp = getMatrix(key);
    std::string dec;
    for (int i = 0; i < s.length(); i+=2) {
        int * indxes = getDecFromMatrix(mp[s[i]], mp[s[i+1]]);
        dec += mrx[indxes[0]][indxes[1]];
        dec += mrx[indxes[2]][indxes[3]];
    }
    return dec;
}

