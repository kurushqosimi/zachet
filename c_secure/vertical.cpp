#include "vertical.h"

#include <map>

std::string getUnique(std::string s)
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

std::string sort(std::string s)
{
    for (int i = 0; i < s.length(); ++i) {
        for (int j = 0; j < s.length() - 1; j++) {
            if (std::to_string(s[j]) > std::to_string(s[j+1])) {
                std::swap(s[j], s[j+1]);
            }
        }
    }
    return s;
}

std::string verticalEncode(std::string text, std::string key)
{
    int kl = key.length(), tl = text.length();
    int rows = tl/kl +(tl%kl != 0), columns = kl;
    std::string matrix[rows][columns];
    int k = 0;
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < columns; j++)
        {
            if (k >= tl)
            {
                matrix[i][j] += char(11);
                continue;
            }
            matrix[i][j] += text[k++];
        }
    }
    std::string sKey = sort(key), res;
    for (char c:sKey)
    {
        for (int j = 0; j < rows; j++)
        {
            res += matrix[j][key.find(c)];
        }
    }
    return res;
}

std::map<int, int> sortIn(int *s, int n)
{
    std::map<int, int> mp;
    for (int i = n-1; i >= 0; i--)
    {
        int maxInd = i, max = s[i];
        for (int j = 0; j < i; j++)
        {
            if (s[j] > max)
            {
                max = s[j];
                maxInd = j;
            }
        }
        mp[s[maxInd]] = i;
        std::swap(s[i], s[maxInd]);
    }
    return mp;
}


std::string cleanString(std::string s)
{
    std::string ns;
    for (char c:s)
    {
        if (c == char(11)) continue;
        ns += c;
    }
    return ns;
}


std::string verticalDecode(std::string s, std::string key)
{
    key = getUnique(key);
    int kl = key.length(), tl = s.length();
    int rows = tl/kl +(tl%kl != 0), columns = kl;
    std::string matrix[rows][columns];
    std::string sKey = sort(key);
    int k = 0;
    for (char c:sKey)
    {
        for (int j = 0; j < rows; j++)
        {
            matrix[j][key.find(c)] = s[k++];
        }
    }
    std::string res;
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < columns; j++)
        {
            res += matrix[i][j];
        }
    }
    return cleanString(res);
}
