package use.Case.hit;

public interface HitOutputBoundary {

    void prepareSuccessView(HitOutputData outputData);

    void prepareFailView(String errorMessage);
}
