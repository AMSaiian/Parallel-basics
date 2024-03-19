package FileAnalyze.WordLengthAnalyzer;

public record Statistics(double avgWordLength, double dispersion, double stdDeviation)
{
    @Override
    public String toString() {
        return String.format("Average word length = %f - dispersion = %f - standard deviation = %f",
                avgWordLength, dispersion, stdDeviation);
    }
}