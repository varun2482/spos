#include <iostream>

// Remove the Windows-specific headers and functions

// Define the interface for the dynamic link library
#ifdef __cplusplus
extern "C" {
#endif

// Remove __declspec(dllexport) as it's Windows-specific
double add(double a, double b);
double subtract(double a, double b);
double multiply(double a, double b);
double divide(double a, double b);

#ifdef __cplusplus
}
#endif

// Implementation of mathematical operations
double add(double a, double b) {
    return a + b;
}

double subtract(double a, double b) {
    return a - b;
}

double multiply(double a, double b) {
    return a * b;
}

double divide(double a, double b) {
    if (b == 0) {
        std::cerr << "Error: Division by zero!" << std::endl;
        return 0;
    }
    return a / b;
}

int main() {
    // Test mathematical operations
    double result_add = add(5, 3);
    double result_subtract = subtract(5, 3);
    double result_multiply = multiply(5, 3);
    double result_divide = divide(5, 3);

    // Output results
    std::cout << "Addition: " << result_add << std::endl;
    std::cout << "Subtraction: " << result_subtract << std::endl;
    std::cout << "Multiplication: " << result_multiply << std::endl;
    std::cout << "Division: " << result_divide << std::endl;

    return 0;
}
