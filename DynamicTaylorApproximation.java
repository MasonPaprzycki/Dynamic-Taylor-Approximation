import java.util.ArrayList;

public class TaylorPolynomial {

    public static class TaylorResult {
        double approximatedValue;
        double errorBound;

        public TaylorResult() {
            this.errorBound = errorBound;
        }
    }

    // Computes the value of a polynomial at a given point t
    public static double computeValue(ArrayList<Double> coefficients, double t) {
        double sigma = 0; // Accumulator for the polynomial sum
        for (int z = 0; z < coefficients.size(); z++) {
            sigma += coefficients.get(z) * Math.pow(t, z);
        }
        return sigma;
    }

    // Computes the Taylor polynomial coefficients from the data array
    public static ArrayList<Double> computeTaylorPolynomial(double[][] data, errorMargin) {
        int n = data.length; // Number of data points
        double[] t = new double[n];
        double[] f = new double[n];
        double lagrange = 0;

        // Extract t and f values from the data array
        for (int i = 0; i < n; i++) {
            t[i] = data[i][0]; // First column is t values
            f[i] = data[i][1]; // Second column is f values
        }

        // List to store coefficients
        ArrayList<Double> coefficients = new ArrayList<>();
        coefficients.add(f[0]); // First coefficient is the value at t0

        // Compute higher-order coefficients
        for (int y = 1; y < n; y++) {
            double[] identity = calculateNextCoefficient(coefficients, t[y], f[y]);
            coefficients.add(identity[0]);
            lagrange = identity[1];
        }
        
        if(lagrange > errorMargin){
            return new ArrayList<Double>();
        }
        return coefficients;
    }




    // Calculates the next Taylor polynomial coefficient
    private static double[] calculateNextCoefficient(
        ArrayList<Double> currentCoefficients,
        double t,
        double f
    ) {
        double sigma = 0;

        // Sum up contributions from existing terms in the polynomial
        for (int a = 0; a < currentCoefficients.size(); a++) {
            sigma += currentCoefficients.get(a) * Math.pow(t, a);
        }

        // Compute the difference (error) to determine the next coefficient
        double target = f - sigma; 
        int a = currentCoefficients.size(); // Current order of the polynomial
        double coefficient = target / Math.pow(t, a); // Next coefficient
        

        return {coefficient, target};
    }

    // Factorial helper method
    private static long factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }



    // Main method for testing
    public static void main(String[] args) {
        // Example data: {t, f(t)} pairs
        double[][] data =   {
            {0.1, Math.tan(0.1)},
            {0.2, Math.tan(0.2)},
            {0.3, Math.tan(0.3)},
            {0.4, Math.tan(0.4)}, 
            {0.5, Math.tan(0.5)},
            {0.6, Math.tan(0.6)},
        };

        // Compute Taylor polynomial coefficients
        ArrayList<Double> coefficients = computeTaylorPolynomial(data,0.5);

        // Test computing value at a specific point
        double t = 0.7; // Test point
        double approximatedValue = computeValue(coefficients, t);
        t

        // Print results
        System.out.println("Actual value at t "+ Math.tan(t));
        System.out.println("Taylor Polynomial Coefficients: " + coefficients);
        System.out.println("Approximated Value at t = " + t + ": " + approximatedValue);
        System.out.printLn("Lagrange Error before End Term" + )this.errorBound;
    }
}